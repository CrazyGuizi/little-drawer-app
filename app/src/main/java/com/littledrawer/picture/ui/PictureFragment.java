package com.littledrawer.picture.ui;

import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;

/**
 * @author 土小贵
 * @date 2019/4/18 17:40
 */
public class PictureFragment extends BaseFragment
{
    public static PictureFragment getInstance() {
        return new PictureFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_picture;
    }
}
