package com.example.administrator.smartbutler.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.adapter.ChatListAdapter;
import com.example.administrator.smartbutler.entity.ChatListData;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.ShareUtils;
import com.example.administrator.smartbutler.utils.StaticClass;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BulterFragment extends Fragment implements View.OnClickListener {

    private SpeechSynthesizer mTts;

    private ListView mChatListView;
    private EditText et_text;
    private Button btn_send_text;

    private ChatListAdapter adapter;

    private List<ChatListData> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bulter, null);

        findView(view);

        return view;
    }

    //初始化view
    private void findView(View view) {

        //TTS讯飞
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mTts.setParameter( SpeechConstant.VOICE_NAME, "xiaoyan"); //名字
        mTts.setParameter( SpeechConstant.SPEED, "50");//语速
        mTts.setParameter( SpeechConstant.VOLUME, "80");//音量
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);//设置云端

        mChatListView = (ListView) view.findViewById(R.id.mChatListView);

        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send_text = (Button) view.findViewById(R.id.btn_send_text);
        btn_send_text.setOnClickListener(this);

        //设置适配器
        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);

        addLeftItem("你好 我是小优机器人");
//         addRightItem("你好 我是智能小管家");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_text:
                /**
                 * 逻辑
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.清空输入框的内容
                 * 5.添加你输入的内容到right_item
                 * 6.发送给机器人请求返回应答
                 * 7.显示机器人的返回内容到left_item
                 *
                 */
                //1.获取输入框的内容
                String text = et_text.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(text)) {
                    //3.判断长度不能大于30
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), "输入的内容不能超过30个字符", Toast.LENGTH_SHORT).show();
                    } else {
                        //4.清空输入框的内容
                        et_text.setText("");
                        //5.添加你输入的内容到right_item
                        addRightItem(text);
                        //6.发送给机器人请求返回应答
                        String url = "http://op.juhe.cn/robot/index?info=" +
                                      text + "&key=" + StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                //Toast.makeText(getActivity(), t , Toast.LENGTH_SHORT).show();
                                L.i(t);
                                parsingJSON(t);
                            }
                        });
                    }

                } else {
                    Toast.makeText(getActivity(), "输入框的内容不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    /*
      {
        "reason":"成功的返回",
        "result": //根据code值的不同，返回的字段有所不同
        {
        "code":100000, //返回的数据类型，请根据code的值去数据类型API查询
            "text":"你好啊，希望你今天过的快乐"
        },
            "error_code":0
        }
     */

    //解析JSON数据
    private void parsingJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String text = jsonResult.getString("text");
            //7.显示机器人的返回内容到left_item
            addLeftItem(text);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addRightItem(String text) {
        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    private void addLeftItem(String text) {

        Boolean isSpeak = ShareUtils.getBoolean(getActivity(), "is_speak", false);
        if (isSpeak) {
            startSpeak(text);
        }

        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    private void startSpeak(String text) {
        mTts.startSpeaking(text, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }

}
