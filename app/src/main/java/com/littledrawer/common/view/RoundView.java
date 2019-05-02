package com.littledrawer.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.littledrawer.R;
import com.littledrawer.util.Util;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author 土小贵
 * @date 2019/4/20 9:15
 */
@SuppressLint("AppCompatCustomView")
public class RoundView extends ImageView {

    private boolean isCircle;
    private int mBorderWidth;
    private int mBorderColor = Color.WHITE;
    private int mCornerRadius;

    private int mMaskColor;
    private Xfermode mXfermode;

    private int mWidth, mHeight;
    private float mRadius;
    private float[] mBorderRadii;
    private float[] mSrcRadii;

    private RectF mSrcRectF;
    private RectF mBorderRectF;

    private Paint mPaint;
    private Path mPath;
    private Path mSrcPath;


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
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundView,
                0, 0);

        isCircle = ta.getBoolean(R.styleable.RoundView_is_circle, true);
        mBorderWidth = ta.getDimensionPixelSize(R.styleable.RoundView_border_width, 0);
        mBorderColor = ta.getColor(R.styleable.RoundView_border_color, mBorderColor);
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.RoundView_corner_radius, 0);
        mMaskColor = ta.getColor(R.styleable.RoundView_mask_color, mMaskColor);
        ta.recycle();

        mBorderRadii = new float[8];
        mSrcRadii = new float[8];
        mBorderRectF = new RectF();
        mSrcRectF = new RectF();
        mPath = new Path();
        mPaint = new Paint();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        } else {
            mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
            mSrcPath = new Path();
        }

        calculateRadii();
    }

    /**
     * 计算RectF的圆角半径
     */
    private void calculateRadii() {
        if (isCircle) {
            return;
        }

        if (mCornerRadius > 0) {
            for (int i = 0; i < mBorderRadii.length; i++) {
                mBorderRadii[i] = mCornerRadius;
                mSrcRadii[i] = mCornerRadius - mBorderWidth / 2.0F;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        initBorderRectF();
        initSrcRectF();
    }

    private void initSrcRectF() {
        if (isCircle) {
            mRadius = Math.min(mWidth, mHeight) / 2.0F;
            mSrcRectF = new RectF(mWidth / 2.0F - mRadius, mHeight / 2.0F - mRadius,
                    mWidth / 2.F + mRadius, mHeight / 2.0F + mRadius);
        } else {
            mSrcRectF = new RectF(0, 0, mWidth, mHeight);
        }
    }

    private void initBorderRectF() {
        if (!isCircle) {
            mBorderRectF.set(mBorderWidth / 2.0F, mBorderWidth / 2.0F,
                    mWidth - mBorderWidth / 2.0F, mHeight - mBorderWidth / 2.0F);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(mSrcRectF, null, Canvas.ALL_SAVE_FLAG);
        mPaint.reset();
        mPath.reset();
        if (isCircle) {
            mPath.addCircle(mWidth / 2.0F, mHeight / 2.0F, mRadius, Path.Direction.CCW);
        } else {
            mPath.addRoundRect(mSrcRectF, mSrcRadii, Path.Direction.CCW);
        }

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(mXfermode);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            canvas.drawPath(mPath, mPaint);
        } else {
            mSrcPath.addRect(mSrcRectF, Path.Direction.CCW);
            mSrcPath.op(mPath, Path.Op.DIFFERENCE);
            canvas.drawPath(mSrcPath, mPaint);
        }

        mPaint.setXfermode(null);

        if (mMaskColor != 0) {
            mPaint.setColor(mMaskColor);
            canvas.drawPath(mPath, mPaint);
        }
        canvas.restore();
        drawerBorder(canvas);
    }

    private void drawerBorder(Canvas canvas) {
        if (isCircle) {
            if (mBorderWidth > 0) {
                drawerCircleBorder(canvas, mBorderWidth, mBorderColor,
                        mRadius - mBorderWidth / 2.0F);
            }
        } else {
            if (mBorderWidth > 0) {
                drawRectFBorder(canvas, mBorderWidth, mBorderColor, mBorderRectF, mBorderRadii);
            }
        }
    }

    private void drawRectFBorder(Canvas canvas, int borderWidth, int borderColor, RectF borderRectF, float[] borderRadii) {
        initBorderPaint(borderWidth, borderColor);
        mPath.addRoundRect(borderRectF, borderRadii, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawerCircleBorder(Canvas canvas, int borderWidth, int borderColor, float radius) {
        initBorderPaint(borderWidth, borderColor);

        mPath.addCircle(mWidth / 2.0F, mHeight / 2.0F, radius, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
    }

    private void initBorderPaint(int borderWidth, int borderColor) {
        mPath.reset();
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(borderColor);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    public void setCircle(boolean circle) {
        isCircle = circle;
        initSrcRectF();
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        mBorderWidth = Util.dp2px(borderWidth);
        calculateRadiiAndRectF(false);

    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        mCornerRadius = cornerRadius;
        calculateRadiiAndRectF(false);
    }

    public void setMaskColor(int maskColor) {
        mMaskColor = maskColor;
        invalidate();
    }

    private void calculateRadiiAndRectF(boolean reset) {
        if (reset) {
            mCornerRadius = 0;
        }

        calculateRadii();
        initBorderRectF();
        invalidate();
    }
}
