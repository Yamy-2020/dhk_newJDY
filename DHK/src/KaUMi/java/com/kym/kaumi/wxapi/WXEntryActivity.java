package com.kym.xinehuan.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.kym.ui.BuildConfig;
import com.kym.ui.util.SysUtils;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private final String WX_PACKAGE_NAME = "com.tencent.mm";
    public static final String APP_ID = BuildConfig.WX_APP_ID;
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // setContentView(R.layout.weixin);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(WXEntryActivity.this, APP_ID, true);
        api.handleIntent(getIntent(), WXEntryActivity.this);
        super.onCreate(savedInstanceState);

        Boolean result = SysUtils.isAvilible(WXEntryActivity.this, WX_PACKAGE_NAME);
        if (!result)
            Toast.makeText(WXEntryActivity.this, "未安装微信,请先安装微信", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp arg0) {
        String result = "";
        // 分享
        if (arg0.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            switch (arg0.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "发送成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送失败";
                    break;
                default:
                    result = "出现异常";
                    break;
            }
        } else if (arg0.getType() == ConstantsAPI.COMMAND_SENDAUTH) {// 登录
            SendAuth.Resp resp = (SendAuth.Resp) arg0;
            switch (arg0.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "成功授权";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "取消授权";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "授权失败";
                    break;
                default:
                    result = "出现异常";
                    break;
            }
        } else {
            result = arg0.errStr;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }


}
