package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import com.nhnacademy.shoppingmall.model.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.model.user.service.UserService;
import com.nhnacademy.shoppingmall.model.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/delete.do")
public class UserDeleteController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            userService.deleteUser(user.getUserId());

            // 세션 무효화
            session.invalidate();

            log.info("User deleted: userId={}", user.getUserId());

            return "redirect:/index.do?deleted=true";
        } catch (Exception e) {
            log.error("Failed to delete user: {}", e.getMessage());
            return "redirect:/mypage/index.do?error=delete_failed";
        }
    }
}