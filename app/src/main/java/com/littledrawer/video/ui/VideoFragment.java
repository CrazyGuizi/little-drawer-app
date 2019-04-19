package com.littledrawer.video.ui;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.util.VideoType;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @author 土小贵
 * @date 2019/4/18 17:37
 */
public class VideoFragment extends BaseFragment {

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private int mCurrentPage = 0;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;


    public static VideoFragment getInstance() {
        return new VideoFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        mFragmentManager = getChildFragmentManager();
        initAdapter();
        initData();
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new FragmentPagerAdapter(mFragmentManager) {
                @Override
                public Fragment getItem(int position) {
                    return mFragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return mFragmentList.size();
                }

                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {
                    return mFragmentTitles.get(position);
                }
            };
        }
    }

    private void initData() {
        mFragmentList.add(VideoType.FUNNY.typeIndex,
                VideoTabFragment.getInstance(VideoType.FUNNY));
        mFragmentList.add(VideoType.GAME.typeIndex,
                VideoTabFragment.getInstance(VideoType.GAME));
        mFragmentList.add(VideoType.LIFE.typeIndex,
                VideoTabFragment.getInstance(VideoType.LIFE));
        mFragmentList.add(VideoType.FILM.typeIndex,
                VideoTabFragment.getInstance(VideoType.FILM));
        mFragmentList.add(VideoType.SCIENCE.typeIndex,
                VideoTabFragment.getInstance(VideoType.SCIENCE));
        mFragmentList.add(VideoType.OTHER.typeIndex,
                VideoTabFragment.getInstance(VideoType.OTHER));

        mFragmentTitles.add(VideoType.FUNNY.typeIndex,VideoType.FUNNY.typeName);
        mFragmentTitles.add(VideoType.GAME.typeIndex,VideoType.GAME.typeName);
        mFragmentTitles.add(VideoType.LIFE.typeIndex,VideoType.LIFE.typeName);
        mFragmentTitles.add(VideoType.FILM.typeIndex,VideoType.FILM.typeName);
        mFragmentTitles.add(VideoType.SCIENCE.typeIndex,VideoType.SCIENCE.typeName);
        mFragmentTitles.add(VideoType.OTHER.typeIndex,VideoType.OTHER.typeName);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
