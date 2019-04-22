package com.littledrawer.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.base.net.AuthUtil;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.R;
import com.littledrawer.common.view.TextEditDialog;
import com.littledrawer.http.bean.Comment;
import com.littledrawer.http.bean.Reply;
import com.littledrawer.http.bean.User;
import com.littledrawer.http.service.ReplyService;
import com.littledrawer.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 土小贵
 * @date 2019/4/20 10:43
 */
public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {


    private Context mContext;


    public CommentAdapter(List<Comment> data, Context context) {
        super(R.layout.recycler_item_comment, data);
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        bindComment(helper, item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindImage(BaseViewHolder helper, String url, int rId) {

    }

    /**
     * 绑定评论数据
     * @param helper
     * @param comment
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindComment(BaseViewHolder helper, Comment comment) {
        if (comment != null) {
            Util.glideLoad(mContext, comment.fromUser.iconUrl, (ImageView)helper.getView(R.id.iv_icon));
            helper.setText(R.id.tv_from_user, comment.fromUser.nickName);
            helper.setText(R.id.tv_date, Util.transformDate(comment.date));
            helper.setText(R.id.tv_content, comment.content);

            helper.addOnClickListener(R.id.tv_comment);
            if (comment.replys != null) {
                helper.setText(R.id.tv_replys_count, "(" + comment.replys.size() + ")");
            }

            // 没有回复的时候不显示查看回复
            if (comment.replys == null || comment.replys.size() <= 0) {
                helper.setVisible(R.id.tv_see_reply, false);
                helper.setVisible(R.id.tv_replys_count, false);
            } else {
                helper.setVisible(R.id.tv_see_reply, true);
                helper.setVisible(R.id.tv_replys_count, true);
            }

            // 设置回复列表
            RecyclerView recyclerView = helper.getView(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            BaseQuickAdapter<Reply, BaseViewHolder> replyAdapter = new BaseQuickAdapter<Reply, BaseViewHolder>
                    (R.layout.recycler_item_reply, comment.replys) {
                @Override
                protected void convert(BaseViewHolder helper, Reply reply) {
                    bindReply(helper, comment.id, reply);
                }
            };
            recyclerView.setAdapter(replyAdapter);
            // 监听reply的item点击
            replyAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    addReply(false, comment, position);
                }
            });

            // 监听查看回复点击
            helper.getView(R.id.tv_see_reply).setOnClickListener(v -> {
                String sChange = null;
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    sChange = mContext.getResources().getString(R.string.close_reply);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    sChange = mContext.getResources().getString(R.string.see_reply);
                }
                helper.setText(R.id.tv_see_reply,sChange);
            });
        }
    }

    /**
     * 添加回复，可以是回复评论，也可以是回复回复
     * @param isReplyComment 这个回复是否是回复评论
     * @param comment
     * @param position
     */
    public void addReply(boolean isReplyComment, Comment comment, int position) {
        if (!AuthUtil.getInstance().isLogin()) {
            Toast.makeText(mContext,
                    mContext.getString(R.string.text_please_login), Toast.LENGTH_SHORT).show();
        }
        TextEditDialog dialog = new TextEditDialog();
        dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),
                TextEditDialog.TAG);
        dialog.setListener(content -> {
            RetrofitManager manager = RetrofitManager.getInstance();
            Reply addReply = new Reply();
            addReply.setCommentId(comment.id);
            addReply.setContent(content);
            if (isReplyComment) {
                addReply.setReplyType(ReplyService.REPLY_TYPE_COMMENT);
                addReply.setReplyId(comment.id);
            } else {
                addReply.setReplyType(ReplyService.REPLY_TYPE_REPLY);
                addReply.setReplyId(comment.replys.get(position).id);
            }
            User fromUser = new User();
            fromUser.setId(AuthUtil.getInstance().getUserId());
            addReply.setFromUser(fromUser);
            if (isReplyComment) {
                addReply.setToUser(comment.getFromUser());
            } else {
                addReply.setToUser(comment.replys.get(position).fromUser);
            }
            manager.request(manager.getService(ReplyService.class)
                    .addReply(addReply), new BaseListener<Reply>() {
                @Override
                public void onSuccess(Reply reply) {
                    if (reply != null) {
                        if (comment.replys == null) {
                            List<Reply> list = new ArrayList<>();
                            list.add(reply);
                        } else {
                            comment.replys.add(reply);
                        }
                        notifyItemChanged(position);
                        Toast.makeText(mContext,
                                mContext.getString(R.string.comment_success),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFail(BaseException e) {
                    Toast.makeText(mContext,
                            e.msg, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindReply(BaseViewHolder helper, int commentId, Reply reply ) {
        if (reply != null) {
            Util.glideLoad(mContext,reply.fromUser.iconUrl, helper.getView(R.id.iv_icon));
            helper.setText(R.id.tv_from_user, reply.fromUser.nickName);
            String textReply = mContext.getResources().getString(R.string.text_reply);
            helper.setText(R.id.tv_to_user,textReply + reply.toUser.nickName);
            helper.setText(R.id.tv_date, Util.transformDate(reply.date));
            helper.setText(R.id.tv_content, reply.content);
        }
    }
}
