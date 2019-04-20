package com.littledrawer.picture.ui;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.net.AuthUtil;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.http.bean.Collection;
import com.littledrawer.http.bean.Like;
import com.littledrawer.http.bean.Picture;
import com.littledrawer.http.bean.User;
import com.littledrawer.http.service.CollectionService;
import com.littledrawer.http.service.LikeService;
import com.littledrawer.http.service.PictureService;
import com.littledrawer.util.TopicTag;
import com.littledrawer.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * @author 土小贵
 * @date 2019/4/18 17:40
 */
public class PictureFragment extends BaseFragment {
    public static PictureFragment getInstance() {
        return new PictureFragment();
    }

    // 请求分页
    private int pageNum = 1;
    private static final int pageSize = 20;

    private List<Picture> mPictures = new ArrayList<>();
    private BaseQuickAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;


    @Override
    public int getLayout() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.title_picture));
        }

        initAdapter();

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        // 请求刷新事件
        mRefreshLayout.setOnRefreshListener(() -> {
            requestPicture();
        });

        requestPicture();
    }

    /**
     * 请求图片数据
     */
    private void requestPicture() {
        if (mRefreshLayout.isRefreshing()) {
            return;
        }
        mRefreshLayout.setRefreshing(true);
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put(PictureService.PAGE_NUM, "" + pageNum);
        map.put(PictureService.PAGE_SIZE, "" + pageSize);
        manager.request(manager.getService(PictureService.class)
                .getFunnyPicturesRandom(map), new BaseListener<List<Picture>>() {
            @Override
            public void onSuccess(List<Picture> pictures) {
                mRefreshLayout.setRefreshing(false);
                if (pictures != null) {
                    mPictures = pictures;
                    mAdapter.setNewData(pictures);
                }
            }

            @Override
            public void onFail(BaseException e) {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseQuickAdapter<Picture, BaseViewHolder>(R.layout.recycler_item_picture, null) {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void convert(BaseViewHolder helper, Picture picture) {
                    if (picture != null) {
                        Util.glideLoad(getContext(), picture.url,
                                helper.getView(R.id.iv_picture));
                        Util.glideLoad(getContext(), picture.author.iconUrl,
                                helper.getView(R.id.iv_icon));
                        helper.setText(R.id.tv_author, picture.author.nickName);
                        helper.setText(R.id.tv_like, "" + picture.like);
                        helper.setText(R.id.tv_collect, "" + picture.collections);

                        // 如果用户已经登录，则去查看该用户是否已经点赞过或收藏过这个图片
                        if (AuthUtil.getInstance().isLogin()) {
                            getLikeStatus(picture, (status) -> {
                                        ImageView view = helper.getView(R.id.iv_like);
                                        view.setTag(status);
                                        if (status) {
                                            view.setImageDrawable(getContext().getDrawable(R.drawable.icon_like));
                                            // 不允许取消赞
                                            view.setClickable(false);
                                        } else {
                                            view.setImageDrawable(getContext().getDrawable(R.drawable.icon_unlike));
                                            view.setClickable(true);
                                        }
                                    });


                            getCollectionStatus(picture, (status) -> {
                                        ImageView view = helper.getView(R.id.iv_collect);
                                        // 保存tag，收藏操作
                                        view.setTag(status);
                                        if (status) {
                                            // 收藏过了
                                            view.setImageDrawable(getContext().getDrawable(R.drawable.icon_collected));
                                        } else {
                                            // 没收藏
                                            view.setImageDrawable(getContext().getDrawable(R.drawable.icon_uncollected));
                                        }
                                    });
                        }

                        // 设置点击监听
                        helper.addOnClickListener(R.id.iv_like);
                        helper.addOnClickListener(R.id.iv_collect);
                    }
                }
            };
        }


        /**
         * 监听用户点赞和收藏行为
         */
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!AuthUtil.getInstance().isLogin()) {
                    Toast.makeText(getContext(), getString(R.string.text_please_login), Toast.LENGTH_SHORT).show();
                } else {
                    if (view.getId() == R.id.iv_like) {
                        handleLike(position);
                    } else if (view.getId() == R.id.iv_collect) {
                        handleCollect(position);
                    }
                }
            }
        });
    }

    /**
     * 用户收藏行为
     * @param position
     */
    private void handleCollect(int position) {
        if (mAdapter != null) {
            ImageView collectImage = (ImageView) mAdapter.getViewByPosition(position, R.id.iv_collect);
            boolean hasCollected = false;
            if (collectImage.getTag() != null) {
                hasCollected = (boolean)collectImage.getTag();
            }

            RetrofitManager manager = RetrofitManager.getInstance();

            if (hasCollected) {
                // 取消收藏
                HashMap<String, Integer> map = new HashMap<>();
                map.put(CollectionService.TOPIC_TYPE, TopicTag.PICTURE.topicIndex);
                map.put(CollectionService.TOPIC_ID, mPictures.get(position).id);
                map.put(CollectionService.USER_ID, AuthUtil.getInstance().getUserId());

                manager.request(manager.getService(CollectionService.class)
                        .cancelCollection(map), new BaseListener<Collection>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Collection collection) {
                        // 取消成功
                        collectImage.setImageDrawable(getContext().getDrawable(R.drawable.icon_uncollected));
                        collectImage.setTag(false);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFail(BaseException e) {
                        // 取消失败
                        collectImage.setImageDrawable(getContext()
                                .getDrawable(R.drawable.icon_collected));
                        Toast.makeText(getContext(),
                                getString(R.string.text_cancel_collect_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // 收藏图片

                Collection collection = new Collection();
                collection.setTopicType(TopicTag.PICTURE.topicIndex);
                collection.setTopicId(mPictures.get(position).id);
                User collector = new User();
                collection.setId(AuthUtil.getInstance().getUserId());
                collection.setCollector(collector);
                manager.request(manager.getService(CollectionService.class)
                        .addCollection(collection), new BaseListener<Collection>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Collection collection) {
                        if (collection != null) {
                            // 收藏成功
                            collectImage.setImageDrawable(getContext()
                                    .getDrawable(R.drawable.icon_collected));
                            collectImage.setTag(true);
                        } else {
                            // 收藏失败
                            collectImage.setImageDrawable(getContext()
                                    .getDrawable(R.drawable.icon_uncollected));
                            Toast.makeText(getContext(), getString(R.string.text_collect_fail), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFail(BaseException e) {
                        collectImage.setImageDrawable(getContext()
                                .getDrawable(R.drawable.icon_uncollected));
                        Toast.makeText(getContext(), e.msg, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }

    /**
     * 用户点赞行为
     * @param position
     */
    private void handleLike(int position) {
        if (mAdapter != null) {
            ImageView likeImage = (ImageView) mAdapter.getViewByPosition(position, R.id.iv_like);
            boolean hasLiked = false;
            if (likeImage.getTag() != null) {
                hasLiked = (boolean)likeImage.getTag();
            }

            if (hasLiked) {
                Toast.makeText(getContext(), getString(R.string.text_have_liked), Toast.LENGTH_SHORT).show();
            } else {
                RetrofitManager manager = RetrofitManager.getInstance();
                Like like = new Like();
                like.setTopicType(TopicTag.PICTURE.topicIndex);
                like.setTopicId(mPictures.get(position).id);
                like.setStatus(1);
                manager.request(manager.getService(LikeService.class)
                        .addLike(like), new BaseListener<Like>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Like like) {
                        likeImage.setImageDrawable(getContext().getDrawable(R.drawable.icon_like));
                        likeImage.setTag(false);
                    }

                    @Override
                    public void onFail(BaseException e) {
                        Toast.makeText(getContext(), e.msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    /**
     * 点赞状态、收藏状态的回调
     */
    private interface onStatusListener {
        void getStatus(boolean status);
    }

    /**
     * 请求后台查看该用户是否收藏过了图片
     *
     * @param picture
     * @param listener 回调，true为已经收藏，false为未收藏
     */
    private void getCollectionStatus(Picture picture, onStatusListener listener) {
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, Integer> map = new HashMap<>();
        map.put(CollectionService.TOPIC_TYPE, TopicTag.PICTURE.topicIndex);
        map.put(CollectionService.TOPIC_ID, picture.id);
        map.put(CollectionService.USER_ID, AuthUtil.getInstance().getUserId());

        manager.request(manager.getService(CollectionService.class)
                .getCollectionStatus(map), new BaseListener<Collection>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(Collection collection) {
                if (collection != null) {
                    if (listener != null) {
                        listener.getStatus(true);
                    }
                } else {
                    if (listener != null) {
                        listener.getStatus(false);
                    }
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFail(BaseException e) {
                if (listener != null) {
                    listener.getStatus(false);
                }
            }
        });
    }

    /**
     * 请求后台查看该用户是否给这个图片点过赞
     */
    private void getLikeStatus(Picture picture, onStatusListener listener) {
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, Integer> map = new HashMap<>();
        map.put(LikeService.TOPIC_TYPE, TopicTag.PICTURE.topicIndex);
        map.put(LikeService.TOPIC_ID, picture.id);
        map.put(LikeService.USER_ID, AuthUtil.getInstance().getUserId());

        manager.request(manager.getService(LikeService.class)
                .getLikeStatus(map), new BaseListener<Like>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(Like like) {
                if (listener != null) {
                    if (like.status == 0) {
                        listener.getStatus(false);
                    } else {
                        listener.getStatus(true);
                    }
                }
            }

            @Override
            public void onFail(BaseException e) {
                if (listener != null) {
                    listener.getStatus(false);
                }
            }
        });
    }
}
