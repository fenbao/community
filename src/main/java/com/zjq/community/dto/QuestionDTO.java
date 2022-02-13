package com.zjq.community.dto;

import com.zjq.community.model.User;
import lombok.Data;

/**
 * @date 2022-01-20 22:34
 */
@Data
public class QuestionDTO {
    /*用于question和user之间的关联  因为最后在index页面上需要显示头像信息，所以一个单独的question表中信息不够
    * 需要加上user表的信息，所以构建了这个dto的类*/
    private int id;
    private String title;
    private String description;
    private String tag;
    private Long gmt_create;
    private Long gmt_modified;
    private int creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private User user;
}
