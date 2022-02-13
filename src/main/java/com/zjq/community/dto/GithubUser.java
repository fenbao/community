package com.zjq.community.dto;

import lombok.Data;

/**
 * @date 2022-01-14 17:20
 */
@Data
public class GithubUser {
    // user 信息
    private String name;
    private Long id;
    private String bio; //描述
    private String avatarUrl; //头像的url地址
}
