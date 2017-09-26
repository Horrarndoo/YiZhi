package com.zyw.horrarndoo.yizhi.ui.fragment.personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.zyw.horrarndoo.sdk.utils.LogUtils;
import com.zyw.horrarndoo.sdk.utils.MD5Utils;
import com.zyw.horrarndoo.yizhi.BuildConfig;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.rxbus.RxEventHeadBean;
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

public class PersonalFragment extends BaseMVPCompatFragment {

    @BindView(R.id.civ_head)
    CircleImageView civHead;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    //调用照相机返回图片文件
    private File tempFile;

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
        //此处实际应用中替换成服务器拉取图片
        Uri headUri = Uri.fromFile(new File(mActivity.getCacheDir(), "yizhi_head_image" + ".jpg"));
        if(headUri != null){
            String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), headUri);
            Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
            civHead.setImageBitmap(bitMap);
        }
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
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
                uploadHeadImage();
                break;
            default:
                break;
        }
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

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        LogUtils.d("*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        LogUtils.d("*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtils.checkDirPath(Environment.getExternalStorageDirectory()
                .getPath() + "/image/"), MD5Utils.getMD5("yizhi_head_image") + System
                .currentTimeMillis() + ".jpg");

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
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) rootView.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) rootView.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) rootView.findViewById(R.id.btn_cancel);

        View parent = LayoutInflater.from(mActivity).inflate(R.layout.fragment_personal, null);
        final PopupWindow popupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams
                .MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        //设置点击空白处消失
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);

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
                //权限判断
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest
                                    .permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission
                        .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest
                                    .permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoHeadSettingActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoHeadSettingActivity(uri);
                }
                break;
        }
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    /**
     * 打开头像设置界面
     */
    public void gotoHeadSettingActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mActivity, HeadSettingActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }
}
