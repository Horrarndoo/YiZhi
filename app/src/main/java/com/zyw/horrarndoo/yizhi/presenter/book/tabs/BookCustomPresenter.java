package com.zyw.horrarndoo.yizhi.presenter.book.tabs;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.zyw.horrarndoo.yizhi.contract.book.tabs.BookCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookListBean;
import com.zyw.horrarndoo.yizhi.model.book.tabs.BookCustomModel;
import com.zyw.horrarndoo.yizhi.ui.activity.detail.BookDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookCustomPresenter extends BookCustomContract.BookCustomPresenter {
    private int mStart;
    private int mCount = 30;
    private boolean isLoading;

    @NonNull
    public static BookCustomPresenter newInstance() {
        return new BookCustomPresenter();
    }

    @Override
    public void loadLatestBookList() {
        if (mIModel == null || mIView == null)
            return;

        mStart = 0;
        //一次加载20条数据
        mRxManager.register(mIModel.getBookListWithTag(mIView.getBookTags(), mStart, mCount)
                .subscribe(new Consumer<BookListBean>() {
                    @Override
                    public void accept(BookListBean bookListBean) throws Exception {
                        if (mIView == null)
                            return;

                        mStart += mCount;
                        mIView.updateContentList(bookListBean.getBooks());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mIView == null)
                            return;

                        if (mIView.isVisiable())
                            mIView.showToast("Network error.");

                        mIView.showNetworkError();
                    }
                }));
    }

    @Override
    public void loadMoreBookList() {
        if (!isLoading) {
            isLoading = true;
            //一次加载20条数据
            mRxManager.register(mIModel.getBookListWithTag(mIView.getBookTags(), mStart, mCount)
                    .subscribe(new Consumer<BookListBean>() {
                        @Override
                        public void accept(BookListBean bookListBean) throws
                                Exception {
                            isLoading = false;
                            if (mIView == null)
                                return;

                            if (bookListBean != null && bookListBean.getBooks() != null &&
                                    bookListBean.getBooks().size() > 0) {
                                mStart += mCount;
                                mIView.updateContentList(bookListBean.getBooks());
                            } else {
                                mIView.showNoMoreData();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            isLoading = false;
                            if (mIView != null) {
                                mIView.showLoadMoreError();
                            }
                        }
                    }));
        }
    }

    @Override
    public void onItemClick(int position, BookItemBean item, ImageView imageView) {
//        Logger.e("position " + position + " is clicked.");
        BookDetailActivity.start(mIView.getBindActivity(), item, imageView);
    }

    @Override
    public BookCustomContract.IBookCustomModel getModel() {
        return BookCustomModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
