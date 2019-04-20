package com.littledrawer.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author 土小贵
 * @date 2019/4/20 9:15
 */
@SuppressLint("AppCompatCustomView")
public class RoundView extends ImageView {

    private String mUrl;

    public RoundView(Context context) {
        super(context);
        initView(context, null);
    }


    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
    }

}
