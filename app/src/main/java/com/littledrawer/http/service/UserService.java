package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/17 18:29
 */
public interface UserService {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NICK_NAME = "nickName";

    @POST(Api.USER_LOGIN)
    Call<BaseResponse<User>> login(@Body Map<String, String> map);

    @POST(Api.USER_REGISTER)
    Call<BaseResponse<User>> register(@Body Map<String, String> map);

    @POST(Api.USER_VALIDATE_USERNAME)
    Call<BaseResponse<User>> validateUsername(@Body Map<String, String> map);

    @POST(Api.USER_UPDATE_USER)
    Call<BaseResponse<User>> updateUserInfo(@Body User user);

}
