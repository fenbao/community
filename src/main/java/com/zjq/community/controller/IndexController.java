package com.zjq.community.controller;

import com.zjq.community.dto.PaginationDTO;
import com.zjq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @date 2022-01-13 12:45
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){
        // 默认第一页，每页五个元素

        System.out.println("测试该方法是否被触发");
        // List<QuestionDTO> questionList = questionService.list(page,size); // 获取问题以及用户信息
        PaginationDTO pagination = questionService.list(page,size); // 获取问题以及用户信息还有最后的分页信息
        model.addAttribute("pagination",pagination); //将问题列表添加到model中
        return "index";
    }
}
