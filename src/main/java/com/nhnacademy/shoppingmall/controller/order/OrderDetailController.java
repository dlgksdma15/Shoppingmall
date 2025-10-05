package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.model.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.model.order.service.OrderService;
import com.nhnacademy.shoppingmall.model.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.model.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.model.order.repository.impl.OrderItemRepositoryImpl;
import com.nhnacademy.shoppingmall.model.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.model.user.repository.impl.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// 주문 상세보기
@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/order/detail.do")
public class OrderDetailController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(
            new OrderRepositoryImpl(),
            new OrderItemRepositoryImpl(),
            new CartRepositoryImpl(),
            new UserRepositoryImpl(),
            new ProductRepositoryImpl()
    );

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        List<OrderItem> orderItems = orderService.getOrderItems(orderId);

        req.setAttribute("orderId", orderId);
        req.setAttribute("orderItems", orderItems);

        log.debug("Order detail loaded: orderId={}, items count={}", orderId, orderItems.size());

        return "shop/order/order_detail";
    }
}