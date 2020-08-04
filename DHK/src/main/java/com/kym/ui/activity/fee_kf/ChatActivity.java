package com.kym.ui.activity.fee_kf;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.paradigm.botkit.ChatFragment;
import com.paradigm.botkit.MessageAdapter;
import com.paradigm.botlib.BotLibClient;
import com.paradigm.botlib.MenuItem;
import com.paradigm.botlib.Message;
import com.kym.ui.R;
import com.kym.ui.activity.bpbro_base.BaseActivity;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity implements BotLibClient.ConnectionListener, BotLibClient.MessageListener, View.OnClickListener {

    protected ChatFragment chatFragment;

    public ChatActivity() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pd_activity_chat);
        this.chatFragment = (ChatFragment) this.getFragmentManager().findFragmentById(com.paradigm.botkit.R.id.pd_char_fragment);
        initHead();
    }

    private void initHead() {
        TextView tvTitle = findViewById(R.id.head_text_title);
        findViewById(R.id.head_img_left).setOnClickListener(this);
        findViewById(R.id.gone).setVisibility(View.GONE);
        tvTitle.setText("在线客服");
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.chatFragment.initBotClient();
    }

    protected void reloadMessageList() {
        this.chatFragment.reloadMessageList();
    }

    protected ListView getMessageListView() {
        return this.chatFragment.getMessageListView();
    }

    protected MessageAdapter getMessageAdapter() {
        return this.chatFragment.getMessageAdapter();
    }

    protected ArrayList<Message> getMessageData() {
        return this.chatFragment.getMessageData();
    }

    public void onConnectionStateChanged(int state) {
    }

    public void onReceivedSuggestion(ArrayList<MenuItem> suggestions) {
    }

    public void onAppendMessage(Message message) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img_left:
                finish();
                break;
        }
    }
}
