package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.cart.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.model.order.domain.Order;
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

import java.util.List;

// 주문 내역 補氣
@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/orders.do")
public class OrderListController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(
            new OrderRepositoryImpl(),
            new OrderItemRepositoryImpl(),
            new CartRepositoryImpl(),
            new UserRepositoryImpl(),
            new ProductRepositoryImpl()
    );

    private static final int PAGE_SIZE = 10;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        log.debug("Attempting to fetch orders for userId from session: {}", user.getUserId());

        // 페이지 번호 가져오기 (기본값 1)
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // 주문 목록 조회
        List<Order> orders = orderService.getOrdersByUserId(user.getUserId(), page, PAGE_SIZE);
        long totalCount = orderService.getTotalOrderCount(user.getUserId());
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

        req.setAttribute("orders", orders);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalCount", totalCount);

        log.debug("Order list loaded: userId={}, page={}, totalCount={}", user.getUserId(), page, totalCount);

        return "shop/order/order_list";
    }
}