package com.kym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kym.ui.activity.bpbro_base.BaseActivity;
import com.kym.ui.activity.sun_util.ToastUtil;
import com.kym.ui.appconfig.IService;
import com.kym.ui.model.ZhiHang;
import com.kym.ui.util.Connect;
import com.kym.ui.util.Connect.OnResponseListener;
import com.kym.ui.util.DialogUtil;
import com.kym.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 查找支行
 *
 * @author sun
 * @date 2019/12/30
 */

public class SearchActivity extends BaseActivity implements OnClickListener {

    private TextView tv_title;
    private EditText edit;
    private TextView search;
    private ListView lv;
    private SearchAdapter searchAdapter;
    private DialogUtil loadDialogUtil2;
    private String province;
    private String city;
    private String bank_name;
    private ArrayList<ZhiHang.DataBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        province = intent.getStringExtra("province");
        city = intent.getStringExtra("city");
        bank_name = intent.getStringExtra("bank_name");
        initUI();
        getdata2();
    }

    private void getdata2() {
        loadDialogUtil2 = new DialogUtil(this);
        data.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("province", province);
        params.put("city", city);
        params.put("bank_name", bank_name);
        params.put("bankSub_key", edit.getText().toString().trim());
        Connect.getInstance().post(SearchActivity.this, IService.GET_ZBANK, params, new OnResponseListener() {
            @Override
            public void onSuccess(Object result) {
                loadDialogUtil2.dismiss();
                ZhiHang response = (ZhiHang) JsonUtils.parse((String) result, ZhiHang.class);
                if (response.getResult().getCode() == 10000) {
                    if (response.getData() != null && response.getData().size() > 0) {
                        if (!edit.getText().toString().trim().equals("")) {
                            for (int i = 0; i < response.getData().size(); i++) {
                                if (response.getData().get(i).getBank_sub().contains(edit.getText().toString().trim())) {
                                    data.add(response.getData().get(i));
                                }
                            }
                            searchAdapter.notifyDataSetChanged();
                        } else {
                            data.addAll(response.getData());
                            searchAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtil.showTextToas(getApplicationContext(), "暂无支行信息,请更换储蓄卡试试");
                    }
                } else {
                    ToastUtil.showTextToas(getApplicationContext(), response.getResult().getMsg());
                }

            }

            @Override
            public void onFailure(String message) {
                loadDialogUtil2.dismiss();
                ToastUtil.showTextToas(getApplicationContext(), message);
            }
        });

    }

    private void initUI() {
        tv_title = (TextView) findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        tv_title.setText("支行查询");
        edit = (EditText) findViewById(R.id.editText1);
        search = (TextView) findViewById(R.id.login_btn_ok);
        search.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.listView1);
        searchAdapter = new SearchAdapter(SearchActivity.this, data);
        lv.setAdapter(searchAdapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("ZHIHANG", data.get(position).getBank_sub());
                intent.putExtra("ZHIHANGCODE", data.get(position).getBank_code());
                setResult(1, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img_left:
                finish();
                break;
            case R.id.login_btn_ok:
                String trim = edit.getText().toString().trim();
                if (trim.equals("") || trim == null) {
                    ToastUtil.showTextToas(getApplicationContext(), "搜索条件不可为空");
                } else {// TODO 搜索支行
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    }
                    getdata2();
                }
                break;
            default:
                break;
        }

    }

}
