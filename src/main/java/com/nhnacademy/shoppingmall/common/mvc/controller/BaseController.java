package com.nhnacademy.shoppingmall.common.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BaseController {
    String execute(HttpServletRequest req, HttpServletResponse resp);
}