package com.kym.ui.activity.shiming;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kym.ui.BackDialog;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.daichang.DaiChangCityActivity;
import com.kym.ui.activity.daichang.DaiChangRongHuiWebActivity;
import com.kym.ui.activity.huankuan.NewAddCreditCardActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.appconfig.IService;
import com.kym.ui.appconfig.SPConfig;
import com.kym.ui.info.BankListResponse;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.UploadFile;
import com.kym.ui.model.NewUserResponse;
import com.kym.ui.model.Result;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.FileCache;
import com.kym.ui.util.JsonUtils;
import com.kym.ui.util.PerMisson;
import com.kym.ui.util.UpLoadImage;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.kym.ui.activity.bpbro_untils.bpbro_untils.restartApp;

/**
 * 融汇上传照片
 *
 * @author sun
 * @date 2019/12/26
 */

public class RhZhengJianActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img1, img2, img3, img4, img1_1, img1_2, img1_3, img1_4, big_img;
    private static final int PHOTO_REQUEST_CAMERA = 11;
    private String img_name;
    private LinearLayout img_box1, img_box2, img_box3, img_box4, btn_login, li1, close;
    private RelativeLayout li2;
    private String path, photoType;
    private String picPath, idCardPhoto, idCardBackPhoto, facePhoto, signPhoto;
    private Uri picUri;
    private TextView right_tv;
    private Intent intent;
    private BankListResponse.BankInfo bankInfo;
    private BackDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rh_zheng_jian);
        bankInfo = (BankListResponse.BankInfo) getIntent().getSerializableExtra("bankInfo");
        initHead();
        initView();
    }

    private void initHead() {
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        right_tv = (TextView) findViewById(R.id.right_tv);
        right_tv.setText("重新拍照");
        right_tv.setOnClickListener(this);
        tv.setText("证件上传");
    }

    private void initView() {
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        big_img = (ImageView) findViewById(R.id.big_img);
        img1_1 = (ImageView) findViewById(R.id.img1_1);
        img1_2 = (ImageView) findViewById(R.id.img1_2);
        img1_3 = (ImageView) findViewById(R.id.img1_3);
        img1_4 = (ImageView) findViewById(R.id.img1_4);
        img_box1 = (LinearLayout) findViewById(R.id.img_box1);
        img_box2 = (LinearLayout) findViewById(R.id.img_box2);
        img_box3 = (LinearLayout) findViewById(R.id.img_box3);
        img_box4 = (LinearLayout) findViewById(R.id.img_box4);
        btn_login = (LinearLayout) findViewById(R.id.btn_login);
        li1 = (LinearLayout) findViewById(R.id.li1);
        li2 = (RelativeLayout) findViewById(R.id.li2);
        close = (LinearLayout) findViewById(R.id.close);
        btn_login.setOnClickListener(this);
        img_box1.setOnClickListener(this);
        img_box2.setOnClickListener(this);
        img_box3.setOnClickListener(this);
        img_box4.setOnClickListener(this);
        close.setOnClickListener(this);
        li2.setVisibility(View.GONE);
        right_tv.setVisibility(View.GONE);
        if (getIntent().getStringExtra("code1").equals("1")) {
            showYiBaoImg();
        } else {
            img1_1.setImageResource(R.drawable.img1);
            img1_2.setImageResource(R.drawable.img2);
            img1_3.setImageResource(R.drawable.img5);
            img1_4.setImageResource(R.drawable.img6);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.right_tv:
                if (photoType.equals("idCardPhoto")) {
                    img_name = "idCardPhoto";
                } else if (photoType.equals("idCardBackPhoto")) {
                    img_name = "idCardBackPhoto";
                } else if (photoType.equals("facePhoto")) {
                    img_name = "facePhoto";
                } else if (photoType.equals("signPhoto")) {
                    img_name = "signPhoto";
                }
                li1.setVisibility(View.VISIBLE);
                li2.setVisibility(View.GONE);
                photo();
                break;
            case R.id.img_box1:
                if (img1.getVisibility() == View.GONE) {
                    right_tv.setVisibility(View.VISIBLE);
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(idCardPhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                            .into(big_img);
                    photoType = "idCardPhoto";
                } else {
                    li1.setVisibility(View.VISIBLE);
                    li2.setVisibility(View.GONE);
                    img_name = "idCardPhoto";
                    photo();
                }
                break;
            case R.id.img_box2:
                if (img2.getVisibility() == View.GONE) {
                    right_tv.setVisibility(View.VISIBLE);
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(idCardBackPhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                            .into(big_img);
                    photoType = "idCardBackPhoto";
                } else {
                    li1.setVisibility(View.VISIBLE);
                    li2.setVisibility(View.GONE);
                    img_name = "idCardBackPhoto";
                    photo();
                }

                break;
            case R.id.img_box3:
                if (img3.getVisibility() == View.GONE) {
                    right_tv.setVisibility(View.VISIBLE);
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(facePhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                            .into(big_img);
                    photoType = "facePhoto";
                } else {
                    li1.setVisibility(View.VISIBLE);
                    li2.setVisibility(View.GONE);
                    img_name = "facePhoto";
                    photo();
                }

                break;
            case R.id.img_box4:
                if (img4.getVisibility() == View.GONE) {
                    right_tv.setVisibility(View.VISIBLE);
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(signPhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                            .into(big_img);
                    photoType = "signPhoto";
                } else {
                    li1.setVisibility(View.VISIBLE);
                    li2.setVisibility(View.GONE);
                    img_name = "signPhoto";
                    photo();
                }
                break;
            case R.id.btn_login:
                if (img1.getVisibility() != View.GONE || img2.getVisibility() != View.GONE ||
                        img3.getVisibility() != View.GONE || img4.getVisibility() != View.GONE) {
                    ToastUtil.showTextToas(getApplicationContext(), "请先补充照片");
                } else {
//                    if (getIntent().getStringExtra("type").equals("ronghui")) {
//                        isShangchuanDiqu();
//                    } else {
                    finish();
//                    }
                }
                break;
            case R.id.close:
                li1.setVisibility(View.VISIBLE);
                li2.setVisibility(View.GONE);
                right_tv.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 判断是否上传地区
     */
    private void isShangchuanDiqu() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.IS_CHUANDIQU, null, new Connect.OnResponseListener() {
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
                        String code1 = obj2.get("code").toString();
                        if (code1.equals("1")) {
                            /**
                             * 已上传
                             */
                            getBangKa();
                        } else {
                            /**
                             * 未上传
                             */
                            intent = new Intent(getApplicationContext(), DaiChangCityActivity.class);
                            intent.putExtra("data", bankInfo);
                            intent.putExtra("id", getIntent().getStringExtra("id"));
                            startActivity(intent);
                        }
                    } else if (code.equals("101") || code.equals("601")) {
                        backDialog = new BackDialog("", "登录过期,请重新登录", "确定", RhZhengJianActivity.this,
                                R.style.Theme_Dialog_Scale, new BackDialog.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(RhZhengJianActivity.this, LoginActivity.class));

//                                restartApp(getApplicationContext());
                                backDialog.dismiss();
                            }
                        });
                        backDialog.setCancelable(false);
                        backDialog.show();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    /**
     * 融汇绑卡
     */
    private void getBangKa() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", getIntent().getStringExtra("id"));
        params.put("cardid", bankInfo.getCardid());
        Connect.getInstance().post(getApplicationContext(), IService.ALL_SIGN, params, new Connect.OnResponseListener() {
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
                        String data = obj.get("data").toString();
                        JSONObject obj2 = new JSONObject(data);
                        intent = new Intent(getApplicationContext(), DaiChangRongHuiWebActivity.class);
                        intent.putExtra("html", obj2.getString("html"));
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    //生成照片的名字
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + format.format(date);
    }

    private void photo() {
        if (new PerMisson().cameraIsCanUse()) {
            path = Environment.getExternalStorageDirectory().getPath();
            picPath = path + File.separator + getPhotoFileName() + ".jpg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Android7.0文件保存方式改变了
            if (Build.VERSION.SDK_INT < 24) {
                picUri = Uri.fromFile(new File(picPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);//将原图的uri传入
                startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, picPath);
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            }
        } else {
            ToastUtil.showTextToas(getApplicationContext(), "请打开摄像头权限");
        }
    }

    private void uploadUserHeadImg(final String url, final File file) {

        HashMap<String, String> params = new HashMap<>();
        params.put(img_name, url);
        Connect.getInstance().post(getApplicationContext(), IService.DC_UPIMG, params, new Connect.OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                Result response = (Result) JsonUtils.parse((String) result, Result.class);
                if (response.getResult().getCode() == 10000) {
                    showYiBaoImg1();
                    if (img_name.equals("idCardPhoto")) {
                        img1_1.setVisibility(View.VISIBLE);
                        img1.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_1);
                    } else if (img_name.equals("idCardBackPhoto")) {
                        img1_2.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_2);
                    } else if (img_name.equals("facePhoto")) {
                        img1_3.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_3);
                    } else if (img_name.equals("signPhoto")) {
                        img1_4.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_4);
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            final DialogUtil dialogUtil = new DialogUtil(RhZhengJianActivity.this);
            final File file = (File) msg.obj;
            UploadFile params = new UploadFile();
            NewUserResponse.DataBean userInfo = SPConfig.getInstance(getApplicationContext()).getUserAllInfoNew();
            params.setType(userInfo.getUid());
            params.setFile(file);
            RequestParam param = new RequestParam(IService.UPLOAD_IMG, params, getApplicationContext(), Constant.UPDLOAD);
            Connect.getInstance().httpUtil(param, new Connect.OnResponseListener() {
                @Override
                public void onSuccess(Object result) {

                    UpLoadImage resultUrl = (UpLoadImage) result;
                    if (resultUrl.getResult().getCode() == 10000) {
                        uploadUserHeadImg(resultUrl.getData().getUrl(), file);
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), resultUrl.getResult().getMsg());
                    }
                    dialogUtil.dismiss();
                }

                @Override
                public void onFailure(String message) {
                    ToastUtil.showTextToas(getApplicationContext(), message);
                    dialogUtil.dismiss();
                }
            });
            return false;
        }
    };
    private Handler handler = new Handler(callback);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_CAMERA) {
                try {
                    uploadImage(picPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 易宝
     */
    private void showYiBaoImg() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.RH_ZHENGJIAN_SHOW, null, new Connect.OnResponseListener() {
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
                        idCardPhoto = obj2.getString("idCardPhoto");
                        idCardBackPhoto = obj2.getString("idCardBackPhoto");
                        facePhoto = obj2.getString("facePhoto");
                        signPhoto = obj2.getString("signPhoto");
                        img1_1.setVisibility(View.VISIBLE);
                        img1.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(idCardPhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_1);

                        img1_2.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(idCardBackPhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_2);

                        img1_3.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(facePhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_3);

                        img1_4.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.GONE);
                        Glide.with(getApplicationContext()).load(signPhoto).placeholder(R.drawable.default_image).dontAnimate().error(R.drawable.default_image)
                                .into(img1_4);
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }

    private void showYiBaoImg1() {
        final DialogUtil dialogUtil = new DialogUtil(this);
        Connect.getInstance().post(getApplicationContext(), IService.RH_ZHENGJIAN_SHOW, null, new Connect.OnResponseListener() {
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
                        idCardPhoto = obj2.getString("idCardPhoto");
                        idCardBackPhoto = obj2.getString("idCardBackPhoto");
                        facePhoto = obj2.getString("facePhoto");
                        signPhoto = obj2.getString("signPhoto");
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                dialogUtil.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });
    }
}
