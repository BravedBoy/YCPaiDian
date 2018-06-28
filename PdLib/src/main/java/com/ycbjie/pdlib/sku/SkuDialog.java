package com.ycbjie.pdlib.sku;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycbjie.pdlib.R;
import com.ycbjie.pdlib.sku.flowLayout.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211/YCUtils
 *     time  : 2018/6/5
 *     desc  : sku
 *     revise:
 * </pre>
 */
public class SkuDialog  extends BaseDialogFragment {

    private LinearLayout mSkuList;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_sku_view;
    }

    @Override
    public void bindView(View v) {
        initFindById(v);
        List<SkuBean> list = initData();
        initSkuView(list);
    }


    private void initFindById(View v) {
        mSkuList = v.findViewById(R.id.sku_list);
    }


    private void initSkuView(List<SkuBean> list) {
        if(list!=null && list.size()>0){
            int size = list.size();
            for(int a=0 ;a<size ; a++){
                View view = View.inflate(getContext(),R.layout.sku_item_view,null);
                TextView mTvSpecName =  view.findViewById(R.id.tv_spec_name);
                RecyclerView mRecyclerView =  view.findViewById(R.id.recyclerView);
                mTvSpecName.setTextColor(Color.BLACK);
                mTvSpecName.setText(list.get(a).getSpec_name());

                List<SkuBean.SpecValuesBean> spec_values = list.get(a).getSpec_values();
                mRecyclerView.setLayoutManager(new FlowLayoutManager());
                SkuAdapter skuAdapter = new SkuAdapter(spec_values,getContext());
                mRecyclerView.setAdapter(skuAdapter);



                mSkuList.addView(view);
            }
        }
    }


    private List<SkuBean> initData() {
        List<SkuBean> list = new ArrayList<>();
        SkuBean skuBean1 = new SkuBean();
        List<SkuBean.SpecValuesBean> list1 = new ArrayList<>();
        for(int a=0 ; a<8 ; a++){
            SkuBean.SpecValuesBean bean = new SkuBean.SpecValuesBean();
            switch (a){
                case 0:
                    bean.setName("红色");
                    break;
                case 1:
                    bean.setName("黄色");
                    break;
                case 2:
                    bean.setName("青色");
                    break;
                case 3:
                    bean.setName("橙黄色");
                    break;
                case 4:
                    bean.setName("绿色");
                    break;
                case 5:
                    bean.setName("白色");
                    break;
                case 6:
                    bean.setName("浅灰色");
                    break;
                case 7:
                    bean.setName("粉红色");
                    break;
            }
            list1.add(bean);
        }
        skuBean1.setSpec_values(list1);
        skuBean1.setSpec_name("颜色");
        list.add(skuBean1);

        SkuBean skuBean2 = new SkuBean();
        List<SkuBean.SpecValuesBean> list2 = new ArrayList<>();
        for(int b=0 ; b<5 ; b++){
            SkuBean.SpecValuesBean bean = new SkuBean.SpecValuesBean();
            switch (b){
                case 0:
                    bean.setName("100g");
                    break;
                case 1:
                    bean.setName("200g");
                    break;
                case 2:
                    bean.setName("300g");
                    break;
                case 3:
                    bean.setName("400g");
                    break;
                case 4:
                    bean.setName("500g");
                    break;
            }
            list2.add(bean);
        }
        skuBean2.setSpec_values(list2);
        skuBean2.setSpec_name("重量");
        list.add(skuBean2);

        SkuBean skuBean3 = new SkuBean();
        List<SkuBean.SpecValuesBean> list3 = new ArrayList<>();
        for(int c=0 ; c<7 ; c++){
            SkuBean.SpecValuesBean bean = new SkuBean.SpecValuesBean();
            switch (c){
                case 0:
                    bean.setName("大象");
                    break;
                case 1:
                    bean.setName("米老鼠");
                    break;
                case 2:
                    bean.setName("猪");
                    break;
                case 3:
                    bean.setName("狼狗");
                    break;
                case 4:
                    bean.setName("老虎");
                    break;
                case 5:
                    bean.setName("小鲤鱼");
                    break;
                case 6:
                    bean.setName("草鱼");
                    break;
            }
            list3.add(bean);
        }
        skuBean3.setSpec_values(list3);
        skuBean3.setSpec_name("动物");
        list.add(skuBean3);
        return list;
    }


}
