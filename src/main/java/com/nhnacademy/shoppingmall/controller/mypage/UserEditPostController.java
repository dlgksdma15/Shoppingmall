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

// 회원 정보 수정 POST
@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/edit.do")
public class UserEditPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String userBirth = req.getParameter("userBirth");

        // 수정된 사용자 정보 생성
        User updatedUser = new User(
                currentUser.getUserId(),
                userName,
                userPassword,
                userBirth,
                currentUser.getUserAuth(),
                currentUser.getUserPoint(),
                currentUser.getCreatedAt(),
                currentUser.getLatestLoginAt()
        );

        userService.updateUser(updatedUser);

        // 세션 업데이트
        session.setAttribute("user", updatedUser);

        log.info("User updated: userId={}", currentUser.getUserId());

        return "redirect:/mypage/index.do";
    }
}