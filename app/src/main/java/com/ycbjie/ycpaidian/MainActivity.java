package com.ycbjie.ycpaidian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ycbjie.pdlib.amount.ShopAmountView;
import com.ycbjie.pdlib.badge.BadgeView;
import com.ycbjie.pdlib.dialog.fragment.AlertNormalDialog;
import com.ycbjie.pdlib.dialog.fragment.BaseDialog;
import com.ycbjie.pdlib.dialog.dialog.LoadDialog;
import com.ycbjie.pdlib.dialog.pop.CustomPopupWindow;
import com.ycbjie.pdlib.edittext.EditTextAndDel;
import com.ycbjie.pdlib.share.ShareDialog;
import com.ycbjie.pdlib.sku.SkuDialog;
import com.ycbjie.ycpaidian.second.SecondActivity;
import com.ycbjie.ycpaidian.service.LogService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShopAmountView av_view;
    private ShopAmountView av_view2;
    private ShopAmountView av_view3;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_8;
    private CustomPopupWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        av_view = findViewById(R.id.av_view);
        av_view2 = findViewById(R.id.av_view2);
        av_view3 = findViewById(R.id.av_view3);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);
        tv_5 = findViewById(R.id.tv_5);
        tv_6 = findViewById(R.id.tv_6);
        tv_8 = findViewById(R.id.tv_8);
        initAmountView();
        initBadgeView();
        initEditTextAndDel();

        Intent stateService =  new Intent (this,LogService.class);
        startService( stateService );
    }

    private void initListener() {
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_8.setOnClickListener(this);
    }

    /**
     * 购物车自定义加减控件
     */
    private void initAmountView() {
        av_view.setAmountNum(2,10,1);
        int amount = av_view.getAmount();
        av_view.setEtClickable(false);
        av_view.setOnAmountChangeListener(new ShopAmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(boolean isAdd, boolean isMaxOrMin, int amount) {
                Log.e("change",isAdd+"-------"+isMaxOrMin+"----------"+amount);
            }
        });

        av_view2.setAmountNum(1,10,1);
        av_view2.setEtClickable(false);
        av_view2.setOnAmountChangeListener(new ShopAmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(boolean isAdd, boolean isMaxOrMin, int amount) {
                Log.e("change",isAdd+"-------"+isMaxOrMin+"----------"+amount);
            }
        });

        av_view3.setAmountNum(10,10,1);
        av_view3.setEtClickable(false);
        av_view3.setOnAmountChangeListener(new ShopAmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(boolean isAdd, boolean isMaxOrMin, int amount) {
                Log.e("change",isAdd+"-------"+isMaxOrMin+"----------"+amount);
            }
        });
    }

    private void initBadgeView() {
        //创建对象
        BadgeView badgeView = new BadgeView(this);
        //设置依附的view
        badgeView.setTargetView(tv_1);
        //设置红点数量
        badgeView.setBadgeCount(10);
        //设置边距
        badgeView.setBadgeMargin(0,0,0,0);
        //设置位置
        badgeView.setRedHotViewGravity(Gravity.RIGHT);
    }


    private void initDialog() {
        final AlertNormalDialog alertCustomDialog = new AlertNormalDialog(this);
        alertCustomDialog.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCustomDialog.dialogClose();
                //UpdateUtils.setBackgroundAlpha(context,1.0f);
            }
        });
        alertCustomDialog.setTitle("这个是标题");
        alertCustomDialog.setContentText("这个是内容");
        alertCustomDialog.setLeftText("取消");
        alertCustomDialog.setRightText("确定");
        alertCustomDialog.setLoadFinishListener(new BaseDialog.onLoadFinishListener() {
            @Override
            public void listener(boolean isSuccess) {

            }
        });
        alertCustomDialog.show(getSupportFragmentManager());
    }


    private void initEditTextAndDel() {
        EditTextAndDel textAndDel = findViewById(R.id.et_number);
        textAndDel.setClickable(false);
        textAndDel.setFocusable(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_2:
                initDialog();
                break;
            case R.id.tv_3:
                ShareDialog dialog = new ShareDialog(this,null);
                dialog.show();
                break;
            case R.id.tv_4:
                loadDialog();
                break;
            case R.id.tv_5:
                skuDialog();
                break;
            case R.id.tv_6:
                showShopPop();
                break;
            case R.id.tv_8:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            default:
                break;
        }
    }

    private void loadDialog() {
        LoadDialog.show(this,"加载中");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadDialog.dismiss(this);
    }


    private void skuDialog() {
        SkuDialog skuDialog = new SkuDialog();
        skuDialog.show(getSupportFragmentManager());
    }


    private void showShopPop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_layout,null);
        //处理popWindow 显示内容,自定义布局
        handleLogic(contentView);
        //处理popWindow 显示内容,recycleView
        //handleListView(contentView);
        //创建并显示popWindow
        popWindow = new CustomPopupWindow.PopupWindowBuilder(this)
                //.setView(R.layout.pop_layout)
                .setView(contentView)
                .setFocusable(true)
                //弹出popWindow时，背景是否变暗
                .enableBackgroundDark(true)
                //控制亮度
                .setBgDarkAlpha(0.7f)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.popWindowStyle)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //对话框销毁时
                    }
                })
                .create();
        popWindow.showAsDropDown(tv_6,0,-(tv_6.getHeight() + popWindow.getHeight()),Gravity.TOP);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_balance:
                        Toast.makeText(MainActivity.this,"吐司",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                if(popWindow !=null){
                    popWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.go_balance).setOnClickListener(listener);
    }

}
