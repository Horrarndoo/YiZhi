package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.activity.BaseMVPCompatActivity;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMovieDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.MovieDetailBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.presenter.detail.DoubanMovieDetailPresenter;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.InternKeyConstant.INTENT_KEY_DOUBAN_MOVIE_SUBJECTBEAN;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class DoubanMovieDetailActivity extends BaseMVPCompatActivity<DoubanMovieDetailContract
        .DoubanMovieDetailPresenter, DoubanMovieDetailContract.IDoubanMovieDetailModel>
        implements DoubanMovieDetailContract.IDoubanMovieDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.iv_header_bg)
    ImageView ivHeaderBg;
    @BindView(R.id.iv_movie_photo)
    ImageView ivMoviePhoto;
    @BindView(R.id.tv_movie_rating_rate)
    TextView tvMovieRatingRate;
    @BindView(R.id.tv_movie_rating_number)
    TextView tvMovieRatingNumber;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;
    @BindView(R.id.tv_movie_directors)
    TextView tvMovieDirectors;
    @BindView(R.id.tv_movie_casts)
    TextView tvMovieCasts;
    @BindView(R.id.tv_movie_genres)
    TextView tvMovieGenres;
    @BindView(R.id.tv_movie_date)
    TextView tvMovieDate;
    @BindView(R.id.tv_movie_city)
    TextView tvMovieCity;
    @BindView(R.id.ll_movie_header)
    LinearLayout llMovieHeader;

    private SubjectsBean mSubjectBean;

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return DoubanMovieDetailPresenter.newInstance();
    }

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            mSubjectBean = (SubjectsBean) getIntent().getSerializableExtra
                    (INTENT_KEY_DOUBAN_MOVIE_SUBJECTBEAN);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, "电影详情");
        tvMovieRatingNumber.setText(String.valueOf(mSubjectBean.getRating().getAverage()));
        tvCollectCount.setText(String.valueOf(mSubjectBean.getCollect_count()));
        tvMovieDirectors.setText(mSubjectBean.getDirectorsString());
        tvMovieCasts.setText(mSubjectBean.getActorsString());
        tvMovieGenres.setText(mSubjectBean.getGenresString());
        tvMovieDate.setText(mSubjectBean.getYear());
        tvMovieCity.setText("city");
        Glide.with(this).load(mSubjectBean.getImages().getLarge()).asBitmap().into(ivMoviePhoto);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban_detail;
    }

    @Override
    public void showMovieDetail(MovieDetailBean bean) {
        Logger.e(bean.toString());
    }

    @Override
    public void showNetworkError() {
    }

    @Override
    public void showLoading() {
    }

    /**
     * @param context      activity
     * @param subjectsBean bean
     * @param imageView    imageView
     */
    public static void start(Activity context, SubjectsBean subjectsBean, ImageView imageView) {
        Intent intent = new Intent(context, DoubanMovieDetailActivity.class);
        intent.putExtra(INTENT_KEY_DOUBAN_MOVIE_SUBJECTBEAN, subjectsBean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, ResourcesUtils.getString(R.string.transition_movie_img));
        //与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
