package com.littledrawer.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.littledrawer.R;

import androidx.annotation.RequiresApi;

/**
 * @author 土小贵
 * @date 2019/4/19 23:36
 */
public class NoContentView extends View {

    private Paint mTextPaint;
    private Paint mBitmapPaint;
    private String defaultColor = "#757575";
    private int mColor;
    private String mContent;
    private Bitmap mBitmap;

    private int mWidth;
    private int mHeight;
    private int mBitWidth, mBitHeight;

    private Rect mSrcRect, mDestRect;

    public NoContentView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mContent = getResources().getString(R.string.text_no_content);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture_404);
        mBitWidth = mBitmap.getWidth();
        mBitHeight = mBitmap.getHeight();

        mSrcRect = new Rect();
        mTextPaint = new Paint();
        mBitmapPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mBitmapPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mBitmapPaint.setDither(true);

        mColor = Color.parseColor(defaultColor);
        mTextPaint.setColor(mColor);
        mBitmapPaint.setFilterBitmap(true);
    }

    public NoContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NoContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = measureSize(widthMeasureSpec);
        mHeight = measureSize(heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    private int measureSize(int spec) {
        int result = 0;
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.max(mBitHeight, mBitWidth);
                result = Math.min(result, size);
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mContent, 0, 0, mTextPaint);
//        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mBitmapPaint);
    }
}
