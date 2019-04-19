package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.Comment;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/19 22:28
 */
public interface CommentService {

    public static final String TOPIC_TYPE = "topicType";
    public static final String TOPIC_ID = "topicId";

    @POST(Api.COMMON_ADD_COMMENT)
    Call<BaseResponse<Comment>> addComment(@Body Comment comment);

    @POST(Api.COMMON_GET_COMMENTS_BY_TOPIC)
    Call<BaseResponse<List<Comment>>> getCommentsByTopic(@Body Map<String, String> map);
}
