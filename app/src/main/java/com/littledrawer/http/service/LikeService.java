package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.Like;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/20 15:38
 */
public interface LikeService {

   public static final String TOPIC_TYPE = "topicType";
   public static final String TOPIC_ID = "topicId";
   public static final String USER_ID = "userId";

    @POST(Api.COMMON_GET_LIKE_STATUS)
    Call<BaseResponse<Like>> getLikeStatus(@Body Map<String, Integer> map);

    /**
     * 点赞
     * @param like
     * @return
     */
    @POST(Api.COMMON_ADD_LIKE)
    Call<BaseResponse<Like>> addLike(@Body Like like);

}
