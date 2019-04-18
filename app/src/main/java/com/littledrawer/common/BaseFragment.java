package com.littledrawer.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;


/**
 * @author 土小贵
 * @date 2019/4/18 11:07
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract int getLayout();
    protected void initWidget(View view){}
    protected void initEvent(){}
    protected void getBundleData(Bundle bundle){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getBundleData(getArguments());
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        initWidget(view);
        initEvent();
        Log.d(this, "创建fragment");
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
