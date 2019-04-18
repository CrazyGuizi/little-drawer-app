package com.littledrawer;

import android.view.MenuItem;

import com.littledrawer.common.BaseActivity;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.common.view.BottomNavigationView;
import com.littledrawer.me.ui.MeFragment;
import com.littledrawer.news.view.NewsFragment;
import com.littledrawer.picture.ui.PictureFragment;
import com.littledrawer.util.TopicTag;
import com.littledrawer.video.ui.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragments;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(TopicTag.NEWS.topicIndex, NewsFragment.getInstance());
            mFragments.add(TopicTag.VIDEO.topicIndex, VideoFragment.getInstance());
            mFragments.add(TopicTag.PICTURE.topicIndex, PictureFragment.getInstance());
            mFragments.add(TopicTag.ME.topicIndex, MeFragment.getInstance());
        }
        transaction.add(R.id.container, mFragments.get(TopicTag.NEWS.topicIndex));
        transaction.commit();

    }

    @Override
    public void initEvent() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(mNavSelectedListener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener mNavSelectedListener = new com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    handleSelectedFragment(TopicTag.NEWS);
                    return true;
                case R.id.navigation_video:
                    handleSelectedFragment(TopicTag.VIDEO);
                    return true;
                case R.id.navigation_picture:
                    handleSelectedFragment(TopicTag.PICTURE);
                    return true;
                case R.id.navigation_me:
                    handleSelectedFragment(TopicTag.ME);
                    return true;
            }
            return false;
        }
    };

    private void handleSelectedFragment(TopicTag topicTag) {
        BaseFragment fragment = mFragments.get(topicTag.topicIndex);
        if (fragment == null) {
            if (topicTag.topicIndex == TopicTag.NEWS.topicIndex) {
                fragment = NewsFragment.getInstance();
            } else if (topicTag.topicIndex == TopicTag.VIDEO.topicIndex) {
                fragment = VideoFragment.getInstance();
            } else if (topicTag.topicIndex == TopicTag.PICTURE.topicIndex) {
                fragment = PictureFragment.getInstance();
            } else if (topicTag.topicIndex == TopicTag.ME.topicIndex) {
                fragment = MeFragment.getInstance();
            }

            mFragments.add(topicTag.topicIndex, fragment);
            mFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        } else {
            if (!fragment.isAdded()) {
                mFragmentManager.beginTransaction()
                        .add(R.id.container, fragment)
                        .commit();
            }
            showFragment(topicTag.topicIndex);
        }
    }

    /**
     * 显示选中的fragment
     * @param index
     */
    private void showFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (transaction != null) {
            for (int i = 0; i < mFragments.size(); i++) {
                if (index == i) {
                    transaction.show(mFragments.get(index));
                } else {
                    transaction.hide(mFragments.get(i));
                }
            }
            transaction.commit();
        }
    }
}
