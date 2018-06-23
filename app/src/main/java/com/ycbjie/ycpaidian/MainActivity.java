package com.ycbjie.ycpaidian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ycbjie.pdlib.amount.ShopAmountView;
import com.ycbjie.pdlib.badge.BadgeView;
import com.ycbjie.pdlib.dialog.AlertNormalDialog;
import com.ycbjie.pdlib.dialog.BaseDialog;
import com.ycbjie.pdlib.dialog.LoadDialog;
import com.ycbjie.pdlib.edittext.EditTextAndDel;
import com.ycbjie.pdlib.share.ShareDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShopAmountView av_view;
    private ShopAmountView av_view2;
    private ShopAmountView av_view3;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;

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
        initAmountView();
        initBadgeView();
        initEditTextAndDel();
    }

    private void initListener() {
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
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
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(tv_1);
        badgeView.setBadgeCount(10);
        badgeView.setBadgeMargin(0,0,0,0);
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
        alertCustomDialog.setLoadFinishListenter(new BaseDialog.onLoadFinishListenter() {
            @Override
            public void listenter(boolean isSuccess) {
                //弹窗load结束监听
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
}
