package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.model.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.model.cart.service.CartService;
import com.nhnacademy.shoppingmall.model.cart.service.impl.CartServiceImpl;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/cart.do")
public class CartViewController implements BaseController {

    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        List<CartItem> cartItems = cartService.getCartItems(user.getUserId());

        // 총 금액 계산
        int totalAmount = cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("totalAmount", totalAmount);

        log.debug("Cart view loaded for user: {}, items count: {}", user.getUserId(), cartItems.size());

        return "shop/cart/cart_view";
    }
}