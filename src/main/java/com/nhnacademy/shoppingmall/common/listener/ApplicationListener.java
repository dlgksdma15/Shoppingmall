package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDateTime;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        try{
            DbConnectionThreadLocal.initialize();

            ServletContext servletContext = sce.getServletContext();

            if(userService.getUser("admin") == null){
                User admin = new User("admin", "관리자", "1234", "20000101",
                        User.Auth.ROLE_ADMIN, 100000, LocalDateTime.now(), null);

                userService.saveUser(admin);
                log.info("Admin Created");
            }

            if(userService.getUser("user") == null){
                User user = new User("user", "일반사용자", "1234", "20000101",
                        User.Auth.ROLE_USER, 100000, LocalDateTime.now(), null);
                userService.saveUser(user);
                log.info("User Created");
            }
            servletContext.setAttribute("userService",userService);

        } catch (Exception e) {
             log.error("Error initializing application data: {}", e.getMessage());
             // 2. 에러 발생 시 롤백을 위해 플래그 설정
             DbConnectionThreadLocal.setSqlError(true);
         } finally {
             // 3. 트랜잭션 종료 (에러 없으면 commit, 있으면 rollback) 및 커넥션 반납
             DbConnectionThreadLocal.reset();
         }

    }
}
