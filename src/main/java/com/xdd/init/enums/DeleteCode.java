package com.xdd.init.enums;


public enum DeleteCode {

    DELETE(1, "未删除"),
    NO_DELETE(0, "删除");

    private final int code;
    private final String message;

    DeleteCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 获取状态码
    public int getCode() {
        return code;
    }

    // 获取消息
    public String getMessage() {
        return message;
    }
}
