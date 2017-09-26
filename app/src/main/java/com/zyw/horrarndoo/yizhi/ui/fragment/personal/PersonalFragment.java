package com.zyw.horrarndoo.yizhi.ui.fragment.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.sdk.rxbus.Subscribe;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.FileUtils;
import com.zyw.horrarndoo.yizhi.BuildConfig;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.personal.PersonalContract;
import com.zyw.horrarndoo.yizhi.model.bean.rxbus.RxEventHeadBean;
import com.zyw.horrarndoo.yizhi.presenter.personal.PersonalPresenter;
import com.zyw.horrarndoo.yizhi.ui.activity.personal.HeadSettingActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI;

/**
 * Created by Horrarndoo on 2017/9/23.
 * <p>
 */

public class PersonalFragment extends BaseMVPCompatFragment<PersonalContract.PersonalPresenter,
        PersonalContract.IPersonalModel> implements PersonalContract.IPersonalView {

    @BindView(R.id.civ_head)
    CircleImageView civHead;

    PopupWindow popupWindow;

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initPopupView();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return PersonalPresenter.newInstance();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //Logger.e("onLazyInitView");
        //加载子fragment
    }

    @OnClick(R.id.civ_head)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_head:
                mPresenter.btnHeadClicked();
                break;
            default:
                break;
        }
    }

    @Override
    public void initPopupView() {
        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) rootView.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) rootView.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) rootView.findViewById(R.id.btn_cancel);

        popupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        //设置点击空白处消失
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.btnCameraClicked();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                mPresenter.btnPhotoClicked();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.btnCancelClicked();
            }
        });
    }

    @Override
    public void showHead(Bitmap bitmap) {
        //此处实际应用中替换成服务器拉取图片
        Uri headUri = Uri.fromFile(new File(mActivity.getCacheDir(), "yizhi_head_image" + ".jpg"));
        if(headUri != null){
            String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), headUri);
            Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
            civHead.setImageBitmap(bitMap);
        }
    }

    @Override
    public void showPopupView() {
        View parent = LayoutInflater.from(mActivity).inflate(R.layout.fragment_personal, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    @Override
    public void dismissPopupView() {
        popupWindow.dismiss();
    }

    @Override
    public boolean popupIsShowing() {
        return popupWindow.isShowing();
    }

    @Override
    public void gotoHeadSettingActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mActivity, HeadSettingActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void gotoSystemPhoto(int requestCode) {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), requestCode);
    }

    @Override
    public void gotoSystemCamera(File tempFile, int requestCode) {
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + "" +
                    ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mPresenter.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe(code = RX_BUS_CODE_HEAD_IMAGE_URI)
    public void rxBusEvent(RxEventHeadBean bean) {
        Uri uri = bean.getUri();
        if (uri == null) {
            return;
        }
        String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), uri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        civHead.setImageBitmap(bitMap);
    }
}
