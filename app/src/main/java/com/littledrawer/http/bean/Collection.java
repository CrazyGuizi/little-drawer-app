package com.littledrawer.http.bean;

import java.util.Date;

/**
 * 我的收藏
 *
 * @author 土小贵
 * @date 2019/4/18 9:19
 */
public class Collection {

    private int id;
    private Date date;
    private int topicType;
    private int topicId;
    private User collector;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public User getCollector() {
        return collector;
    }

    public void setCollector(User collector) {
        this.collector = collector;
    }
}
