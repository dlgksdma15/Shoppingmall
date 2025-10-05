package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
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
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/edit.do")
public class ProductEditController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("productId");

        if(productIdParam == null || productIdParam.trim().isEmpty()){
            log.warn("Product ID가 없습니다.");
            return "redirect:/index.do";
        }

        try{
            int productId = Integer.parseInt(productIdParam);
            Product product = productService.getProduct(productId);
            List<Category> categories = categoryService.getAllCategories();

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);

            log.debug("Product 상품 수정: {}", product.getProductName());

            return "shop/product/product_edit_form";
        } catch (NumberFormatException e) {
            log.error("Invalid product ID format: {}", productIdParam);
            return "redirect:/index.do";
        } catch (Exception e) {
            log.error("Error loading product edit form", e);
            return "redirect:/index.do";
        }

    }
}
