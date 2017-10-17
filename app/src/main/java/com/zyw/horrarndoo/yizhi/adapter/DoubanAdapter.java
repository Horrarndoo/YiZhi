package com.zyw.horrarndoo.yizhi.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.PersonBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class DoubanAdapter extends BaseQuickAdapter<SubjectsBean, BaseViewHolder> {
    public DoubanAdapter(@LayoutRes int layoutResId, @Nullable List<SubjectsBean> data) {
        super(layoutResId, data);
        init();
    }

    public DoubanAdapter(@Nullable List<SubjectsBean> data) {
        super(data);
        init();
    }

    public DoubanAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
        init();
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectsBean item) {
        helper.setText(R.id.tv_movie_title, item.getTitle());
        helper.setText(R.id.tv_movie_directors, getDirectors(item.getDirectors()));
        helper.setText(R.id.tv_movie_actors, getActors(item.getCasts()));
        helper.setText(R.id.tv_movie_genres, getGenres(item.getGenres()));
        helper.setText(R.id.tv_movie_rating_rate, String.valueOf(item.getRating().getAverage()));
        Glide.with(mContext).load(item.getImages().getLarge()).crossFade(300).placeholder(R
                .mipmap.img_default_movie).into((ImageView) helper.getView(R.id.iv_moive_photo));
    }

    private void init() {
        openLoadAnimation(new ScaleInAnimation(0.8f));
        isFirstOnly(false);
    }

    /**
     * 获取导演字符串
     *
     * @param list 导演list
     * @return 导演字符串 A/B/C..
     */
    private String getDirectors(List<PersonBean> list) {
        String directors = "";
        if (list.size() == 0)
            return directors;
        for (int i = 0; i < list.size(); i++) {
            directors = directors + list.get(i).getName();
            if (i < list.size() - 1)
                directors += " / ";
        }
        return directors;
    }

    /**
     * 获取演员字符串
     *
     * @param list 演员list
     * @return 演员字符串 A/B/C..
     */
    private String getActors(List<PersonBean> list) {
        String actors = "";
        if (list.size() == 0)
            return actors;
        for (int i = 0; i < list.size(); i++) {
            actors = actors + list.get(i).getName();
            if (i < list.size() - 1)
                actors += " / ";
        }
        return actors;
    }

    /**
     * 获取类型字符串
     *
     * @param list 类型list
     * @return 类型字符串 A/B/C..
     */
    private String getGenres(List<String> list) {
        String genres = "";
        if (list.size() == 0)
            return genres;
        for (int i = 0; i < list.size(); i++) {
            genres = genres + list.get(i);
            if (i < list.size() - 1)
                genres += " / ";
        }
        return genres;
    }
}
