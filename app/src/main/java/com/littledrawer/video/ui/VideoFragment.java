package com.littledrawer.video.ui;

import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;

/**
 * @author 土小贵
 * @date 2019/4/18 17:37
 */
public class VideoFragment extends BaseFragment {

    public static VideoFragment getInstance() {
        return new VideoFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_video;
    }
}
