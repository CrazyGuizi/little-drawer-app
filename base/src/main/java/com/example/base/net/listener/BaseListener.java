package com.example.base.net.listener;

import com.example.base.net.exception.BaseException;

/**
 * @author 土小贵
 * @date 2019/4/17 21:04
 */
public interface BaseListener<T> {

    public void onSuccess(T t);

    public void onFail(BaseException e);
}
