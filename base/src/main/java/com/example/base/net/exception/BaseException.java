package com.example.base.net.exception;

/**
 * @author 土小贵
 * @date 2019/4/17 17:49
 */
public class BaseException extends Exception {
    public String msg;

    public BaseException(String msg) {
        this.msg = msg;
    }
}
