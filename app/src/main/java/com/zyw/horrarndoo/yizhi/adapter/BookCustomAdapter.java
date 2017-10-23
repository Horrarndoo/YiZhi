package com.zyw.horrarndoo.yizhi.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookItemBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/23.
 * <p>
 */

public class BookCustomAdapter extends BaseCompatAdapter<BookItemBean, BaseViewHolder> {
    public BookCustomAdapter(@LayoutRes int layoutResId, @Nullable List<BookItemBean> data) {
        super(layoutResId, data);
    }

    public BookCustomAdapter(@Nullable List<BookItemBean> data) {
        super(data);
    }

    public BookCustomAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookItemBean item) {
        helper.setText(R.id.tv_item_title, "《" + item.getTitle() + "》");
        helper.setText(R.id.tv_item_author, "作者：" + item.getAuthorsString());
        helper.setText(R.id.tv_item_publisher, "出版社：" + item.getPublisher());
        helper.setText(R.id.tv_item_pubdate, "出版日期：" + item.getPubdate());
        helper.setText(R.id.tv_item_rate, "评分：" + item.getRating().getAverage());
        Glide.with(mContext).load(item.getImage()).crossFade(300).into((ImageView) helper.getView
                (R.id.iv_item_image));
    }
}
