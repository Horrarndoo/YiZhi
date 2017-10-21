package com.zyw.horrarndoo.yizhi.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.SubjectsBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class HotMovieAdapter extends BaseQuickAdapter<SubjectsBean, BaseViewHolder> {
    public HotMovieAdapter(@LayoutRes int layoutResId, @Nullable List<SubjectsBean> data) {
        super(layoutResId, data);
        init();
    }

    public HotMovieAdapter(@Nullable List<SubjectsBean> data) {
        super(data);
        init();
    }

    public HotMovieAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
        init();
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectsBean item) {
        helper.setText(R.id.tv_movie_title, item.getTitle());
        helper.setText(R.id.tv_movie_directors, item.getDirectorsString());
        helper.setText(R.id.tv_movie_actors, item.getActorsString());
        helper.setText(R.id.tv_movie_genres, item.getGenresString());
        helper.setText(R.id.tv_movie_rating_rate, String.valueOf(item.getRating().getAverage()));
        Glide.with(mContext).load(item.getImages().getLarge()).crossFade(300).placeholder(R
                .mipmap.img_default_movie).into((ImageView) helper.getView(R.id.iv_moive_photo));
    }

    private void init() {
        openLoadAnimation(new ScaleInAnimation(0.8f));
        isFirstOnly(false);
    }


}
