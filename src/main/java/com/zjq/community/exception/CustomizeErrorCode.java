package com.zjq.community.exception;

/**
 * @date 2022-02-09 21:55
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("要找的问题不在了，换一个试试吧！");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
