package com.littledrawer.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.littledrawer.R;
import com.littledrawer.event.MessageEvent;
import com.littledrawer.util.ActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;

/**
 * @author 土小贵
 * @date 2019/4/18 11:00
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity = null;
    // 是否在前台
    protected boolean isActivity = false;
    private WeakReference<Activity> mWeakRefActivity = null;

    protected Toolbar mToolbar = null;

    public abstract int getLayout();

    public String getToolTitle() {
        return "";
    }


    protected void getIntentData(Intent intent) {

    }

    public void initWidget() {
    }

    public void initEvent() {
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        beforeInitWidget();
        setContentView(getLayout());
        mActivity = this;
        mWeakRefActivity = new WeakReference<>(this);
        ActivityManager.add(mWeakRefActivity);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setupToolbar();
        initWidget();
        initEvent();
    }

    protected void beforeInitWidget(){}

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (!TextUtils.isEmpty(getToolTitle())) {
                mToolbar.setTitle(getToolTitle());
            }
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivity = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivity = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    protected void onMessageEvent(MessageEvent event) {

    }
}
