package com.nhnacademy.shoppingmall.common.mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface BaseController {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}