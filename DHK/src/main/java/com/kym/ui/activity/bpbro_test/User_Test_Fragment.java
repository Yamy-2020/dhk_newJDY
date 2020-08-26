package com.kym.ui.activity.bpbro_test;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.BackDialog3;
import com.kym.ui.FeiLvActivity;
import com.kym.ui.LianxiActivity_new;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.MyCXKActivity;
import com.kym.ui.activity.SheZhiActivity;
import com.kym.ui.activity.bpbro_safe.UserAnQuanActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.news.NewsActivity;
import com.kym.ui.activity.shiming.NewShiMingActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.appconfig.log;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.UploadFile;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.model.Result;
import com.kym.ui.model.TiXianInFo;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.Connect.OnResponseListener;
import com.kym.ui.util.FileCache;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.PerMisson;
import com.kym.ui.util.UpLoadImage;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 单独的我的界面
 *
 * @author sun
 * @date 2019/12/3
 */

public class User_Test_Fragment extends Fragment implements OnClickListener {
    private static final int PHOTO_REQUEST_CAMERA = 11;
    private static final int PHOTO_REQUEST_GALLERY = 3;
    private View layout, view;
    public static int status;
    private SPConfig spConfig;
    private String new_path;
    private TextView textV_dj, textV_fl, tv_tjr, status_sm;
    private CircleImageView image_head;
    private Dialog selectDialog;
    private PopupWindow popWindow;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private BackDialog3 backDialog3;
    private Intent intent;
    private Callback callback = new Callback() {
        public boolean handleMessage(Message msg) {
            final File file = (File) msg.obj;
            UploadFile params = new UploadFile();
            NewUserResponse.DataBean userInfo = SPConfig.getInstance(getActivity().getApplicationContext()).getUserAllInfoNew();
            params.setType(userInfo.getUid());
            params.setFile(file);
            RequestParam param = new RequestParam(IService.UPLOAD_IMG, params, getActivity(), Constant.UPDLOAD);
            Connect.getInstance().httpUtil(param, new OnResponseListener() {
                @Override
                public void onSuccess(Object result) {
                    UpLoadImage resultUrl = (UpLoadImage) result;
                    if (resultUrl.getResult().getCode() == 10000) {
                        uploadUserHeadImg(resultUrl.getData().getUrl(), file);
                    } else if (resultUrl.getResult().getCode() == 101 || resultUrl.getResult().getCode() == 601) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", getContext(),
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getContext(), LoginActivity.class));

//                                restartApp(getContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getActivity(), resultUrl.getResult().getMsg());
                    }
                }

                @Override
                public void onFailure(String message) {
                    ToastUtil.showTextToas(getActivity(), message);
                }
            });
            return false;
        }
    };
    private Handler handler = new Handler(callback);
    private static final int PHOTO_REQUEST_CUT = 13;
    private NewUserResponse.DataBean userAllInfoNew;
    private ImageView im_lv;
    private BackDialog backDialog;
    private boolean isGetData = false;


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.user_test, null);
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        return layout;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter && !isGetData) {
            isGetData = true;
            userAllInfoNew = SPConfig.getInstance(getActivity()).getUserAllInfoNew();
            initView();
            HashMap<String, String> accountInfo = SPConfig.getInstance(getActivity()).getAccountInfo();
            LevelinFormation();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    private void uploadUserHeadImg(final String url, final File file) {
        HashMap<String, String> params = new HashMap<>();
        params.put("headimage", url);
        Connect.getInstance().post(getActivity(), IService.UPLOADAVATAR, params, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                Result response = (Result) JsonUtils.parse((String) result, Result.class);
                if (response.getResult().getCode() == 10000) {
                    Glide.with(getActivity()).load(url).error(R.drawable.icon)
                            .dontAnimate().into(image_head);
                } else if (response.getResult().getCode() == 101 || response.getResult().getCode() == 601) {
                    backDialog = new BackDialog("", "登录过期,请重新登录", "确定", getContext(),
                            R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getContext(), LoginActivity.class));

//                            restartApp(getContext());
                            backDialog.dismiss();
                        }
                    });
                    backDialog.setCancelable(false);
                    backDialog.show();
                } else {
                    ToastUtil.showTextToas(getActivity(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
    }

    private void uploadImage(final String path) {
        new Thread() {

            @Override
            public void run() {
                File file = FileCache.getInstance().saveBitmapToFile("/image" + 0 + ".jpg",
                        FileCache.getInstance().getImageFromLocal(path));
                Message msg = new Message();
                msg.obj = file;
                msg.what = 0;
                handler.sendMessage(msg);
            }

        }.start();

    }

    private void LevelinFormation() {
        Connect.getInstance().post(getActivity(), IService.ACCESSINGLEVELINFORMATION, null, new OnResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object result) {
                TiXianInFo response = (TiXianInFo) JsonUtils.parse((String) result, TiXianInFo.class);
                if (response.getResult().getCode() == 10000) {
                    TiXianInFo.DataBean data = response.getData();
                    String lfid = data.getLfid();
                    String name = data.getName();
                    textV_dj.setText(name);
                    switch (Integer.parseInt(lfid)) {
                        case 1:
                            im_lv.setImageResource(R.drawable.v1);
                            break;
                        case 2:
                            im_lv.setImageResource(R.drawable.v2);
                            break;
                        case 3:
                            im_lv.setImageResource(R.drawable.v3);
                            break;
                        case 4:
                            im_lv.setImageResource(R.drawable.v4);
                            break;
                        case 5:
                            im_lv.setImageResource(R.drawable.v5);
                            break;
                        case 6:
                            im_lv.setImageResource(R.drawable.v6);
                            break;
                        case 7:
                            im_lv.setImageResource(R.drawable.v7);
                            break;
                        case 8:
                            im_lv.setImageResource(R.drawable.v8);
                            break;
                        case 9:
                            im_lv.setImageResource(R.drawable.v9);
                            break;

                        default:
                            im_lv.setImageResource(R.drawable.v1);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getActivity(), message);
            }
        });
    }

    /**
     * 剪切图片
     */
    @SuppressLint("SdCardPath")
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        new_path = "/sdcard/ll1x/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
        File tempFile = new File(new_path);
        File temp = new File("/sdcard/ll1x/");
        if (!temp.exists()) {
            temp.mkdir();
        }
        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }
        }
        if (requestCode == PHOTO_REQUEST_CAMERA) {
            File tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
            crop(Uri.fromFile(tempFile));
        }
        if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                uploadImage(new_path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int CURRENT = 0;
        if (requestCode == CURRENT) {
            switch (requestCode) {
                case 0:
                    log.e(requestCode + ",requestCode");
                    String path = "";
                    uploadImage(path);
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        getImage();
        status_sm = (TextView) layout.findViewById(R.id.status);
        if (spConfig == null) {
            spConfig = SPConfig.getInstance(getActivity().getApplicationContext());
        }
        int status = spConfig.getUserInfoStatus();
        if (status == 1) {
            status_sm.setText("未实名");
        } else if (status == 2) {
            status_sm.setText("审核中");
        } else if (status == 3) {
            status_sm.setText("已实名");
        } else if (status == 4) {
            status_sm.setText("未通过");
        }
        im_lv = (ImageView) layout.findViewById(R.id.im_lv);
        tv_tjr = (TextView) layout.findViewById(R.id.tv_tjr);
        layout.findViewById(R.id.change_password).setOnClickListener(this);
        layout.findViewById(R.id.user_bankcard1).setOnClickListener(this);
        layout.findViewById(R.id.ziliao_set).setOnClickListener(this);
        layout.findViewById(R.id.exict_ll).setOnClickListener(this);
        textV_dj = (TextView) layout.findViewById(R.id.textv_set_dj);
        textV_fl = (TextView) layout.findViewById(R.id.textv_set_fl);
        image_head = (CircleImageView) layout.findViewById(R.id.imageV_dj_log);
        image_head.setOnClickListener(this);
        Glide.with(getActivity()).load(this.userAllInfoNew.getHeadimage()).error(R.drawable.icon)
                .dontAnimate().into(image_head);
    }

    private void getImage() {
        selectDialog = new Dialog(getActivity(), R.style.MyDialgoStyle);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.select_image, null);
        view.findViewById(R.id.registertakephoto).setOnClickListener(this);
        view.findViewById(R.id.registerfromphone).setOnClickListener(this);
        view.findViewById(R.id.cancle_tv).setOnClickListener(this);
        Window window = selectDialog.getWindow();
        assert window != null;
        window.setContentView(view);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @SuppressLint("InflateParams")
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_select_photo, null);
            popWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        }
        initPop(view);
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void initPop(View view) {
        TextView photograph = (TextView) view.findViewById(R.id.photograph);
        TextView albums = (TextView) view.findViewById(R.id.albums);
        LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        photograph.setOnClickListener(this);
        albums.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photograph:
                Intent intent_p = new Intent("android.media.action.IMAGE_CAPTURE");
                intent_p.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
                startActivityForResult(intent_p, PHOTO_REQUEST_CAMERA);
                popWindow.dismiss();
                break;
            case R.id.user_bankcard1:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), MyCXKActivity.class));
                }
                break;
            case R.id.albums:
                Intent intentxc = new Intent(Intent.ACTION_PICK);
                intentxc.setType("image/*");
                startActivityForResult(intentxc, PHOTO_REQUEST_GALLERY);
                popWindow.dismiss();
                break;
            case R.id.cancel:
                popWindow.dismiss();
                break;
            case R.id.imageV_dj_log:
                if (new PerMisson().cameraIsCanUse()) {
                    showPopupWindow(image_head);
                } else {
                    ToastUtil.showTextToas(getContext(), "请打开摄像头权限");
                }
                break;
            case R.id.zanyong:
                intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.jiesao_set:
                intent = new Intent(getActivity(), LianxiActivity_new.class);
                intent.putExtra("title", "1");
                startActivity(intent);
                break;
            case R.id.ziliao_set:
                if (spConfig == null) {
                    spConfig = SPConfig.getInstance(getActivity().getApplicationContext());
                }
                int status = spConfig.getUserInfoStatus();
                if (status == 1) {
                    startActivity(new Intent(getActivity(), NewShiMingActivity.class));
                }
                break;
            case R.id.change_password:
                startActivity(new Intent(getActivity(), UserAnQuanActivity.class));
                break;
            case R.id.exict_ll:
                startActivity(new Intent(getActivity(), SheZhiActivity.class));
                break;
            case R.id.registertakephoto:
                selectDialog.dismiss();
                break;
            case R.id.cancle_tv:
                selectDialog.dismiss();
                break;
            case R.id.user_feilv:
                if (canJump()) {
                    startActivity(new Intent(getActivity(), FeiLvActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private boolean canJump() {
        if (spConfig == null) {
            spConfig = SPConfig.getInstance(getActivity().getApplicationContext());
        }
        int status = spConfig.getUserInfoStatus();
        switch (status) {
            case 1:
                backDialog3 = new BackDialog3("确定", "取消", "提示", "请先完成实名认证", getContext(), R.style.Theme_Dialog_Scale, new BackDialog3.Dialog3ClickListener() {
                    @Override
                    public void onClick(View view) {
                        backDialog3.dismiss();
                        switch (view.getId()) {
                            case R.id.textView2:
                                break;
                            case R.id.textView1:
                                startActivity(new Intent(getActivity(), NewShiMingActivity.class));
                                break;
                        }
                    }
                });
                backDialog3.setCancelable(false);
                backDialog3.show();
                return false;
            case 2:
                ToastUtil.showTextToas(getContext(), "您的资料审核中,无法使用该功能");
                return false;
            case 3:
                return true;
            case 4:
                ToastUtil.showTextToas(getContext(), "您的资料审核未通过,无法使用该功能");
                return false;
            default:
                return false;
        }
    }
}
