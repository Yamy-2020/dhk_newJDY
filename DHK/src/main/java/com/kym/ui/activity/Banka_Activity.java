package com.kym.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kym.ui.PublicWebActivity;
import com.kym.ui.R;

public class Banka_Activity extends Activity implements View.OnClickListener {

    private String type;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banka);
        initHead();
        initView();
    }

    private void initHead() {
        type = getIntent().getStringExtra("type");
        TextView tv = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        if (type.equals("staging")) {
            tv.setText("分期专属卡");
            findViewById(R.id.fenqi).setVisibility(View.VISIBLE);
        } else {
            tv.setText("分期联名卡");
            findViewById(R.id.lianming).setVisibility(View.VISIBLE);
        }
    }

    private void initView(){
        findViewById(R.id.li1).setOnClickListener(this);
        findViewById(R.id.li2).setOnClickListener(this);
        findViewById(R.id.li3).setOnClickListener(this);
//        findViewById(R.id.li4).setOnClickListener(this);
//        findViewById(R.id.li5).setOnClickListener(this);
//        findViewById(R.id.li6).setOnClickListener(this);

        findViewById(R.id.lianming_li1).setOnClickListener(this);
        findViewById(R.id.lianming_li2).setOnClickListener(this);
        findViewById(R.id.lianming_li3).setOnClickListener(this);
        findViewById(R.id.lianming_li4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.li1:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "工商银行星座金卡专属卡");
                intent1.putExtra("WEB_URL", "https://elife.icbc.com.cn/OFSTCARDWEB/dist/#/apply/index/10700000000000000000000000/231/00200/null/EW88802001100900000/null/460000012/null");
                startActivity(intent1);
                break;
            case R.id.li2:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "上海银行专属卡");
                intent1.putExtra("WEB_URL", "https://mbank.bankofshanghai.com/Latte/#/CreditHot?YLLink=0000001121");
                startActivity(intent1);
                break;
            case R.id.li3:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "浦发银行高端专属定制卡");
                intent1.putExtra("WEB_URL", "https://ecentre.spdbccc.com.cn/creditcard/indexActivity.htm?data=ZF2791278&itemcode=0000000485");
                startActivity(intent1);
                break;
//            case R.id.li4:
//                intent1 = news_img Intent(getApplicationContext(), PublicWebActivity.class);
//                intent1.putExtra("WEB_TITLE", "兴业银行金卡专属渠道");
//                intent1.putExtra("WEB_URL", "https://wm.cib.com.cn/html/webapp/fast-issue/parnter.html?id=7d41c00bbf1a49b58d45ce67be493431");
//                startActivity(intent1);
//                break;
//            case R.id.li5:
//                intent1 = news_img Intent(getApplicationContext(), PublicWebActivity.class);
//                intent1.putExtra("WEB_TITLE", "中信银行金卡专属渠道");
//                intent1.putExtra("WEB_URL", "https://creditcard.ecitic.com/m/tjbk/card_list.html?uid=TJ25153766&sid=SJDKTJBK6");
//                startActivity(intent1);
//                break;
//            case R.id.li6:
//                intent1 = news_img Intent(getApplicationContext(), PublicWebActivity.class);
//                intent1.putExtra("WEB_TITLE", "平安银行金卡专属卡");
//                intent1.putExtra("WEB_URL", "https://test-b-fat.pingan.com.cn/ca/pc/index.html#/First?channel=WX&onlineSQFlag=N&sign=21904d0e-49d0-4492-a93a-764bb56627d90c84f6f2c2b1fca67b3a95852631c10d&ccp=1a2a3a4a5a8a10a11at4&versionNo=R10310&scc=950000635&deleteCardinfo=Y");
//                startActivity(intent1);
//                break;
            case R.id.lianming_li1:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "中青旅联名卡");
                intent1.putExtra("WEB_URL", "https://xyk.cebbank.com/cebmms/apply/ps/card-index.htm?req_card_id=11749&pro_code=FHTG023529SJ01BJZQ&c2c_recom_flag=");
                startActivity(intent1);
                break;
            case R.id.lianming_li2:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "中青旅联名白金卡");
                intent1.putExtra("WEB_URL", "https://xyk.cebbank.com/cebmms/apply/ps/card-index.htm?req_card_id=11750&pro_code=FHTG023529SJ01ZQLG");
                startActivity(intent1);
                break;
            case R.id.lianming_li3:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "时尚芭莎联名白金卡");
                intent1.putExtra("WEB_URL", "https://creditapply.hxb.com.cn/hxbank/WSCradapply.do?_locale=zh_CN&ShowCards=0113&CardName=%E5%8D%8E%E5%A4%8F%E6%97%B6%E5%B0%9A%E8%8A%AD%E8%8E%8E%E8%81%94%E5%90%8D%E5%8D%A1%E9%87%91%E5%8D%A1%EF%BC%88%E4%B8%87%E4%BA%8B%E8%BE%BE%EF%BC%89&CardImg=https://creditapply.hxb.com.cn/images/cardimage/0113/0113.jpg&choiceFrom=cardChoice&channelId=weixin");
                startActivity(intent1);
                break;
            case R.id.lianming_li4:
                intent1 = new Intent(getApplicationContext(), PublicWebActivity.class);
                intent1.putExtra("WEB_TITLE", "时尚芭莎联名金卡");
                intent1.putExtra("WEB_URL", "https://creditapply.hxb.com.cn/hxbank/WSCradapply.do?_locale=zh_CN&ShowCards=0113&CardName=%E5%8D%8E%E5%A4%8F%E6%97%B6%E5%B0%9A%E8%8A%AD%E8%8E%8E%E8%81%94%E5%90%8D%E5%8D%A1%E9%87%91%E5%8D%A1%EF%BC%88%E4%B8%87%E4%BA%8B%E8%BE%BE%EF%BC%89&CardImg=https://creditapply.hxb.com.cn/images/cardimage/0113/0113.jpg&choiceFrom=cardChoice&channelId=weixin");
                startActivity(intent1);
                break;
        }
    }
}
