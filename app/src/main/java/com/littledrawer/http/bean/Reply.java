package com.littledrawer.http.bean;

import java.util.Date;

/**
 * @author 土小贵
 * @date 2019/4/18 9:18
 */
public class Reply {

    private int id;
    private String content;
    private Date date;
    private int commentId;
    // 回复类型，0代表回复评论， 1代表回复回复
    private int replyType;
    // 回复的那个东西的id，如果那东西是回复评论就是评论id，如果是回复那就是回复id
    private int replyId;
    private User fromUser;
    private User toUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getReplyType() {
        return replyType;
    }

    public void setReplyType(int replyType) {
        this.replyType = replyType;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
