package com.kym.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.appconfig.IService;
import com.kym.ui.bean.Constants;
import com.kym.ui.bean.OkHttpUtils;
import com.kym.ui.bean.OrederSendInfo;
import com.kym.ui.bean.PrepayIdInfo;
import com.kym.ui.util.Connect;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.MD5;
import com.kym.ui.util.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PayActivity extends BaseActivity implements View.OnClickListener {

    private IWXAPI api;
    private StringBuilder response;
    private final int what = 1;
    private Button appay_btn, check_pay_btn;
    PrepayIdInfo bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

    }

    public void initView() {
        appay_btn = (Button) findViewById(R.id.appay_btn);
        appay_btn.setOnClickListener(this);
        check_pay_btn = (Button) findViewById(R.id.check_pay_btn);
        check_pay_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appay_btn:
//                Date d = news_img Date();
//                System.out.println(d);
//                SimpleDateFormat sdf = news_img SimpleDateFormat("yyyyMMddHHmmss");
//                String dateNowStr = sdf.format(d);
//                System.out.println("格式化后的日期：" + dateNowStr);
//                OrederSendInfo sendInfo = news_img OrederSendInfo(Constants.APP_ID, Constants.MCH_ID, genNonceStr(), "鹅豆-旅游2", dateNowStr, "1", "127.0.0.1", "www.baidu.com", "APP");
//                NetWorkFactory.UnfiedOrder(sendInfo, news_img NetWorkFactory.Listerner() {
//                    @Override
//                    public void Success(String data) {
//                        Toast.makeText(getApplicationContext(), "生成预支付Id成功"+data, Toast.LENGTH_LONG).show();
//                        XStream stream = news_img XStream();
//                        stream.processAnnotations(PrepayIdInfo.class);
//                        bean = (PrepayIdInfo) stream.fromXML(data);
//                    }
//
//                    @Override
//                    public void Faiulre(String data) {
//                        Toast.makeText(getApplicationContext(), "生成预支付Id失败", Toast.LENGTH_LONG).show();
//                    }
//                });
                final DialogUtil dialogUtil = new DialogUtil(this);
                Connect.getInstance().post(getApplicationContext(), IService.GET_ORDER, null, new Connect.OnResponseListener() {
                    @Override
                    public void onSuccess(Object result) {
                        dialogUtil.dismiss();
                        Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(String message) {
                        dialogUtil.dismiss();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.check_pay_btn:
                Pay();
                break;
        }
    }

    //生成预支付随机签名
    public static String genSign(OrederSendInfo info) {
        StringBuffer sb = new StringBuffer(info.toString());
        if (Constants.API_KEY.equals("")) {
            Toast.makeText(MyApplication.getInstance(), "APP_ID为空", Toast.LENGTH_LONG).show();
        }
        //拼接密钥
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes());

        return appSign;
    }

    //生成支付随机签名
    private static String genAppSign(List<OkHttpUtils.Param> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).key);
            sb.append('=');
            sb.append(params.get(i).value);
            sb.append('&');
        }
        //拼接密钥
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        return appSign.toUpperCase();
    }

    //生成随机字符串
    public String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    //获得时间戳
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public void Pay() {
        api.registerApp(Constants.APP_ID);
        PayReq req = new PayReq();
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = bean.getPrepay_id();
        req.packageValue = "Sign=" + bean.getPrepay_id();
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<OkHttpUtils.Param> signParams = new LinkedList<OkHttpUtils.Param>();
        signParams.add(new OkHttpUtils.Param("appid", req.appId));
        signParams.add(new OkHttpUtils.Param("noncestr", req.nonceStr));
        signParams.add(new OkHttpUtils.Param("package", req.packageValue));
        signParams.add(new OkHttpUtils.Param("partnerid", req.partnerId));
        signParams.add(new OkHttpUtils.Param("prepayid", req.prepayId));
        signParams.add(new OkHttpUtils.Param("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
    }

    private Handler myHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case what:
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    public void sendHttpRequest(final String address) {
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    // 请求方式
                    connection.setRequestMethod("POST");
                    // 连接超时
                    connection.setConnectTimeout(8000);
                    // 读取超时
                    connection.setReadTimeout(8000);
                    String data = "appid=" + URLEncoder.encode("wxddda0822580a4630", "UTF-8") +
                            "mch_id=" + URLEncoder.encode("1487994972", "UTF-8") +
                            "nonce_str=" + URLEncoder.encode("845eccca7b52a16f1447435dff9e2ed2", "UTF-8") +
                            "sign=" + URLEncoder.encode("89f34c2754811e60875cc973ddac30fe", "UTF-8") +
                            "body=" + URLEncoder.encode("融e还会员升级", "UTF-8") +
                            "out_trade_no=" + URLEncoder.encode("20150806125346", "UTF-8") +
                            "total_fee=" + 199 +
                            "spbill_create_ip=" + URLEncoder.encode("106.121.140.132", "UTF-8") +
                            "notify_url=" + URLEncoder.encode("http://www.weixin.qq.com/wxpay/pay.php", "UTF-8") +
                            "trade_type=" + URLEncoder.encode("APP", "UTF-8");

                    // 设置请求的头
                    connection.setRequestProperty("Connection", "keep-alive");
                    // 设置请求的头
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // 设置请求的头
                    connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
                    // 设置请求的头
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

                    //获取输出流
                    OutputStream os = connection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    connection.connect();
                    // 获取输入流

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "UTF-8"));
                    response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = 1;
                    myHandler1.sendMessage(message);
                } catch (Exception e) {

                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


}
