package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/17 18:29
 */
public interface UserService {

    @POST(Api.USER_LOGIN)
    Call<BaseResponse<User>> test(@Body Map<String, String> map);

}
