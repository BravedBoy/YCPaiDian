package com.ycbjie.pdlib.edittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ycbjie.pdlib.R;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/06/12
 *     desc  : 支持带有删除的editText
 *     revise:
 * </pre>
 */
public class EditTextAndDel extends AppCompatEditText {


    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;

    public EditTextAndDel(Context context) {
        super(context);
        init();
    }

    public EditTextAndDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextAndDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 如果有任何子类想知道，则在更改文本时调用此方法。
     * @param text                      TextView显示的文本
     * @param start                     文本范围开始的偏移量
     * @param lengthBefore              已替换的原文本的长度
     * @param lengthAfter               替换修改文本的长度
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        boolean focus = hasFocus();
        setClearIconVisible(focus && length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //返回左侧、顶部、右侧和底部边框的可绘制项
                //获取右边drawable
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if(drawable != null){
                    int i = getWidth() - getPaddingRight();
                    int width = drawable.getBounds().width();
                    if (event.getX() <= i && event.getX() >= (i - width)) {
                        setText("");
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    private void init() {
        mClearDrawable = getResources().getDrawable(R.drawable.icon_delete_black);
        Bitmap bitmap = ((BitmapDrawable)mClearDrawable).getBitmap();
        Bitmap bitmapScale = getBitmap(bitmap, dip2px(getContext(), 13),
                dip2px(getContext(), 13));
        mClearDrawable = new BitmapDrawable(getResources(),bitmapScale);

    }

    private void setClearIconVisible(boolean isVisible) {
        Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
        Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
        Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop
                ,isVisible ? mClearDrawable : null,drawableBottom);
    }

    public Bitmap getBitmap(Bitmap bitmap,int width,int height) {
        //实际的大小
        int totalWidth = bitmap.getWidth();
        int totalHeight = bitmap.getHeight();
        //计算缩放比例
        float scaleWidth = (float) width/totalWidth ;
        float scaleHeight = (float) height/totalHeight ;
        //Matrix
        Matrix matrix = new Matrix();
        //提交缩放
        matrix.postScale(scaleWidth, scaleHeight);
        //得到缩放后的图片
        Bitmap bitmapResult = Bitmap.createBitmap(bitmap,
                0,0,totalWidth,totalHeight,matrix,true);
        return bitmapResult ;
    }


    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
