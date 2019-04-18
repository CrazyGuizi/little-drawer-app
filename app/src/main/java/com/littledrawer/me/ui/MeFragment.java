package com.littledrawer.me.ui;

import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;

/**
 * @author 土小贵
 * @date 2019/4/18 17:41
 */
public class MeFragment extends BaseFragment {
    public static MeFragment getInstance() {
        return new MeFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_me;
    }
}
