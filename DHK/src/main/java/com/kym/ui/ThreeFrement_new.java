package com.kym.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.activity.AllQianYueListActivity;
import com.kym.ui.activity.CommonTestActivity;
import com.kym.ui.activity.MyCXKActivity;
import com.kym.ui.activity.SheZhiActivity;
import com.kym.ui.activity.bpbro_safe.UserAnQuanActivity;
import com.kym.ui.activity.bpbro_real_name.Bpbro_Idcardid_Activity;
import com.kym.ui.activity.shiming.ZhengJianActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.log;
import com.kym.ui.activity.news.NewsActivity;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.UploadFile;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.model.Result;
import com.kym.ui.model.TiXianInFo;
import com.kym.ui.newutilutil.CircleImageView;
import com.kym.ui.util.Connect;
import com.kym.ui.util.Connect.OnResponseListener;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.FileCache;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.PerMisson;
import com.kym.ui.util.UpLoadImage;
import com.kym.ui.wxapi.WXEntryActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import widget.CustomPopWindow;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;
import static com.kym.ui.util.ShareUtil.bmpToByteArray;
import static com.zzss.jindy.appconfig.Clone.OMID;

/**
 * 我的
 *
 * @author sun
 * @date 2019/12/3
 */

public class ThreeFrement_new extends Fragment implements OnClickListener {
    private static final int PHOTO_REQUEST_CAMERA = 11;
    private static final int PHOTO_REQUEST_GALLERY = 3;
    private View layout, view;
    public static int status;
    private SPConfig spConfig;
    private String new_path, code1;
    private TextView textV_dj, textV_fl, tv_tjr, status_sm, wdkf, yaoqing, lingqian, zhengjian_status;
    private LinearLayout user_lingqian;
    private CircleImageView image_head;
    private Dialog selectDialog;
    private PopupWindow popWindow;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private FrameLayout ceshi;
    private BackDialog3 backDialog3;
    private Intent intent;
    private IWXAPI api;
    private final int TYPE_WX_FRIEND = 0;
    private final int TYPE_WX_FRIENDS = 1;
    private CustomPopWindow popupWindow;
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
                                restartApp(getContext());
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
    private ImageView id;


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.activity_three_frement_new, null);
        }
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeView(layout);
        }
        getYiBaoImg();
        return layout;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter && !isGetData) {
            isGetData = true;
            userAllInfoNew = SPConfig.getInstance(getActivity()).getUserAllInfoNew();
            initView();
            HashMap<String, String> accountInfo = SPConfig.getInstance(getActivity()).getAccountInfo();
            if (accountInfo.get(Constant.USERNAME).length() != 0) {
                textV_fl.setText(accountInfo.get(Constant.USERNAME).substring(0, 3) +
                        "****" + accountInfo.get(Constant.USERNAME).substring(7, 11));
            }
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
                            restartApp(getContext());
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
        Connect.getInstance().post(getActivity(), IService.ACCESSINGLEVELINFORMATION, null, new Connect.OnResponseListener() {
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
        id = layout.findViewById(R.id.shiming_sanjiao);
        wdkf = layout.findViewById(R.id.wdkf);
        lingqian = layout.findViewById(R.id.lingqian);
        getImage();
        status_sm = layout.findViewById(R.id.status);
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
//            status_sm  实名的textview
            //id         小三角形的imageview
            if (status_sm.getText().equals("已实名")){
                id.setVisibility(View.INVISIBLE);
            }

        } else if (status == 4) {
            status_sm.setText("未通过");
        }
        im_lv = layout.findViewById(R.id.im_lv);
        tv_tjr = layout.findViewById(R.id.tv_tjr);
        if (userAllInfoNew.getReferrername().length() != 0) {
            tv_tjr.setText(userAllInfoNew.getReferrername().substring(0, 1) + "** " +
                    userAllInfoNew.getReferrermobile().substring(0, 3) + "****" +
                    userAllInfoNew.getReferrermobile().substring(7, 11));
        }
//        zhengjian_status = layout.findViewById(R.id.zhengjian_status);
        layout.findViewById(R.id.user_feilv).setOnClickListener(this);
        layout.findViewById(R.id.change_password).setOnClickListener(this);
        layout.findViewById(R.id.user_bankcard1).setOnClickListener(this);
        layout.findViewById(R.id.zanyong).setOnClickListener(this);
        layout.findViewById(R.id.tuijianren).setOnClickListener(this);
        layout.findViewById(R.id.ziliao_set).setOnClickListener(this);
//        layout.findViewById(R.id.jiesao_set).setOnClickListener(this);
        layout.findViewById(R.id.exict_ll).setOnClickListener(this);
        layout.findViewById(R.id.user_zhangdan).setOnClickListener(this);
//        layout.findViewById(R.id.zhengjian).setOnClickListener(this);
        yaoqing = layout.findViewById(R.id.yaoqing);
        yaoqing.setOnClickListener(this);
        user_lingqian = layout.findViewById(R.id.user_lingqian);
        user_lingqian.setOnClickListener(this);
     /*   if (OMID.equals("1H1AJD6SLKVADDM6")) {
            wdkf.setText("卡医生");
            lingqian.setText("资金保障险");
            user_lingqian.setVisibility(View.VISIBLE);
            if (SPConfig.getInstance(getContext()).getUserAllInfoNew().getIs_yuyue().equals("1")) {
                layout.findViewById(R.id.user_zhangdan).setVisibility(View.VISIBLE);
            } else {
                layout.findViewById(R.id.user_zhangdan).setVisibility(View.GONE);
            }
        } else if (OMID.equals("rd500ZbaNVcKVr8g")) {
            wdkf.setText("卡医生");
        } else {
            wdkf.setText("我的客服");
            layout.findViewById(R.id.user_zhangdan).setVisibility(View.GONE);
        }*/
        ceshi = layout.findViewById(R.id.ceshi);
        ceshi.setOnClickListener(this);
        if (OMID.equals("R4PGM59YOCLZLOG6")) {
            ceshi.setVisibility(View.VISIBLE);
        } else {
            ceshi.setVisibility(View.GONE);
        }
        textV_dj = layout.findViewById(R.id.textv_set_dj);
        textV_fl = layout.findViewById(R.id.textv_set_fl);
        image_head = layout.findViewById(R.id.imageV_dj_log);
        image_head.setOnClickListener(this);
     /*   Glide.with(getActivity()).load(this.userAllInfoNew.getHeadimage()).error(R.drawable.icon)
                .dontAnimate().into(image_head);*/
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
        window.setLayout(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
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
        TextView photograph = view.findViewById(R.id.photograph);
        TextView albums = view.findViewById(R.id.albums);
        LinearLayout cancel = view.findViewById(R.id.cancel);
        photograph.setOnClickListener(this);
        albums.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tuijianren:
//                if (!(tv_tjr.getText().equals(" "))) {
//                    Intent intent_tuijianren = new Intent(Intent.ACTION_DIAL);
//                    Uri uris = Uri.parse("tel:" + userAllInfoNew.getReferrermobile());
//                    intent_tuijianren.setData(uris);
//                    startActivity(intent_tuijianren);
//                }
//                break;
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
                startActivity(new Intent(getActivity(), NewsActivity.class));
                break;
            case R.id.yaoqing:
                if (canJump()) {
                    showPop();
                }
                break;
            case R.id.user_lingqian:
                if (canJump()) {

                    startActivity(new Intent(getActivity(), AllQianYueListActivity.class));
//                    if (OMID.equals("1H1AJD6SLKVADDM6")) {
//                        intent = new Intent(getActivity(), HL_BK_WebActivity.class);
//                        intent.putExtra("WEB_TITLE", "资金保障险");
//                        intent.putExtra("WEB_URL", SPConfig.getInstance(getContext()).getUserAllInfoNew().getHandlecard_url());
//                        startActivity(intent);
//                    } else {
//                        startActivity(new Intent(getActivity(), LingQianActivity.class));
//                    }
                }
                break;
            /*case R.id.jiesao_set:
                if (OMID.equals("1H1AJD6SLKVADDM6") || OMID.equals("rd500ZbaNVcKVr8g")) {
                    intent = new Intent(getActivity(), CardTestActivity.class);
                    intent.putExtra("title", "卡医生");
                    startActivity(intent);
                } else {
                    Intent tolianxiActivity = new Intent(getActivity(), LianxiActivity_new.class);
                    tolianxiActivity.putExtra("title", "1");
                    startActivity(tolianxiActivity);
                }
                break;*/
            case R.id.ceshi:
                Intent CommonTest = new Intent(getActivity(), CommonTestActivity.class);
                startActivity(CommonTest);
                break;
            case R.id.ziliao_set:
                if (spConfig == null) {
                    spConfig = SPConfig.getInstance(getActivity().getApplicationContext());
                }
                int status = spConfig.getUserInfoStatus();
                if (status == 1) {
                    startActivity(new Intent(getActivity(), Bpbro_Idcardid_Activity.class));
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
      /*      case R.id.zhengjian:
            Intent intent = new Intent(getContext(), ZhengJianActivity.class);
            intent.putExtra("code1", code1);
            intent.putExtra("type", "yibao");
            startActivity(intent);
            break;
            default:
                break;*/
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
                                startActivity(new Intent(getActivity(), Bpbro_Idcardid_Activity.class));
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

    private void showPop() {
        popupWindow = new CustomPopWindow.Builder(getActivity()).setContentView(initPopView()).setAnimationStyle(R.style.anim_popup_rise_down)
                .setOutsideFocusable(true).setFocusable(true).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .build();
        popupWindow.showAtLocation(yaoqing, Gravity.BOTTOM);
    }

    private View initPopView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_share_qr, null);
        view.findViewById(R.id.iv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.tv_share_qr_save).setVisibility(View.GONE);
        view.findViewById(R.id.iv_share_qr_wx_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(TYPE_WX_FRIEND);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_wx_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(TYPE_WX_FRIENDS);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.iv_share_qr_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.tv_share_qr_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return view;
    }

    private void share(int type) {
        api = WXAPIFactory.createWXAPI(getContext(), WXEntryActivity.APP_ID);
        api.registerApp(WXEntryActivity.APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!api.isWXAppInstalled()) {
            ToastUtil.showTextToas(getContext(), "您还没有安装微信");
            return;
        }
        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = SPConfig.getInstance(getContext()).getUserAllInfoNew().getAd_to_url() + "?reurl=" + SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_url();
        WXMediaMessage msg = new WXMediaMessage(object);
        msg.title = SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_title();
        msg.description = SPConfig.getInstance(getContext()).getUserAllInfoNew().getYuyue_introduce();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_home);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        if (type == TYPE_WX_FRIEND) {
            //设置发送给朋友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == TYPE_WX_FRIENDS) {
            //设置发送到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        req.transaction = String.valueOf(type);
        api.sendReq(req);
    }

    /**
     * 易宝
     */
    private void getYiBaoImg() {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "5");
        Connect.getInstance().post(getContext(), IService.YIBAO_IMG, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                dialogUtil.dismiss();
                try {
                    JSONObject obj = new JSONObject(result.toString());
                    String result1 = obj.get("result").toString();
                    JSONObject obj1 = new JSONObject(result1);
                    String code = obj1.get("code").toString();
                    String msg = obj1.get("msg").toString();
                    if (code.equals("10000")) {
                        String data1 = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data1);
                        String msg1 = obj2.get("msg").toString();
                        code1 = obj2.get("code").toString();
                        zhengjian_status.setText(msg1);
                        if (zhengjian_status.getText().toString().equals("未上传")) {
                            zhengjian_status.setTextColor(getResources().getColor(R.color.blue_2e));
                        } else {
                            zhengjian_status.setTextColor(getResources().getColor(R.color.blue_2e));
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", getContext(),
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                restartApp(getContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getContext(), message);
            }
        });
    }
}
