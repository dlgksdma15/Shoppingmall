package com.nhnacademy.shoppingmall.model.order.service.impl;

import com.nhnacademy.shoppingmall.model.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.model.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.model.order.domain.Order;
import com.nhnacademy.shoppingmall.model.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.model.order.service.OrderService;
import com.nhnacademy.shoppingmall.model.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.model.order.repository.OrderItemRepository;
import com.nhnacademy.shoppingmall.model.product.domain.Product;
import com.nhnacademy.shoppingmall.model.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import com.nhnacademy.shoppingmall.model.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartRepository cartRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createOrder(String userId) {
        // 1. 장바구니 조회
        List<CartItem> cartItems = cartRepository.findCartItemsByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("장바구니가 비어있습니다.");
        }

        // 2. 총 금액 계산
        int totalAmount = cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();

        // 3. 사용자 포인트 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (user.getUserPoint() < totalAmount) {
            throw new RuntimeException("포인트가 부족합니다. 필요: " + totalAmount + ", 보유: " + user.getUserPoint());
        }

        // 4. 재고 확인 및 차감
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

            if (product.getProductNumber() < cartItem.getQuantity()) {
                throw new RuntimeException(product.getProductName() + "의 재고가 부족합니다.");
            }

            // 재고 차감
            product.setProductNumber(product.getProductNumber() - cartItem.getQuantity());
            productRepository.update(product);
        }

        // 5. 주문 생성
        Order order = new Order(0, userId, totalAmount, new Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);

        // 6. 주문 ID 가져오기
        int orderId = orderRepository.getLastInsertId();

        // 7. 주문 상품 저장
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                    0,
                    orderId,
                    cartItem.getProduct().getProductId(),
                    cartItem.getProduct().getProductName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getProductUnitCost()
            );
            orderItemRepository.save(orderItem);
        }

        // 8. 포인트 차감
        user.setUserPoint(user.getUserPoint() - totalAmount);
        userRepository.update(user);

        // 9. 장바구니 비우기
        cartRepository.deleteAllByUserId(userId);

        log.info("Order created successfully: userId={}, orderId={}, totalAmount={}", userId, orderId, totalAmount);
    }

    @Override
    public List<Order> getOrdersByUserId(String userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return orderRepository.findByUserId(userId, offset, pageSize);
    }

    @Override
    public List<OrderItem> getOrderItems(int orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public long getTotalOrderCount(String userId) {
        return orderRepository.countByUserId(userId);
    }
}