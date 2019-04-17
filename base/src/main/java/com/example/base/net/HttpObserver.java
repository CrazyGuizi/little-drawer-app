package com.example.base.net;

import com.example.base.net.exception.BaseException;
import com.example.base.net.response.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 土小贵
 * @date 2019/4/17 17:45
 */
public abstract class HttpObserver<T> implements Observer<BaseResponse<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> response) {
        if (response.getCode() == NetConst.CODE_SUCCESS) {
            doSuccess(response.getData());
        } else {
            doFail(new BaseException(response.getMsg()));
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof Exception) {
            doFail(new BaseException("网络出错"));
        } else {
            doFail(new BaseException("未知错误"));
        }
    }

    @Override
    public void onComplete() {

    }

    // 请求成功
    public abstract void doSuccess(T res);
    public abstract void doFail(BaseException e);

}
