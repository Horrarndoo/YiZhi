package com.zyw.horrarndoo.yizhi.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.PersonBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class DoubanMovieDetailAdapter extends BaseCompatAdapter<PersonBean, BaseViewHolder> {
    public DoubanMovieDetailAdapter(@LayoutRes int layoutResId, @Nullable List<PersonBean> data) {
        super(layoutResId, data);
    }

    public DoubanMovieDetailAdapter(@Nullable List<PersonBean> data) {
        super(data);
    }

    public DoubanMovieDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setText(R.id.tv_person_name, item.getName());
        helper.setText(R.id.tv_person_type, item.getType());
        Glide.with(mContext).load(item.getAvatars().getLarge()).crossFade().into((ImageView)
                helper.getView(R.id.iv_avatar_photo));
    }
}
