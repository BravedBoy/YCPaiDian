package com.ycbjie.ycpaidian.second;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter;
import com.yc.cn.ycbannerlib.banner.inter.OnPageListener;
import com.yc.cn.ycbannerlib.banner.util.SizeUtil;
import com.ycbjie.ycpaidian.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SecondActivity  extends AppCompatActivity{

    private ImageView mIvBlurBackground;
    private BannerView mBanner;
    private BaseBannerPagerAdapter pagerAdapter;
    private Runnable mBlurRunnable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mIvBlurBackground = (ImageView) findViewById(R.id.iv_blur_background);
        mBanner = (BannerView) findViewById(R.id.banner);


        List<Integer> lists = new ArrayList<>();
        lists.add(R.mipmap.bg_autumn_tree_min);
        lists.add(R.mipmap.bg_kites_min);
        lists.add(R.mipmap.bg_lake_min);
        lists.add(R.mipmap.bg_leaves_min);
        mBanner.setHintGravity(1);
        mBanner.setAnimationDuration(1000);
        mBanner.setPlayDelay(2000);
        mBanner.setHintPadding(0, 0, 0, SizeUtil.dip2px(this, 10));
        pagerAdapter = new BaseBannerPagerAdapter(this, lists);
        mBanner.setAdapter(pagerAdapter);
        mBanner.setOnPageListener(new OnPageListener() {
            @Override
            public void onPageChange(int position) {
                if(position>=0){
                    setBlurBackground(position);
                }
            }
        });
        setBlurBackground(0);
    }


    /**
     * 设置模糊背景
     */
    private void setBlurBackground(int pos) {
        //获取轮播图索引pos处的图片
        List<Integer> bitmapHashMap = pagerAdapter.getBitmapHashMap();
        Resources res = getApplicationContext().getResources();
        Bitmap bitmap= BitmapFactory.decodeResource(res, bitmapHashMap.get(pos));
        //质量压缩图片
        Bitmap image = BitmapUtils.compressImage(bitmap);
        final Bitmap blurBitmap = BlurBitmapUtils.getBlurBitmap(
                mIvBlurBackground.getContext(), image, 15);


        /*Bitmap bitmap = null;
        Map<Integer, SoftReference<Bitmap>> imageCache = pagerAdapter.getImageCache();
        SoftReference<Bitmap> softReference = imageCache.get(pos);
        if(softReference.get()!=null){
            bitmap = softReference.get();
        }*/


        if (blurBitmap != null) {
            if (mBlurRunnable != null) {
                mIvBlurBackground.removeCallbacks(mBlurRunnable);
            }
            mBlurRunnable = new Runnable() {
                @Override
                public void run() {
                    ViewSwitchUtils.startSwitchBackgroundAnim(mIvBlurBackground, blurBitmap);
                }
            };
            mIvBlurBackground.postDelayed(mBlurRunnable, 100);
        }
    }



    public class BaseBannerPagerAdapter extends AbsStaticPagerAdapter {

        private Context ctx;
        private List<Integer> list;

        Map<Integer, SoftReference<Bitmap>> imageCache = new HashMap<>();

        BaseBannerPagerAdapter(Context ctx, List<Integer> list) {
            this.ctx = ctx;
            this.list = list;
        }


        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(ctx);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //加载图片
            if(list!=null){
                imageView.setBackgroundResource(list.get(position));

                /*Resources res = ctx.getResources();
                Bitmap bitmap= BitmapFactory.decodeResource(res, list.get(position));
                //质量压缩图片
                Bitmap image = BitmapUtils.compressImage(bitmap);
                SoftReference<Bitmap> softReference = new SoftReference<>(image);
                imageCache.put(position,softReference);*/
            }
            return imageView;
        }

        @Override
        public int getCount() {
            return list==null ? 0 : list.size();
        }


        public List<Integer> getBitmapHashMap() {
            return list;
        }

        public Map<Integer, SoftReference<Bitmap>> getImageCache(){
            return imageCache;
        }

    }




}
