package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import com.nhnacademy.shoppingmall.model.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class PointChannelRequest extends ChannelRequest {

    private final UserRepository userRepository;
    private final String userId;
    private final int points;


    public PointChannelRequest(UserRepository userRepository, String userId, int points) {
        this.userRepository = userRepository;
        this.userId = userId;
        this.points = points;
    }

    @Override
    public void execute() throws SQLException {
        DbConnectionThreadLocal.initialize();

        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        User user = userRepository.findById(userId).orElse(null);
        try {
            if (user != null) {
                // 사용자의 현재 포인트에 적립할 포인트 더한다.
                user.setUserPoint(user.getUserPoint() + points);

                // 변경된 사용자 정보로 업데이트
                userRepository.update(user);
                log.info("유저 정보 변경: " + user);
            } else {
                log.warn("유저 아이디를 찾을 수 없습니다. userId:{}", userId);
            }
        } catch (Exception e){
            log.error("포인트가 추가 되지 못해 롤백합니다." + e.getMessage());
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
        log.debug("pointChannel execute");
    }
}