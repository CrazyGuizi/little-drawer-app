package com.example.base.net;


import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.example.base.net.response.BaseResponse;
import com.example.base.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 土小贵
 * @date 2019/4/17 16:55
 */
public class RetrofitManager {

    private static volatile RetrofitManager INSTANCE = null;
    private static Retrofit mRetrofit;

    private RetrofitManager() {
        initRetrofit();
    }

    /**
     * 初始化Retrofit配置
     *
     * @return
     */
    public Retrofit initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(NetConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HttpClient.getInstance().build());
        mRetrofit = builder.build();
        return mRetrofit;
    }

    public static RetrofitManager getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitManager();
                }
            }
        }

        return INSTANCE;
    }

    public <T> T getService(final Class<T> service) {
        return mRetrofit.create(service);
    }


    public <T> void request(Call<BaseResponse<T>> call, final BaseListener<T> listener) {
        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
                if (listener != null) {
                    if (response.body().getCode() == NetConst.CODE_SUCCESS) {
                        listener.onSuccess(response.body().getData());
                    } else {
                        listener.onFail(new BaseException(response.body().getMsg()));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
                if (listener != null) {
                    listener.onFail(new BaseException("网络出错"));
                    Log.d(this, t.toString());
                }
            }
        });

    }

    /**
     * 不知道为什么，Retrofix+Rxjava一直不起作用，找不到原因，放弃治疗
     */
//    public void request(Observable observable, Observer observer) {
//        if (observable != null && observer != null) {
//            observable.subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe(observer);
//        }
//    }

}
