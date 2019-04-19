package com.littledrawer.news.ui;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.util.NewsColumn;

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
 * @date 2019/4/18 17:30
 */
public class NewsFragment extends BaseFragment {

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private int mCurrentPage = 0;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_news;
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

    private void initData() {
        mFragmentList.add(NewsColumn.SOCIAL.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.SOCIAL));
          mFragmentList.add(NewsColumn.SCIENCE.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.SCIENCE));
          mFragmentList.add(NewsColumn.LIFE.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.LIFE));
          mFragmentList.add(NewsColumn.ENTERTAINMENT.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.ENTERTAINMENT));
          mFragmentList.add(NewsColumn.AGRICULTURAL.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.AGRICULTURAL));
          mFragmentList.add(NewsColumn.INTERNATIONAL.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.INTERNATIONAL));
          mFragmentList.add(NewsColumn.SPORTS.columnIndex,
                NewsColumnFragment.getInstance(NewsColumn.SPORTS));

          mFragmentTitles.add(NewsColumn.SOCIAL.columnIndex, NewsColumn.SOCIAL.columnName);
          mFragmentTitles.add(NewsColumn.SCIENCE.columnIndex, NewsColumn.SCIENCE.columnName);
          mFragmentTitles.add(NewsColumn.LIFE.columnIndex, NewsColumn.LIFE.columnName);
          mFragmentTitles.add(NewsColumn.ENTERTAINMENT.columnIndex, NewsColumn.ENTERTAINMENT.columnName);
          mFragmentTitles.add(NewsColumn.AGRICULTURAL.columnIndex, NewsColumn.AGRICULTURAL.columnName);
          mFragmentTitles.add(NewsColumn.INTERNATIONAL.columnIndex, NewsColumn.INTERNATIONAL.columnName);
          mFragmentTitles.add(NewsColumn.SPORTS.columnIndex, NewsColumn.SPORTS.columnName);

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
