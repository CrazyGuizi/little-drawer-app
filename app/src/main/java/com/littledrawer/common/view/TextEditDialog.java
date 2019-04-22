package com.littledrawer.common.view;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.littledrawer.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author 土小贵
 * @date 2019/4/20 14:18
 */
public class TextEditDialog extends DialogFragment {

    public static final String TAG = "TextEditDialog";

    private TextInputEditText mContent;
    private MaterialButton mCancel;
    private MaterialButton mConfirm;

    private OnConfirmListener mListener;

    public void setListener(OnConfirmListener listener) {
        mListener = listener;
    }

    public interface OnConfirmListener {
        void getContent(String content);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_text, container, false);
        mContent = view.findViewById(R.id.et_content);
        mCancel = view.findViewById(R.id.bt_cancel);
        mConfirm = view.findViewById(R.id.bt_confirm);

        mCancel.setOnClickListener(V -> {
            handleCancel();
        });
        mConfirm.setOnClickListener(V -> {
            handleConfirm();
        });
        return view;
    }

    private void handleConfirm() {
        String content = mContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), getString(R.string.please_edit_text),
                    Toast.LENGTH_SHORT).show();
        } else {
            if (mListener != null) {
                mListener.getContent(content);
                dismiss();
            }
        }
    }

    private void handleCancel() {
        dismiss();
    }

}
