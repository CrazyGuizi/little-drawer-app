package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.Reply;
import com.littledrawer.util.Const;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/22 21:25
 */
public interface ReplyService {

    public static final String COMMENT_ID = "commentId";
    public static final int REPLY_TYPE_COMMENT = 0;
    public static final int REPLY_TYPE_REPLY = 1;

    @POST(Api.COMMON_ADD_REPLY)
    Call<BaseResponse<Reply>> addReply(@Body Reply reply);


    @POST(Api.COMMON_GET_REPLYS_BY_COMMENT_ID)
    Call<BaseResponse<List<Reply>>> getReplysByCommentId(@Body Map<String, String> map);

}
