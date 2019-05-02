package com.littledrawer.me.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.base.net.AuthUtil;
import com.littledrawer.R;
import com.littledrawer.common.view.RoundView;
import com.littledrawer.util.Util;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 土小贵
 * @date 2019/4/22 8:44
 */
public class HeadView extends LinearLayout {

    @BindView(R.id.iv_icon)
    RoundView mIcon;
    @BindView(R.id.tv_user)
    TextView mNickName;

    private OnIconClick mOnIconClick;

    public void setOnIconClick(OnIconClick onIconClick) {
        mOnIconClick = onIconClick;
    }

    public interface OnIconClick {
        public void click(View view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeadView(Context context) {
        super(context);
        initView(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_head, this);
        ButterKnife.bind(this, view);

        if (AuthUtil.getInstance().isLogin()) {
            setUserInfo();
        } else {
            clearUserInfo();
        }

        mIcon.setOnClickListener(v -> {
            if (mOnIconClick != null) {
                mOnIconClick.click(v);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width, height;
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);

        if (modeW == MeasureSpec.UNSPECIFIED) {
            width = Math.min(sizeW, 250);
        } else {
            width = sizeW;
        }

        if (modeH == MeasureSpec.EXACTLY) {
            height = sizeH;
        } else {
            height = Math.min(sizeH, 250);
        }

        setMeasuredDimension(width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setUserInfo() {
        if (AuthUtil.getInstance().isLogin()) {
            Util.glideLoad(getContext(), AuthUtil.getInstance().getIconUrl(),
                    mIcon);
            mNickName.setText(AuthUtil.getInstance().getNickName());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clearUserInfo() {
        if (!AuthUtil.getInstance().isLogin()) {
            mIcon.setImageDrawable(getContext().getDrawable(R.drawable.icon_unlogin));
            mNickName.setText(getContext().getString(R.string.login_by_click_icon));
        }
    }
}
