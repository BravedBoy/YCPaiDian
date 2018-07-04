package com.ycbjie.pdlib.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.ycbjie.pdlib.R;

public class ARoundImageView extends AppCompatImageView {

    /*
     *   Paint：画笔
     *   Canvas：画布
     *   Matrix：变换矩阵
     */

    /**
     * 圆形模式
     */
    private static final int MODE_CIRCLE = 1;
    /**
     * 普通模式
     */
    private static final int MODE_NONE = 0;
    /**
     * 圆角模式
     */
    private static final int MODE_ROUND = 2;
    /**
     * 圆角半径
     */
    private int currRound = dp2px(10);
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 默认是普通模式
     */
    private int currMode = 0;

    public ARoundImageView(Context context) {
        this(context,null);
    }

    public ARoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ARoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(context, attrs, defStyleAttr);
        initViews();
    }

    private void obtainStyledAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ARoundImageView, defStyleAttr, 0);
        currMode = a.hasValue(R.styleable.ARoundImageView_type) ? a.getInt(R.styleable.ARoundImageView_type, MODE_NONE) : MODE_NONE;
        currRound = a.hasValue(R.styleable.ARoundImageView_radius) ? a.getDimensionPixelSize(R.styleable.ARoundImageView_radius, currRound) : currRound;
        a.recycle();
    }

    private void initViews() {
        //ANTI_ALIAS_FLAG 用于绘制时抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }


    /**
     * 当模式为圆形模式的时候，我们强制让宽高一致
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currMode == MODE_CIRCLE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int result = Math.min(getMeasuredHeight(), getMeasuredWidth());
            setMeasuredDimension(result, result);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        Matrix mDrawMatrix = getImageMatrix();
        if (mDrawable == null) {
            return;
        }
        if (mDrawable.getIntrinsicWidth() == 0 || mDrawable.getIntrinsicHeight() == 0) {
            return;
        }

        if (mDrawMatrix == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
            mDrawable.draw(canvas);
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (getCropToPadding()) {
                    final int scrollX = getScrollX();
                    final int scrollY = getScrollY();
                    canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                            scrollX + getRight() - getLeft() - getPaddingRight(),
                            scrollY + getBottom() - getTop() - getPaddingBottom());
                }
            }
            canvas.translate(getPaddingLeft(), getPaddingTop());
            switch (currMode){
                case MODE_CIRCLE:
                    Bitmap bitmap1 = drawable2Bitmap(mDrawable);
                    mPaint.setShader(new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
                    break;
                case MODE_ROUND:
                    Bitmap bitmap2 = drawable2Bitmap(mDrawable);
                    mPaint.setShader(new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                    canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom()),
                            currRound, currRound, mPaint);
                    break;
                case MODE_NONE:
                default:
                    if (mDrawMatrix != null) {
                        canvas.concat(mDrawMatrix);
                    }
                    mDrawable.draw(canvas);
                    break;
            }
            canvas.restoreToCount(saveCount);
        }
    }

    /**
     * drawable转换成bitmap
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //根据传递的scaleType获取matrix对象，设置给bitmap
        Matrix matrix = getImageMatrix();
        if (matrix != null) {
            canvas.concat(matrix);
        }
        drawable.draw(canvas);
        return bitmap;
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, getResources().getDisplayMetrics());
    }
}
