package com.ycbjie.pdlib.dialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;

import com.ycbjie.pdlib.R;


/**
 * 这是一个dialog的基类
 * Created by duanzheng on 2018/1/25.
 */

public abstract class BaseDialog extends DialogFragment {

    private static final String TAG = "base_bottom_dialog";
    private Local local = Local.BOTTOM;
    private final View v;
    private boolean isKeyboardAutoUp = false;
    public enum Local {
        TOP, CENTER, BOTTOM
    }

    private static final float DEFAULT_DIM = 0.2f;


    public BaseDialog(Context context) {
        v = View.inflate(context,getLayoutRes(),null);
        bindView(v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
       // View v = inflater.inflate(getLayoutRes(), container, false);
        if(isKeyboardAutoUp){
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        return v;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (getHeight() > 0) {
            params.height = getHeight();
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if (local == Local.TOP) {
            params.gravity = Gravity.TOP;
        } else if (local == Local.CENTER) {
            params.gravity = Gravity.CENTER;
        } else {
            params.gravity = Gravity.BOTTOM;
        }
        window.setAttributes(params);
    }

    public void setKeyboardAutoUp(boolean isTrue){
        isKeyboardAutoUp=isTrue;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v);



    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return false;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void dialogClose() {
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.cancel();
        }
    }




    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getFragmentTag());
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //yc 添加结束监听
        if(mListenter!=null){
            mListenter.listenter(true);
        }
        if(v!=null){
             ViewParent vp= v.getParent();
                if(vp!=null){
                    ((ViewGroup)vp).removeView(v);
                }
        }
    }


    public onLoadFinishListenter mListenter;
    public void setLoadFinishListenter(onLoadFinishListenter listenter){
        mListenter=listenter;
    }

    public interface onLoadFinishListenter{
        public void listenter(boolean isSuccess);
    }

}
