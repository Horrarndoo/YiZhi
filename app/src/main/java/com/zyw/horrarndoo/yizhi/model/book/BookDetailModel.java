package com.zyw.horrarndoo.yizhi.model.book;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.api.DoubanApi;
import com.zyw.horrarndoo.yizhi.contract.book.BookDeatilContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookDetailBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/23.
 * <p>
 */

public class BookDetailModel extends BaseModel implements BookDeatilContract.IBookDetailModel {

    @NonNull
    public static BookDetailModel newInstance() {
        return new BookDetailModel();
    }

    @Override
    public Observable<BookDetailBean> getBookDetail(String id) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getBookDetail(id)
                .compose(RxHelper.<BookDetailBean>rxSchedulerHelper());
    }
}
