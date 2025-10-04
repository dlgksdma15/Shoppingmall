package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signup.do")
public class SignupPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String userBirth = req.getParameter("userBirth");
        User.Auth userAuth = User.Auth.ROLE_USER;
        int userPoint = 100_0000;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime latestLoginAt = null;


        try{
            User newUser = new User(userId,userName,userPassword,userBirth,userAuth,userPoint,createdAt,latestLoginAt);
            userService.saveUser(newUser);

            log.info("User 회원가입 새로운 userId: {}",userId);

            return "redirect:/login.do";

        } catch (UserAlreadyExistsException e) {
            log.warn("이미 있는 유저 ID: {}", userId);

            return "redirect:/login.do?error=true";

        }

    }
}
