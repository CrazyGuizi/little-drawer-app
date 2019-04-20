package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.Picture;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/20 18:05
 */
public interface PictureService {

    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";

    @POST(Api.PICTURE_GET_FUNNY_PICTURES_RANDOM)
    Call<BaseResponse<List<Picture>>> getFunnyPicturesRandom(@Body Map<String, String> map);
}
