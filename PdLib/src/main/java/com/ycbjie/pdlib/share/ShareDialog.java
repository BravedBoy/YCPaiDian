package com.ycbjie.pdlib.share;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycbjie.pdlib.R;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/6/19
 *     desc  : 分享弹窗
 *     revise:
 * </pre>
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private Handler handler;
    private Activity mContext;
    private ShareBean shareBean;
    private TextView mTvImg;
    private TextView mTvWxFriend;
    private TextView mTvWxMoment;
    private FrameLayout mFlCancel;
    private ImageView mIvCancel;

    public ShareDialog(Activity context, ShareBean shareBean) {
        this(context, shareBean, R.style.share_dialog_style);
    }

    private ShareDialog(Activity context, ShareBean shareBean , int theme) {
        super(context, theme);
        this.mContext = context;
        this.shareBean = shareBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.dialog_share_view, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(view,layoutParams);
        //setContentView(view);
        Window window = getWindow();
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.share_dialog_animStyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = mContext.getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            wl.gravity = Gravity.BOTTOM;
            // 设置显示位置
            onWindowAttributesChanged(wl);
        }
        initViews();
    }


    @Override
    public void show() {
        super.show();
        setBackgroundAlpha(mContext,0.5f);
        showDialogAnim();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setBackgroundAlpha(mContext,1.0f);
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }



    private void initViews() {
        mTvImg = (TextView) findViewById(R.id.tv_img);
        mTvWxFriend = (TextView) findViewById(R.id.tv_wx_friend);
        mTvWxMoment = (TextView) findViewById(R.id.tv_wx_moment);
        mFlCancel = (FrameLayout) findViewById(R.id.fl_cancel);
        mIvCancel = (ImageView) findViewById(R.id.iv_cancel);

        mFlCancel.setOnClickListener(this);
        mTvImg.setOnClickListener(this);
        mTvWxFriend.setOnClickListener(this);
        mTvWxMoment.setOnClickListener(this);
    }


    /**
     * 设置页面的透明度
     * 主要作用于：弹窗时设置宿主Activity的背景色
     * @param bgAlpha 1表示不透明
     */
    private void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        Window window = activity.getWindow();
        if(window!=null){
            if (bgAlpha == 1) {
                //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else {
                //此行代码主要是解决在华为手机上半透明效果无效的bug
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            window.setAttributes(lp);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fl_cancel) {
            dismissDialogAnim();
        } else if(i == R.id.tv_img){
            touchDownAnim(mTvImg,mTvWxMoment,mTvWxFriend);
        } else if(i == R.id.tv_wx_moment){
            touchDownAnim(mTvWxMoment,mTvImg,mTvWxFriend);
        } else if(i == R.id.tv_wx_friend){
            touchDownAnim(mTvWxFriend,mTvWxMoment,mTvImg);
        }
    }

    /**
     * 触摸动画
     * @param view1
     * @param view2
     * @param view3
     */
    private void touchDownAnim(final View view1, final View view2, final View view3) {
        view1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // 按下 放大
                        Animation animationDown  = AnimationUtils.loadAnimation(mContext, R.anim.share_touch_down);
                        view1.startAnimation(animationDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.share_touch_up_select_scale);
                        view1.startAnimation(animation);
                        // 其余的view 缩小消失
                        view2.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.share_touch_up_noselect_scale));
                        view3.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.share_touch_up_noselect_scale));
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 弹出对话框，有动画
     */
    private void showDialogAnim() {
        mTvImg.setVisibility(View.GONE);
        mTvWxFriend.setVisibility(View.GONE);
        mTvWxMoment.setVisibility(View.GONE);
        // 菜单按钮动画
        mIvCancel.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.share_rotate_right));
        // 小队分享 要展示三个
        // 选项动画
        showBottomInAnimation(mTvImg);
        delayedBottomInRunnable(100, mTvWxFriend);
        delayedBottomInRunnable(200, mTvWxMoment);
    }


    /**
     * 退出对话框，有动画
     */
    private void dismissDialogAnim() {
        mIvCancel.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.share_rotate_left));
        showBottomOutAnimation(mTvWxMoment);
        delayedBottomOutRunnable(50, mTvWxFriend);
        delayedBottomOutRunnable(100, mTvImg);
        if(isShowing()){
            dismiss();
        }
    }

    /**
     * show 弹出动画
     *
     * @param textView                      textView
     */
    private void showBottomInAnimation(TextView textView) {
        textView.setVisibility(View.VISIBLE);
        textView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.share_bottom_in));
    }

    /**
     * 延时向上的任务
     *
     * @param time                          time
     * @param textView                      textView
     */
    private void delayedBottomInRunnable(int time, final TextView textView) {
        if(handler==null){
            handler = new Handler();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showBottomInAnimation(textView);
            }
        }, time);
    }

    /**
     * show 收回动画
     *
     * @param textView                      textView
     */
    private void showBottomOutAnimation(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
        textView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.share_bottom_out));
    }

    /**
     * 延时向下的任务
     *
     * @param time                          time
     * @param textView                      textView
     */
    private void delayedBottomOutRunnable(int time, final TextView textView) {
        if(handler==null){
            handler = new Handler();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showBottomOutAnimation(textView);
            }
        }, time);
    }


}
