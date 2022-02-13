package com.zjq.community.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2022-01-21 19:33
 */
@Data
public class PaginationDTO {
    /*用于处理分页所构建的dto，用于显示整个问题列表以及最下方的分页需要的属性*/
    private List<QuestionDTO> questions;
    private boolean showPrevious; //是否显示前一页
    private boolean showFirstPage; //显示首页
    private boolean showNext; //显示下一页
    private boolean showEndPage; //显示最后一页
    private Integer page; //是否是当前页
    private List<Integer> pages = new ArrayList<>(); //用于展示在前端的页码数组
    private Integer totalPage; //总页数

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        // 对于总页数的计算
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        // 分页的范围控制
        if(page<1){
            page = 1;
        }
        if(page>totalPage){
            page = totalPage;
        }

        this.page = page;
        pages.add(page); //将当前页放入pages中
        // 计算pages中应该包括哪些值 即 页面上分页的数值范围是什么
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page-i);  //前插
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 是否展示前一页和后一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showNext = true;
        }
        if (page == totalPage) {
            showNext = false;
        } else {
            showPrevious = true;
        }

        // 判断是否展示首位页 - 通过判断是否包含首尾页去判断的
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
