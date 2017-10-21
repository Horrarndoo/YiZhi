package com.zyw.horrarndoo.yizhi.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.SubjectsBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class TopMovieAdapter extends BaseCompatAdapter<SubjectsBean, BaseViewHolder> {

    public TopMovieAdapter(@LayoutRes int layoutResId, @Nullable List<SubjectsBean> data) {
        super(layoutResId, data);
    }

    public TopMovieAdapter(@Nullable List<SubjectsBean> data) {
        super(data);
    }

    public TopMovieAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectsBean item) {
        helper.setText(R.id.tv_top_moive_name, item.getTitle());
        helper.setText(R.id.tv_top_moive_rate, String.valueOf(item.getRating().getAverage()));
        Glide.with(mContext).load(item.getImages().getLarge()).crossFade(300).placeholder(R
                .mipmap.img_default_movie).into((ImageView) helper.getView(R.id
                .iv_top_moive_photo));
    }
}
