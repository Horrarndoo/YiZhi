package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.activity.BaseMVPCompatActivity;
import com.zyw.horrarndoo.sdk.utils.DisplayUtils;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.sdk.utils.StringUtils;
import com.zyw.horrarndoo.sdk.widgets.CompatNestedScrollView;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.book.BookDeatilContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookDetailBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookItemBean;
import com.zyw.horrarndoo.yizhi.presenter.book.BookDetailPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zyw.horrarndoo.sdk.utils.StatusBarUtils.getStatusBarHeight;
import static com.zyw.horrarndoo.yizhi.constant.InternKeyConstant.INTENT_KEY_BOOK_BOOK_ITEM_BEAN;

/**
 * Created by Horrarndoo on 2017/10/23.
 * <p>
 */

public class BookDetailActivity extends BaseMVPCompatActivity<BookDeatilContract
        .BookDetailPresenter, BookDeatilContract.IBookDetailModel> implements BookDeatilContract
        .IBookDetailView {

    @BindView(R.id.iv_header_bg)
    ImageView ivHeaderBg;
    @BindView(R.id.iv_book_photo)
    ImageView ivBookPhoto;
    @BindView(R.id.tv_book_rating_number)
    TextView tvBookRatingNumber;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;
    @BindView(R.id.tv_book_author)
    TextView tvBookAuthor;
    @BindView(R.id.tv_book_publisher)
    TextView tvBookPublisher;
    @BindView(R.id.tv_book_pupdate)
    TextView tvBookPupdate;
    @BindView(R.id.ll_book_header)
    LinearLayout llBookHeader;
    @BindView(R.id.ll_book_sub_title)
    LinearLayout llBookSubTitle;
    @BindView(R.id.tv_book_sub_title)
    TextView tvBookSubTitle;
    @BindView(R.id.tv_book_author_intro)
    TextView tvBookAuthorIntro;
    @BindView(R.id.tv_book_summary)
    TextView tvBookSummary;
    @BindView(R.id.tv_book_detail)
    TextView tvBookDetail;
    @BindView(R.id.nsv_scrollview)
    CompatNestedScrollView nsvScrollview;
    @BindView(R.id.iv_toolbar_bg)
    ImageView ivToolbarBg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_net_view)
    FrameLayout flNetView;
    @BindView(R.id.v_network_error)
    View vNetworkError;

    private BookItemBean mBookItemBean;

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            mBookItemBean = (BookItemBean) getIntent().getSerializableExtra
                    (INTENT_KEY_BOOK_BOOK_ITEM_BEAN);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, mBookItemBean.getTitle());
        initHeaderView(mBookItemBean);
        nsvScrollview.bindAlphaView(ivToolbarBg);
        mPresenter.loadBookDetail(mBookItemBean.getId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void showBookDetail(BookDetailBean bean) {
        //        Logger.e(bean.toString());
        if (bean != null) {
            if (StringUtils.isEmpty(bean.getSubtitle())) {
                llBookSubTitle.setVisibility(View.GONE);
            } else {
                tvBookSubTitle.setText(bean.getSubtitle());
            }
            tvBookAuthorIntro.setText(bean.getAuthor_intro());
            tvBookSummary.setText(bean.getSummary());
            tvBookDetail.setText(bean.getCatalog());
        }
    }

    @Override
    public void showNetworkError() {
        nsvScrollview.setVisibility(View.GONE);
        vNetworkError.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.fl_net_view, R.id.ll_book_header})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_net_view:
                nsvScrollview.setVisibility(View.VISIBLE);
                vNetworkError.setVisibility(View.GONE);
                mPresenter.loadBookDetail(mBookItemBean.getId());
                break;
            case R.id.ll_book_header:
                mPresenter.onHeaderClick(mBookItemBean);
                break;
        }
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return BookDetailPresenter.newInstance();
    }

    private void initHeaderView(BookItemBean bookItemBean) {
        tvBookRatingNumber.setText(String.valueOf(bookItemBean.getRating().getAverage()));
        tvCollectCount.setText(String.valueOf(bookItemBean.getRating().getNumRaters()));
        tvBookAuthor.setText(bookItemBean.getAuthorsString());
        tvBookPublisher.setText(bookItemBean.getPublisher());
        tvBookPupdate.setText(bookItemBean.getPubdate());
        if (StringUtils.isEmpty(bookItemBean.getSubtitle())) {
            llBookSubTitle.setVisibility(View.GONE);
        } else {
            tvBookSubTitle.setText(bookItemBean.getSubtitle());
        }
        tvBookAuthorIntro.setText(bookItemBean.getAuthor_intro());
        tvBookSummary.setText(bookItemBean.getSummary());
        Glide.with(this).load(bookItemBean.getImages().getLarge()).asBitmap().into(ivBookPhoto);
        DisplayUtils.displayBlurImg(this, bookItemBean.getImages().getLarge(), ivHeaderBg);
        DisplayUtils.displayBlurImg(this, bookItemBean.getImages().getLarge(), ivToolbarBg);

        int headerBgHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            headerBgHeight = toolbar.getLayoutParams().height + getStatusBarHeight(this);
        }else {
            headerBgHeight = toolbar.getLayoutParams().height;
        }
        // 使背景图向上移动到图片的最低端，保留（toolbar+状态栏）的高度
        // 实际上此时ivToolbarBg高度还是330dp，只是除了toolbar外，剩下部分是透明状态
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams)
                ivToolbarBg.getLayoutParams();
        int marginTop = ivToolbarBg.getLayoutParams().height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
    }

    /**
     * @param context      activity
     * @param bookItemBean bean
     * @param imageView    imageView
     */
    public static void start(Activity context, BookItemBean bookItemBean, ImageView imageView) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(INTENT_KEY_BOOK_BOOK_ITEM_BEAN, bookItemBean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, ResourcesUtils.getString(R.string.transition_book_img));
        //与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
