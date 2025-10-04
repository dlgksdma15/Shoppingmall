package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.GET, value = "/signup.do")
public class SignupController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);

        if(session != null && session.getAttribute("user") != null){
            return "redirect:/index.do";
        }

        return "shop/signup/signup_form";
    }
}
