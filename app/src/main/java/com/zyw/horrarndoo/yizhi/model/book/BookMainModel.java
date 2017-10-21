package com.zyw.horrarndoo.yizhi.model.book;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.book.BookMainContract;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookMainModel extends BaseModel implements BookMainContract.IBookMainModel {

    @NonNull
    public static BookMainModel newInstance() {
        return new BookMainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"文学", "文化", "生活"};
    }
}
