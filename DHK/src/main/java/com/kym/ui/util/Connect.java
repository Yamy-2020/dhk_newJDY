package com.kym.ui.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kym.idcard.GsonUtils;
import com.kym.ui.PatterLoginActivity;
import com.kym.ui.R;
import com.kym.ui.activity.LoginActivity;
import com.kym.ui.appconfig.Constant;
import com.kym.ui.info.LoginInfo;
import com.kym.ui.info.LoginInfo_dfb;
import com.kym.ui.info.LowUserInfo;
import com.kym.ui.info.RcodeInfo;
import com.kym.ui.info.RegisterInfo;
import com.kym.ui.info.RequestParam;
import com.kym.ui.info.ResultInfo;
import com.kym.ui.info.TokenDataInfo;
import com.kym.ui.info.TokenInfo;
import com.kym.ui.info.UploadFile;
import com.kym.ui.newutil.CodeUrl;
import com.kym.ui.newutil.HandleCard;
import com.kym.ui.newutil.Unicode;
import com.kym.ui.newutilutil.BonusOrder;
import com.leon.commons.util.NetWorkUtil;
import com.zzss.jindy.appconfig.Clone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kym.ui.activity.LoginActivity.VERSION;
import static com.kym.ui.util.JsonUtils.isJson;


public class Connect {
    private static String token;
    private static OkHttpClient instance;
    private static Connect connect;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static Connect getInstance() {
        if (instance == null) {
            synchronized (Connect.class) {
                if (instance == null) {
                    synchronized (Connect.class) {
                        connect = new Connect();
                        instance = new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .build();
                    }
                }
            }
        }
        return connect;
    }

    private Request postRequest(String url, HashMap<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder("参数 = ");
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!TextUtils.isEmpty(entry.getValue())) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    builder.add(key, value);
                    stringBuilder.append(key).append(":").append(value).append(",");
                }
            }
        }

        if (LoginActivity.accessToken_xin.equals("")) {
            token = PatterLoginActivity.accessToken_xin;
        } else if (PatterLoginActivity.accessToken_xin.equals("")) {
            token = LoginActivity.accessToken_xin;
        }
        builder.add("omid", Clone.OMID);
        stringBuilder.append("omid").append(":").append(Clone.OMID).append(",");
        builder.add("accessToken", token);
        stringBuilder.append("accessToken").append(":").append(token);


        builder.add("version", VERSION);
        stringBuilder.append("version").append(":").append(VERSION);
        builder.add("terminal", "android");
        stringBuilder.append("terminal").append(":").append("android");

        Log.e(Clone.APP_NAME, stringBuilder.toString());
        FormBody body = builder.build();
        return new Request.Builder()
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 5.0.2; vivo X5Pro V Build/LRX22G; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/45016 Mobile Safari/537.36 MMWEBID/8379 MicroMessenger/7.0.9.1560(0x27000934) Process/tools NetType/WIFI Language/zh_CN ABI/arm64/" + token)
                .url(url)
                .post(body)
                .build();
    }

    private Request postFileRequest(String url, HashMap<String, String> params, File file) {
        Log.e(Clone.APP_NAME, "返回参数 = " + url);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!TextUtils.isEmpty(entry.getValue())) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
        }

        if (LoginActivity.accessToken_xin.equals("")) {
            token = PatterLoginActivity.accessToken_xin;
        } else if (PatterLoginActivity.accessToken_xin.equals("")) {
            token = LoginActivity.accessToken_xin;
        }
        builder.addFormDataPart("omid", Clone.OMID);
        builder.addFormDataPart("accessToken", token);
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody body = builder.build();
        return new Request.Builder()
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 5.0.2; vivo X5Pro V Build/LRX22G; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/45016 Mobile Safari/537.36 MMWEBID/8379 MicroMessenger/7.0.9.1560(0x27000934) Process/tools NetType/WIFI Language/zh_CN ABI/arm64/" + token)
                .url(url)
                .post(body).build();
    }

    public Request postFilesRequest(String url, HashMap<String, String> params, HashMap<String, File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!TextUtils.isEmpty(entry.getValue())) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
        }
        if (LoginActivity.accessToken_xin.equals("")) {
            token = PatterLoginActivity.accessToken_xin;
        } else if (PatterLoginActivity.accessToken_xin.equals("")) {
            token = LoginActivity.accessToken_xin;
        }
        builder.addFormDataPart("omid", Clone.OMID);
        builder.addFormDataPart("accessToken", token);
        for (Map.Entry<String, File> entry : files.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue().getName(), RequestBody.create(MediaType.parse("image/*"), entry.getValue()));
        }
        MultipartBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }

    /**
     * okhttp-oldPost
     */
    public void httpUtil(final RequestParam param, final OnResponseListener listener) {
        final Context context = param.getContext();
        if (NetWorkUtil.isNetworkConnected(context)) {

            //构造请求体
            Request request;
            File file = getFile(param);
            if (null == file) {
                request = postRequest(param.getUrl(), getRequestParams(param));
            } else {
                request = postFileRequest(param.getUrl(), getRequestParams(param), file);
            }
            instance.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure("网络异常,请稍后重试");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String string = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            processResult(param.getFlag(), listener, string);
                        }
                    });
                }
            });
        } else {
            listener.onFailure(context.getResources().getString(R.string.connet_host_fail));
        }
    }

    public void post(final Context context, String url, HashMap<String, String> params, final OnResponseListener listener) {
        if (NetWorkUtil.isNetworkConnected(context)) {
            Log.e(Clone.APP_NAME, "返回参数 = " + url);
            Request request = postRequest(url, params);
            instance.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure("网络异常,请稍后重试");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String string = response.body().string();

                    Log.e(Clone.APP_NAME, "返回参数 = " + Unicode.convertUnicode(string));
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isJson(string)) {
                                listener.onSuccess(Unicode.convertUnicode(string));
                            } else {
                                listener.onFailure("网络不太稳定,请稍后重试");
                            }
                        }
                    });
                }
            });
        } else {
            listener.onFailure(context.getResources().getString(R.string.connet_host_fail));
        }
    }

    /**
     * 下载视频
     */
    public String getVideo(String videoUrl, String parentPath, String fileName) {
        String backString = null;
        OutputStream os = null;
        File fileroot = new File(parentPath);
        if (!fileroot.exists()) {
            fileroot.mkdirs();
        }
        File file = new File(fileroot, fileName);
        String filename = parentPath + fileName;
        try {
            os = new FileOutputStream(file);
            URL url = new URL(videoUrl);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            byte buffer[] = new byte[4 * 1024];
            int a;
            while (((a = inputStream.read(buffer))) != -1) {
                os.write(buffer, 0, a);
            }
            backString = filename;
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        return backString;
    }

    private File getFile(RequestParam param) {
        int flag = param.getFlag();
        //判断参数中是否包含文件
        if (flag == Constant.UPDLOAD) {
            UploadFile UPDLOADFile = (UploadFile) param.getParams();
            return UPDLOADFile.getFile();
        }
        return null;
    }

    /**
     * 获得请求参数
     */
    private HashMap<String, String> getRequestParams(RequestParam param) {
        HashMap<String, String> params = new HashMap();
        if (LoginActivity.accessToken_xin.equals("")) {
            token = PatterLoginActivity.accessToken_xin;
        } else if (PatterLoginActivity.accessToken_xin.equals("")) {
            token = LoginActivity.accessToken_xin;
        }
        switch (param.getFlag()) {


            // 注册
            case Constant.REGISTER:
                params.put("accessToken", token);
                RegisterInfo info_ri = (RegisterInfo) param.getParams();
                params.put("type", 1 + "");
                params.put("mobile", info_ri.getInvite_mobile());
                params.put("code", info_ri.getCode());
                params.put("password", info_ri.getPassword());
                params.put("invite_mobile", info_ri.getPtid());
                params.put("passwordConfirm", info_ri.getP());
                break;


            case Constant.UPDLOAD:
                params.put("accessToken", token);
                UploadFile UPDLOADFile = (UploadFile) param.getParams();
                params.put("usid", UPDLOADFile.getType());
                params.put("uid", UPDLOADFile.getType());
                params.put("type", "image");
                break;

            case Constant.card_Link:
                params.put("accessToken", token);
                params.put("usid", (String) param.getParams());
                break;
            case Constant.bonus_Orderlist:
                params.put("accessToken", token);
                params.put("usid", (String) param.getParams());
                break;
            case Constant.handle_Card:
                params.put("accessToken", token);
                params.put("usid", (String) param.getParams());
                break;

            // 费率查询 获取费率
            case Constant.GET_TOKEN:
                params.put("accessToken", token);
                TokenInfo info_token = (TokenInfo) param.getParams();
                params.put("account", info_token.getAccount());
                params.put("key", info_token.getKey());
                break;
            // 登录
            case Constant.LOGIN:
                RegisterInfo info_login = (RegisterInfo) param.getParams();
                params.put("mobile", info_login.getMobile());
                params.put("auth_code", info_login.getCode());
                params.put("password", info_login.getPassword());
                params.put("cid", info_login.getCid());
                params.put("accessToken", info_login.getAccessToken());
                params.put("is_openposition", info_login.getIs_openposition());
                params.put("latitude", info_login.getLatitude());
                params.put("longitude", info_login.getLongitude());
                params.put("currentCity", info_login.getCurrentCity());
                params.put("currentProvinces", info_login.getCurrentProvinces());
                params.put("os_type", "android");
                params.put("device", info_login.getDevice());
                break;
            case Constant.UPLOAD_CLICK:
                HashMap<String, String> param1 = (HashMap) param.getParams();
                params.put("accessToken", param1.get("accessToken"));
                params.put("adlist_id", param1.get("id"));
                break;
            case Constant.AUTH:
                params.put("accessToken", token);
                params.put("code", (String) param.getParams());
                break;
            default:
                break;
        }
        return params;
    }

    /**
     * 处理服务器响应结果
     */
    private void processResult(int flag, OnResponseListener listener, String result) {
        result = Unicode.convertUnicode(result);
        Gson gson = new Gson();
        Type type;
        ResultInfo resultInfo;
        switch (flag) {
            // 登录
            case Constant.LOGIN:
                log.e("登录：" + result);
                type = new TypeToken<LoginInfo>() {
                }.getType();
                // //利用gson 将json转化为对象
                LoginInfo infoLogin = gson.fromJson(result, type);
                ResultInfo result_login = infoLogin.getResult();

                if (result_login.getCode() == 10000 || result_login.getCode() == 103 || result_login.getCode() == 104) {

                    listener.onSuccess(infoLogin);
                } else {

                    listener.onFailure(infoLogin.getResult().getMsg());
                }
                break;

            // 注册
            case Constant.REGISTER:

                type = new TypeToken<LoginInfo_dfb>() {
                }.getType();
                LoginInfo_dfb info_register_dfb = gson.fromJson(result, type);
                ResultInfo result_info = info_register_dfb.getResult();
                if (result_info.getCode() == 10000) {
                    listener.onSuccess(info_register_dfb);
                } else {

                    listener.onFailure(result_info.getMsg());
                }
                break;


            case Constant.UPDLOAD:
                log.e("上传头像" + result);
                UpLoadImage mUPDLOAD = GsonUtils.parseJSON(result, UpLoadImage.class);
                if (mUPDLOAD.getResult().getCode() == 10000) {

                    listener.onSuccess(mUPDLOAD);
                } else {

                    listener.onFailure(mUPDLOAD.getResult().getMsg());
                }
                break;

            case Constant.card_Link:
                log.e("办理信用卡推广链接：" + result);
                CodeUrl codeUrl = GsonUtils.parseJSON(result, CodeUrl.class);
                if (codeUrl.getResult().getCode() == 10000) {
                    listener.onSuccess(codeUrl);
                } else {
                    listener.onFailure(codeUrl.getResult().getMsg());
                }
                break;
            case Constant.bonus_Orderlist:
                log.e("推荐办理卡订单接口：" + result);
                BonusOrder bonusOrder = GsonUtils.parseJSON(result, BonusOrder.class);
                if (bonusOrder.getResult().getCode() == 10000) {
                    listener.onSuccess(bonusOrder);
                } else {
                    listener.onFailure(bonusOrder.getResult().getMsg());
                }
                break;
            case Constant.handle_Card:
                log.e("我要办理信用卡接口：" + result);
                HandleCard handleCard = GsonUtils.parseJSON(result, HandleCard.class);
                if (handleCard.getResult().getCode() == 10000) {
                    listener.onSuccess(handleCard);
                } else {
                    listener.onFailure(handleCard.getResult().getMsg());
                }
                break;


            // 费率查询
            case Constant.GET_TOKEN:
                log.e("----@@@@@@@@@@@@@" + result);
                type = new TypeToken<TokenDataInfo>() {
                }.getType();
                TokenDataInfo token_Info = gson.fromJson(result, type);
                resultInfo = token_Info.getResult();
                if (resultInfo.getCode() == 10000) {

                    listener.onSuccess(token_Info.getData());
                } else {

                    listener.onFailure(resultInfo.getMsg());
                }
                break;


            // 上传用户点击事件
            case Constant.UPLOAD_CLICK:
                type = new TypeToken<LowUserInfo>() {
                }.getType();
                LowUserInfo base = gson.fromJson(result, type);
                if (base.getResult().getCode() == 10000) {
                    listener.onSuccess(base.getData());
                } else {
                    listener.onFailure(base.getResult().getMsg());
                }
                break;


            case Constant.AUTH:
                type = new TypeToken<RcodeInfo>() {
                }.getType();
                RcodeInfo json = gson.fromJson(result, type);
                if (json.getResult().getCode() == 10000) {
                    listener.onSuccess(json.getResult().getMsg());
                } else {
                    listener.onFailure(json.getResult().getMsg());
                }
                break;

            default:
                break;
        }
    }

    /**
     * 返回结果接口
     *
     * @author Administrator
     */
    public interface OnResponseListener {
        void onSuccess(Object result);

        void onFailure(String message);
    }

}
