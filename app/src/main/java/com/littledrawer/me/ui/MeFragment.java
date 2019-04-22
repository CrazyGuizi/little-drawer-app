package com.littledrawer.me.ui;

import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.net.AuthUtil;
import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.common.ui.LoginActivity;
import com.littledrawer.event.EventManager;
import com.littledrawer.event.MessageEvent;
import com.littledrawer.event.UserLoginMessage;
import com.littledrawer.me.view.HeadView;
import com.littledrawer.util.MeMenu;
import com.littledrawer.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 土小贵
 * @date 2019/4/18 17:41
 */
public class MeFragment extends BaseFragment {
    public static MeFragment getInstance() {
        return new MeFragment();
    }

    @BindView(R.id.head_view)
    HeadView mHeadView;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private BaseQuickAdapter mAdapter;
    private List<MeMenu> mMenus = new ArrayList<>();

    // 头像点击监听，未登录状态下跳到登录界面
    private HeadView.OnIconClick mIconClick = new HeadView.OnIconClick() {
        @Override
        public void click(View view) {
            if (!AuthUtil.getInstance().isLogin()) {
                LoginActivity.startActivity(getContext());
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        if (mHeadView != null) {
            mHeadView.setOnIconClick(mIconClick);
        }

        initAdapter();
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * 初始化适配器，因为没写有获取menu的后台接口，这里先写死
     */
    private void initAdapter() {

        if (mMenus == null || mMenus.isEmpty()) {
            mMenus.add(MeMenu.PERSON_INFO.index,MeMenu.PERSON_INFO);
            mMenus.add(MeMenu.MY_NEW.index,MeMenu.MY_NEW);
            mMenus.add(MeMenu.MY_VIDEO.index,MeMenu.MY_VIDEO);
            mMenus.add(MeMenu.MY_PICTURE.index,MeMenu.MY_PICTURE);
            mMenus.add(MeMenu.MY_COLLECTION.index,MeMenu.MY_COLLECTION);
            mMenus.add(MeMenu.LOGOUT.index,MeMenu.LOGOUT);
        }

        if (mAdapter == null) {
            mAdapter = new BaseQuickAdapter<MeMenu, BaseViewHolder>
                    (R.layout.recycler_item_menu, mMenus) {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void convert(BaseViewHolder helper, MeMenu menu) {
                    helper.setImageDrawable(R.id.iv_menu,
                            getContext().getDrawable(menu.src));
                    helper.setText(R.id.tv_menu, menu.name);
                }
            };
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == MeMenu.LOGOUT.index) {
                    Util.logout();
                } else {
                    Toast.makeText(getContext(),
                            "您点击了" + mMenus.get(position).name + "\n该功能未开发",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 监听登录登出
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event instanceof UserLoginMessage) {
            UserLoginMessage userStatus = (UserLoginMessage) event;
            if (userStatus.isLogin) {
                if (userStatus.user != null) {
                    mHeadView.setUserInfo();
                }
            } else {
                mHeadView.clearUserInfo();
            }
        }
    }
}
