package com.nhnacademy.shoppingmall.check.common.mvc.view;

import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//todo#6-5 테스트가 통과하도록 ViewResolver를 구현합니다.
class ViewResolverTest {
    final ViewResolver viewResolver = new ViewResolver();

    @Test
    @DisplayName("prefix,postfix initialize")
    void constructor() throws Exception {
        Try<Object> prefixField = ReflectionUtils.tryToReadFieldValue(ViewResolver.class,"prefix",viewResolver);
        Try<Object> postfixField = ReflectionUtils.tryToReadFieldValue(ViewResolver.class,"postfix",viewResolver);

        String prefix = (String) prefixField.get();
        String postfix = (String) postfixField.get();

        assertAll(
                ()-> assertEquals(ViewResolver.DEFAULT_PREFIX,prefix),
                ()-> assertEquals(ViewResolver.DEFAULT_POSTFIX, postfix)
        );
    }

    @Test
    void getPath() {

        String expected = "/WEB-INF/views/main/index.jsp";
        assertAll(
                ()-> assertEquals(expected,viewResolver.getPath("main/index")),
                ()-> assertEquals(expected,viewResolver.getPath("/main/index"))
        );
    }

    @Test
    void isRedirect() {
        assertAll(
                ()-> assertTrue(viewResolver.isRedirect("redirect:/index.do")),
                ()-> assertTrue(viewResolver.isRedirect("REDIRECT:/login.do")),
                ()-> assertTrue(viewResolver.isRedirect("ReDIrECT:/login.do")),
                ()-> assertFalse(viewResolver.isRedirect("/main/index")),
                ()-> assertFalse(viewResolver.isRedirect("/admin/producnt/list"))
        );
    }

    @Test
    void getRedirectUrl() {
        assertAll(
                ()-> assertEquals("/index.do",viewResolver.getRedirectUrl("redirect:/index.do")),
                ()-> assertEquals("/login.do",viewResolver.getRedirectUrl("REDIRECT:/login.do")),
                ()-> assertEquals("/admin/product/list.do", viewResolver.getRedirectUrl("ReDIrECT:/admin/product/list.do"))
        );
    }

    @Test
    void getLayOut() {
        assertAll(
            ()-> assertEquals(ViewResolver.DEFAULT_ADMIN_LAYOUT, viewResolver.getLayOut("/admin/product/list")),
            ()-> assertEquals(ViewResolver.DEFAULT_SHOP_LAYOUT, viewResolver.getLayOut("/mypage/product/list")),
            ()-> assertEquals(ViewResolver.DEFAULT_SHOP_LAYOUT, viewResolver.getLayOut("/main/index"))
        );
    }
}