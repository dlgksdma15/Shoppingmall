package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.

        String userId = req.getParameter("userId");
        String userPassword = req.getParameter("userPassword");

        try{
            User user = userService.doLogin(userId, userPassword);

            HttpSession session = req.getSession(true);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(60 * 60);

            log.info("User '{}' logged in successfully.", userId);

            return "redirect:/index.do";
        } catch (UserNotFoundException e) {
            log.warn("Login failed for user: {}", userId);
            return "redirect:/login.do?error=true";

        }

//        return "shop/main/index";
    }
}
