package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.product.domain.Product;
import com.nhnacademy.shoppingmall.model.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.model.product.service.ProductService;
import com.nhnacademy.shoppingmall.model.product.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/detail.do")
public class ProductDetailController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("productId");

        if (productIdParam == null || productIdParam.trim().isEmpty()){
            log.warn("Product는 없는 ID입니다.");
            return "redirect:/index.do";
        }

        try{
            int productId = Integer.parseInt(productIdParam);
            Product product = productService.getProduct(productId);

            req.setAttribute("product", product);
            log.debug("Product의 Name: {}", product.getProductName());

            return "shop/product/product_detail";

        } catch (NumberFormatException e) {
            log.error("Invalid product ID format: {}", productIdParam);
            return "redirect:/index.do";
        } catch (Exception e) {
            log.error("Error loading product detail", e);
            return "redirect:/index.do";
        }
    }
}
