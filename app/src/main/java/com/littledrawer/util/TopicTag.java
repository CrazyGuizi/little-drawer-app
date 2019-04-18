package com.littledrawer.util;

/**
 * @author 土小贵
 * @date 2019/4/18 17:58
 */
public enum TopicTag {
    NEWS(0, "新闻"),
    VIDEO(1,"视频"),
    PICTURE(2, "图片"),
    ME(3, "我的");

    public int topicIndex;
    public String topicName;

    TopicTag(int topicType, String topicName) {
        this.topicIndex = topicType;
        this.topicName = topicName;
    }
}
