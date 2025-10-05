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
import jakarta.servlet.annotation.MultipartConfig;
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
@MultipartConfig
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/create.do")
public class ProductPostController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 폼 데이터 추출
        String productName = partToString(req.getPart("productName"));
        int productNumber = Integer.parseInt(partToString(req.getPart("productNumber"))); // 제품 수량
        int productUnitCost = Integer.parseInt(partToString(req.getPart("productUnitCost")));
        String productDescription = partToString(req.getPart("productDescription"));
        int categoryId = Integer.parseInt(partToString(req.getPart("categoryId")));
        // 원본 파일명 추출
        Part filePart = req.getPart("productImage");
        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // 파일명만 추출

        // 파일 확장자 추출
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf('.'); // 바나나.jgp -> fileExtension = ".jpg"
        if (dotIndex > 0) {
            fileExtension = originalFileName.substring(dotIndex);
        }

        // UUID로 고유한 파일명 생성 (한글 파일명 문제 해결 + 중복 방지)
        String fileName = UUID.randomUUID().toString() + fileExtension;
        // 파일 저장 위치 설정
        ServletContext servletContext = req.getServletContext();
        String uploadDir = servletContext.getRealPath("/resources/uploads");
        // 디렉토리 생성
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            log.debug("Upload directory does not exist. Creating it...");
            boolean created = uploadDirFile.mkdirs();
            log.debug("Directory creation result: {}", created);
        }
        // 파일 저장
        String uploadPath = uploadDir + File.separator + fileName; // uploadPath = /uploads/a3f5c7d8-1b2e-4f9a-8c3d-9e7f6a5b4c3d.jpg
        log.debug("Final upload path: {}", uploadPath);

        try {
            filePart.write(uploadPath);
            log.debug("File write attempt finished for path: {}", uploadPath);
        } catch (IOException e) {
            log.error("Error writing uploaded file", e);
        }
        log.debug("=====================================================");
        // DB에 저장할 경로 생성
        String productImage = "/resources/uploads/" + fileName;

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();

        Product product = new Product(0, userId, categoryId, productName, productNumber, productImage, productUnitCost, productDescription);

        productService.saveProduct(product);

        return "redirect:/index.do";
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