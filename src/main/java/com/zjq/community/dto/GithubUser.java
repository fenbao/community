package com.zjq.community.dto;

/**
 * @date 2022-01-14 17:20
 */
public class GithubUser {
    // user 信息
    private String name;
    private Long id;
    private String bio; //描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
