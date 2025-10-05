package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.model.cart.service.CartService;
import com.nhnacademy.shoppingmall.model.cart.service.impl.CartServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/cart/delete.do")
public class CartDeleteController implements BaseController {
    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int cartId = Integer.parseInt(req.getParameter("cardId"));

        cartService.removeFromCart(cartId);
        log.info("Removed from cart: cartId={}", cartId);

        return "redirect:/mypage/cart.do";
    }
}
