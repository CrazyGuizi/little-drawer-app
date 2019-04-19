package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.News;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/17 23:09
 */
public interface NewsService {

    public static final String COLUMN = "column";
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";

    @POST(Api.NEWS_GET_NEWS_BY_ID)
    Call<BaseResponse<News>> getNewsById(@Body Map<String, Integer> map);

    @POST(Api.NEWS_GET_NEWS_BY_COLUMN)
    Call<BaseResponse<List<News>>> getNewsByColumn(@Body Map<String, Object> map);


    @POST(Api.NEWS_GET_NEWS_BY_USER_ID)
    Call<BaseResponse<List<News>>> getNewsByUserId(@Body Map<String, String> map);


    @POST(Api.NEWS_GET_NEWS_RANDOM)
    Call<BaseResponse<List<News>>> getNewsRandom(@Body Map<String, String> map);

    @POST(Api.NEWS_SEARCH_NEWS)
    Call<BaseResponse<List<News>>> searchNews(@Body Map<String, String> map);

}
