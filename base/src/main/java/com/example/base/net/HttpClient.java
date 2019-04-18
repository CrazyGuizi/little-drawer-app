package com.example.base.net;

import com.example.base.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 土小贵
 * @date 2019/4/17 15:32
 */
public class HttpClient {
    private static volatile HttpClient INSTANCE = null;

    private OkHttpClient.Builder mBuilder;

    private HttpClient() {
        initBuilder();
    }

    public static HttpClient getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpClient();
                }
            }
        }

        return INSTANCE;
    }

    public OkHttpClient build() {
        return mBuilder.build();
    }

    private void initBuilder() {
        mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(NetConst.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        mBuilder.readTimeout(NetConst.READ_TIME_OUT, TimeUnit.SECONDS);
        mBuilder.writeTimeout(NetConst.WRITE_TIME_OUT, TimeUnit.SECONDS);
        mBuilder.addNetworkInterceptor(new LoggingInterceptor());

    }

    public class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            // 设置token
            if (AuthUtil.getInstance().isLogin()) {
                request = request.newBuilder()
                        .addHeader(NetConst.HEADER_TOKEN, AuthUtil.getInstance().getToken())
                        .addHeader(NetConst.UID, "" + AuthUtil.getInstance().getUserId())
                        .build();
            }

            // 打印日志
            long t1 = System.nanoTime();
            Log.d(HttpClient.this,
                    String.format("发送请求 %s on %s%n%s",
                            request.url(), chain.connection(), request.headers()));

            long t2 = System.nanoTime();
            Response response = chain.proceed(request);
            Log.d(HttpClient.this,
                    String.format("\n接收响应 %s in %.1fms%n%s",
                            response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }
}
