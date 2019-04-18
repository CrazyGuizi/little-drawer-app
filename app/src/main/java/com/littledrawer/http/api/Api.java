package com.littledrawer.http.api;

/**
 * @author 土小贵
 * @date 2019/4/17 22:34
 */
public class Api {

    public static final String USER_VALIDATE_USERNAME = "user/validateUsername";
    public static final String USER_LOGIN = "user/login";
    public static final String USER_REGISTER = "user/register";
    public static final String USER_UPDATE_USER = "user/updateUser";

    public static final String NEWS_ADD_NEWS = "news/addNews";
    public static final String NEWS_DELETE_NEWS_BY_ID = "news/deleteNewsById";
    public static final String NEWS_UPDATE_NEWS_BY_ID = "news/updateNewsById";
    public static final String NEWS_GET_NEWS_BY_ID = "news/getNewsById";
    public static final String NEWS_GET_NEWS_BY_COLUMN = "news/getNewsByColumn";
    public static final String NEWS_GET_NEWS_BY_USER_ID = "news/getNewsByUserId";
    public static final String NEWS_GET_NEWS_RANDOM = "news/getNewsRandom";
    public static final String NEWS_SEARCH_NEWS = "news/searchNews";


    public static final String COMMON_ADD_COMMENT = "common/addComment";
    public static final String COMMON_DELETE_COMMENT = "common/deleteComment";
    public static final String COMMON_GET_COMMENTS_BY_TOPIC = "common/getCommentsByTopic";
    public static final String COMMON_ADD_REPLY = "common/addReply";
    public static final String COMMON_DELETE_REPLY = "common/deleteReply";
    public static final String COMMON_GET_REPLYS_BY_COMMENT_ID = "common/getReplysByCommentId";
    public static final String COMMON_ADD_LIKE = "common/addLike";
    public static final String COMMON_SET_LIKE_STATUS = "common/setLikeStatus";
    public static final String COMMON_GET_LIKE_STATUS = "common/getLikeStatus";
    public static final String COMMON_ADD_COLLECTION = "common/addCollection";
    public static final String COMMON_CANCEL_COLLECTION = "common/cancelCollection";
    public static final String COMMON_GET_COLLECTION_STATUS = "common/getCollectionStatus";


    public static final String VIDEO_ADD_VIDEO = "video/addVideo";
    public static final String VIDEO_DELETE_VIDEO = "video/deleteVideo";
    public static final String VIDEO_GET_VIDEO_BY_ID = "video/getVideoById";
    public static final String VIDEO_GET_VIDEOS_BY_USER_ID = "video/getVideosByUserId";
    public static final String VIDEO_GET_VIDEOS_BY_TYPE = "video/getVideosByType";
    public static final String VIDEO_GET_VIDEOS_RANDOM = "video/getVideosRandom";



    public static final String PICTURE_ADD_PICTURE = "picture/addPicture";
    public static final String PICTURE_DELETE_PICTURE = "picture/deletePicture";
    public static final String PICTURE_GET_PICTURE_BY_ID = "picture/getPictureById";
    public static final String PICTURE_GET_FUNNY_PICTURES_RANDOM = "picture/getFunnyPicturesRandom";
    public static final String PICTURE_GET_PICTURES_BY_USER_ID = "picture/getPicturesByUserId";



    public static final String RESOURCE_UPLOAD_PICTURE = "resource/upload/picture";
    public static final String RESOURCE_UPLOAD_VIDEO = "resource/upload/video";
}
