package com.littledrawer.video.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.littledrawer.R;
import com.littledrawer.common.BaseActivity;
import com.littledrawer.http.bean.Video;

public class VideoDetailActivity extends BaseActivity {

    public static final String ARG_VIDEO = "ARG_VIDEO";
    private Video mVideo;

    public static void startActivity(Context context, Video video) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(ARG_VIDEO, video);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        if (intent != null) {
            mVideo = (Video) intent.getSerializableExtra(ARG_VIDEO);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_video_detail;
    }


}
