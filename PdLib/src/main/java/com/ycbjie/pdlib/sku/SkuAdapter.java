package com.ycbjie.pdlib.sku;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ycbjie.pdlib.R;

import java.util.List;


public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.MyViewHolder> {

    private Context context;
    private List<SkuBean.SpecValuesBean> list;

    SkuAdapter(List<SkuBean.SpecValuesBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sku_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if(list!=null && list.size()>0){
            SkuBean.SpecValuesBean specValuesBean = list.get(position);
            switch (specValuesBean.getStatus()){
                case NONE:
                    holder.tv_title.setBackgroundResource(R.drawable.shape_sku_unselect_bg);
                    break;
                case SELECTED:
                    holder.tv_title.setBackgroundResource(R.drawable.shape_sku_select_bg);
                    break;
                case NO_SELECTED:
                    holder.tv_title.setBackgroundResource(R.drawable.shape_sku_unselect_bg);
                    break;
                default:
                    break;
            }
            holder.tv_title.setText(specValuesBean.getName());
        }
    }


    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;

        MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
        }
    }

}
