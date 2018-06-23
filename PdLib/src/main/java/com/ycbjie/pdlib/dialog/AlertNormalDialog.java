package com.ycbjie.pdlib.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ycbjie.pdlib.R;


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/6/9.
 *     desc  : 购物车中自定义弹窗
 *     revise:
 * </pre>
 */
@SuppressLint("ValidFragment")
public class AlertNormalDialog extends BaseDialog implements View.OnClickListener {


    private View.OnClickListener okClick;
    private View.OnClickListener cancelClick;

    private TextView mTvTitle;
    private TextView mTvContext;
    private TextView mTvCancel;
    private TextView mTvSure;

    public AlertNormalDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_noraml_view;
    }

    @Override
    public void bindView(View v) {
        mTvTitle = v.findViewById(R.id.tv_title);
        mTvContext = v.findViewById(R.id.tv_context);
        mTvCancel = v.findViewById(R.id.tv_cancel);
        mTvSure = v.findViewById(R.id.tv_sure);

        setLocal(Local.CENTER);

        mTvSure.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_sure) {
            if (okClick != null) {
                okClick.onClick(v);
            }

        } else if (i == R.id.tv_cancel) {
            if (cancelClick != null) {
                cancelClick.onClick(v);
            }

        }
    }


    public void setTitle(String text){
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(text);
    }

    public void setLeftText(String text){
        mTvCancel.setText(text);
        mTvCancel.setVisibility(View.VISIBLE);
    }

    public void setLeftText(Context context , String text, int color){
        mTvCancel.setText(text);
        mTvCancel.setTextColor(ContextCompat.getColor(context, color));
    }


    public void setRightText(String text){
        mTvSure.setVisibility(View.VISIBLE);
        mTvSure.setText(text);
    }


    public void setRightText(Context context , String text,int color){
        mTvSure.setText(text);
        mTvSure.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setContentText(Context context , String text,int color){
        mTvContext.setVisibility(View.VISIBLE);
        mTvContext.setText(text);
        mTvContext.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setContentText(String text){
        mTvContext.setVisibility(View.VISIBLE);
        mTvContext.setText(text);
    }


    public void setLeftClick(View.OnClickListener click){
        cancelClick = click;
    }

    public void setRightClick(View.OnClickListener click){
        okClick = click;
    }


}
