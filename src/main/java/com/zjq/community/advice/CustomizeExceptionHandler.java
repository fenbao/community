package com.zjq.community.advice;

import com.zjq.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @date 2022-02-09 21:01
 */
@ControllerAdvice //想要能够拦截，需要首先加上这个注解，使用@ControllerAdvice来声明一些全局性的东西，最常见的是结合@ExceptionHandler注解用于全局异常的处理。
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
        // 这里让他去捕获所有的异常 @ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        // 这里当出现异常的时候让返回一个页面
        if (e instanceof CustomizeException) {
            model.addAttribute("message",e.getMessage());
        } else {
            model.addAttribute("message", "请稍后再试！");
        }
        return new ModelAndView("error");
    }

}
