package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.model.product.service.ProductService;
import com.nhnacademy.shoppingmall.model.product.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/product/delete.do")
public class ProductDeleteController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("productId");

        if (productIdParam == null || productIdParam.trim().isEmpty()){
            log.warn("Product는 없는 ID입니다.");
            return "redirect:/index.do";
        }

        try{
            productService.deleteProduct(productIdParam);
            log.info("Product {} 삭제", productIdParam);

            return "redirect:/index.do";
        } catch (Exception e){
            log.error("Product 삭제중 오류:" + e.getMessage());
            return "redirect:/index.do?error=delete_failed";
        }

    }
}
