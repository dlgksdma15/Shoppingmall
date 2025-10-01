package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@WebFilter(
        filterName = "loginCheckFilter",
        urlPatterns = "/mypage/*"
)
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("user") == null){
            // 로그인 안됨
            res.sendRedirect(req.getContextPath() + "/login.do");
            return;
        }


        // 로그인 됨 -> 다음 필터/서블릿으로 진행
        chain.doFilter(req,res);

    }
}
