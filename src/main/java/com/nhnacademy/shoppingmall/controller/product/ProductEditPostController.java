package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.product.domain.Product;
import com.nhnacademy.shoppingmall.model.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.model.product.service.ProductService;
import com.nhnacademy.shoppingmall.model.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/edit.do")
public class ProductEditPostController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 상품 ID
        int productId = Integer.parseInt(req.getParameter("productId"));
        
        // 폼 데이터 추출
        String productName = req.getParameter("productName");
        int productNumber = Integer.parseInt(partToString(req.getPart("productNumber")));
        int productUnitCost = Integer.parseInt(partToString(req.getPart("productUnitCost")));
        String productDescription = partToString(req.getPart("productDescription"));
        int categoryId = Integer.parseInt(partToString(req.getPart("categoryId")));
        String productIdParam = req.getParameter("productId");

        Product existingProduct = productService.getProduct(productId);
        String productImage = existingProduct.getProductImage();

        // 새 이미지 업로드 여부 확인
        Part filePart = req.getPart("productImage");
        if (filePart != null && filePart.getSize() > 0) {
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // 파일 확장자 추출
            String fileExtension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
            }

            // UUID로 새 파일명 생성
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 파일 저장
            ServletContext servletContext = req.getServletContext();
            String uploadDir = servletContext.getRealPath("/resources/uploads");
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            String uploadPath = uploadDir + File.separator + fileName;
            try {
                filePart.write(uploadPath);
                productImage = "/resources/uploads/" + fileName;
                log.debug("New image uploaded: {}", fileName);
            } catch (IOException e) {
                log.error("Error writing uploaded file", e);
            }
        }

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        // 수정된 상품 객체 생성
        Product product = new Product(
                productId,
                userId,
                categoryId,
                productName,
                productNumber,
                productImage,
                productUnitCost,
                productDescription
        );

        productService.updateProduct(product);
        log.info("Product updated: ID={}, Name={}", productId, productName);

        return "redirect:/product/detail.do?productId=" + productId;
    }

    private String partToString(Part part) throws IOException {
        if (part == null) {
            return null;
        }
        try (Scanner scanner = new Scanner(part.getInputStream(), StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        }
    }
}
