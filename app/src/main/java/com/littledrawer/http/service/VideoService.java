package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.Video;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/19 13:49
 */
public interface VideoService {

    public static final String TYPE_INDEX = "typeIndex";
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";

    @POST(Api.VIDEO_GET_VIDEO_BY_ID)
    Call<BaseResponse<Video>> getVideoById(@Body Map<String, Integer> map);

    @POST(Api.VIDEO_GET_VIDEOS_BY_USER_ID)
    Call<BaseResponse<List<Video>>> getVideosByUserId(@Body Map<String, Integer> map);

    @POST(Api.VIDEO_GET_VIDEOS_BY_TYPE)
    Call<BaseResponse<List<Video>>> getVideosByType(@Body Map<String, Object> map);

}