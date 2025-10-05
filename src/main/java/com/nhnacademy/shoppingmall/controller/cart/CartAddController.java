package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.model.cart.service.CartService;
import com.nhnacademy.shoppingmall.model.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/cart/add.do")
public class CartAddController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        cartService.addToCart(user.getUserId(), productId, quantity);

        return "redirect:/mypage/cart.do";
    }
//    @Override
//    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        HttpSession session = req.getSession(false);
//        User user = (User) session.getAttribute("user");
//
//        try {
//            int productId = Integer.parseInt(req.getParameter("productId"));
//            int quantity = Integer.parseInt(req.getParameter("quantity"));
//
//            cartService.addToCart(user.getUserId(), productId, quantity);
//            log.info("Added to cart: userId={}, productId={}, quantity={}", user.getUserId(), productId, quantity);
//
//            return "redirect:/mypage/cart.do";
//        } catch (RuntimeException e) {
//            log.error("Failed to add to cart: {}", e.getMessage());
//            return "redirect:/product/detail.do?productId=" + req.getParameter("productId") + "&error=duplicate";
//        }
//    }
}