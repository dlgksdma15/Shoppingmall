package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.model.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.model.order.service.OrderService;
import com.nhnacademy.shoppingmall.model.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.model.order.repository.impl.OrderItemRepositoryImpl;
import com.nhnacademy.shoppingmall.model.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import com.nhnacademy.shoppingmall.model.user.repository.impl.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 주문하기
@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/order/create.do")
public class OrderCreateController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(
            new OrderRepositoryImpl(),
            new OrderItemRepositoryImpl(),
            new CartRepositoryImpl(),
            new UserRepositoryImpl(),
            new ProductRepositoryImpl()
    );

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            orderService.createOrder(user.getUserId());

            // 세션의 user 정보 갱신 (포인트 차감 반영)
            User updatedUser = new UserRepositoryImpl().findById(user.getUserId()).orElse(user);
            session.setAttribute("user", updatedUser);

            log.info("Order created successfully for user: {}", user.getUserId());

            return "redirect:/mypage/cart.do";
        } catch (RuntimeException e) {
            log.error("Order creation failed: {}", e.getMessage());
            return "redirect:/mypage/cart.do?error=" + e.getMessage();
        }
    }
}