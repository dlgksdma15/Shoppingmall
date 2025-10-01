package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        ServletContext servletContext = sce.getServletContext();

        if(userService.getUser("admin") == null){
            User admin = new User(
                    "admin",
                    "관리자",
                    "1234",
                    "2000-01-01",
                    User.Auth.ROLE_ADMIN,
                    100000,
                    LocalDateTime.now(),
                    null
            );

            userService.saveUser(admin);
            log.info("Admin Created");
        }

        if(userService.getUser("user") == null){
            User user = new User(
                    "user",
                    "일반사용자",
                    "1234",
                    "2000-01-01",
                    User.Auth.ROLE_USER,
                    100000,
                    LocalDateTime.now(),
                    null
            );
            userService.saveUser(user);
            log.info("User Created");
        }
        servletContext.setAttribute("userService",userService);
    }
}
