package net.edrop.edrop_user.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.TestData;
import net.edrop.edrop_user.adapter.ChatAdapter;
import net.edrop.edrop_user.entity.ChatModel;
import net.edrop.edrop_user.entity.ItemModel;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import static net.edrop.edrop_user.utils.Constant.RECEVIED_MSG;

public class ChatViewActivity extends AppCompatActivity implements EMMessageListener {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText etContent;
    private TextView tvSend;
    private String content;
    private ImageView ivBack;
    private String userId;
    private TextView chatNav;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==RECEVIED_MSG){
                ArrayList<ItemModel> data = new ArrayList<>();
                ChatModel model = new ChatModel();
                model.setContent(new Gson().fromJson((String) msg.obj,String.class));
                model.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575200975280&di=013453a7f9804cfa5ca5966f3d4ec05e&imgtype=0&src=http%3A%2F%2Fwww.uimaker.com%2Fuploads%2Fallimg%2F20140731%2F1406774235170879.png");
                data.add(new ItemModel(ItemModel.CHAT_A, model));
                adapter.notifyDataSetChanged();
                adapter.addAll(data);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(ChatViewActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        initView();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatAdapter());
        adapter.replaceAll(TestData.getTestAdData());// 测试用的

        userId = getIntent().getExtras().getString("userId");
        chatNav.setText(userId);
        EMClient.getInstance().chatManager().addMessageListener(this);

        initData();
        setLinster();
    }

    private void setLinster() {
        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                EMMessage messagelay = EMMessage.createTxtSendMessage(content,userId);
                EMClient.getInstance().chatManager().sendMessage(messagelay);
                ArrayList<ItemModel> data = new ArrayList<>();
                ChatModel model = new ChatModel();
                model.setIcon("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1575190893&di=186b0e34b0f1e51535794dedcbe0465e&src=http://b-ssl.duitang.com/uploads/item/201106/04/20110604152619_AaY5P.thumb.700_0.jpg");
                model.setContent(content);
                data.add(new ItemModel(ItemModel.CHAT_B, model));
                adapter.notifyDataSetChanged();
                adapter.addAll(data);
                etContent.setText("");
                hideKeyBorad(etContent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        chatNav=findViewById(R.id.chat_nav);
        ivBack=(ImageView)findViewById(R.id.ivBack);
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        etContent = (EditText) findViewById(R.id.etContent);
        tvSend = (TextView) findViewById(R.id.tvSend);
    }

    private void initData() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });
    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    //环信
    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        Observable.from(messages).subscribe(new Action1<EMMessage>() {
            @Override
            public void call(EMMessage emMessage) {
                EMTextMessageBody emtextmessage = (EMTextMessageBody) emMessage.getBody();
                Message msg = new Message();
                msg.obj = new Gson().toJson(emtextmessage.getMessage());
                msg.what = RECEVIED_MSG;
                mHandler.sendMessage(msg);
                Log.e("message",emtextmessage.getMessage());//获取信息
                chatNav.setText(emMessage.getFrom());//设置标题栏
            }
        });
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
