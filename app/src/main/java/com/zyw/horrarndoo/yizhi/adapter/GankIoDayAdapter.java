package com.zyw.horrarndoo.yizhi.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyw.horrarndoo.sdk.utils.SpUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayItemBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public class GankIoDayAdapter extends BaseCompatAdapter<GankIoDayItemBean, BaseViewHolder>{

    public GankIoDayAdapter(@LayoutRes int layoutResId, @Nullable List<GankIoDayItemBean> data) {
        super(layoutResId, data);
    }

    public GankIoDayAdapter(@Nullable List<GankIoDayItemBean> data) {
        super(data);
    }

    public GankIoDayAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIoDayItemBean item) {
        if (SpUtils.getNightModel(mContext)) {
            helper.setTextColor(R.id.tv_type_item_title, Color.WHITE);
            helper.setTextColor(R.id.tv_item_title, Color.WHITE);
        } else {
            helper.setTextColor(R.id.tv_type_item_title, Color.BLACK);
            helper.setTextColor(R.id.tv_item_title, Color.BLACK);
        }

        helper.setText(R.id.tv_type_item_title, item.getType());
        helper.setText(R.id.tv_item_title, item.getDesc());

        if(item.getType().equals("福利")){
            Glide.with(mContext).load(item.getUrl()).crossFade().into((ImageView) helper.getView(R
                    .id.iv_item_title));
        }else if(item.getType().equals("Android")){
            helper.setBackgroundRes(R.id.iv_item_title, R.mipmap.gank_io_day_item_android);
        }else if(item.getType().equals("iOS")){
            helper.setBackgroundRes(R.id.iv_item_title, R.mipmap.gank_io_day_item_ios);
        }else if(item.getType().equals("前端")){
            helper.setBackgroundRes(R.id.iv_item_title, R.mipmap.gank_io_day_item_web);
        }else if(item.getType().equals("休息视频")){
            helper.setBackgroundRes(R.id.iv_item_title, R.mipmap.gank_io_day_item_video);
        }

        helper.addOnClickListener(R.id.ll_more);

        //GankIo 返回数据中，很多没有images，这里不用GankIo的图，直接写死
//        if(item.getImages() == null)
//            return;
//
//        Glide.with(mContext).load(item.getImages().get(0)).crossFade().into((ImageView) helper.getView(R
//                .id.iv_item_title));
    }
}
