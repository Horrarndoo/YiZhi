package com.zyw.horrarndoo.yizhi.model.book.tabs;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.api.DoubanApi;
import com.zyw.horrarndoo.yizhi.contract.book.tabs.BookCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookListBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookCustomModel extends BaseModel implements BookCustomContract.IBookCustomModel {
    @NonNull
    public static BookCustomModel newInstance() {
        return new BookCustomModel();
    }

    @Override
    public Observable<BookListBean> getBookListWithTag(String tag, int start, int count) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getBookListWithTag
                (tag, start, count).compose(RxHelper.<BookListBean>rxSchedulerHelper());
    }
}
