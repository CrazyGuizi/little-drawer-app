package com.littledrawer.http.service;

import com.example.base.net.response.BaseResponse;
import com.littledrawer.http.api.Api;
import com.littledrawer.http.bean.Collection;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/20 17:52
 */
public interface CollectionService {

    public static final String TOPIC_TYPE = "topicType";
    public static final String TOPIC_ID = "topicId";
    public static final String USER_ID = "userId";
    public static final String COLLECTION_ID = "collectionId";


    @POST(Api.COMMON_GET_COLLECTION_STATUS)
    Call<BaseResponse<Collection>> getCollectionStatus(@Body Map<String, Integer> map);

    @POST(Api.COMMON_ADD_COLLECTION)
    Call<BaseResponse<Collection>> addCollection(@Body Collection collection);

    @POST(Api.COMMON_CANCEL_COLLECTION)
    Call<BaseResponse<Collection>> cancelCollection(@Body Map<String, Integer> map);
}
