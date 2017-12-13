package com.example.dell.chatdemoreally.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.chatdemoreally.MainActivity;
import com.example.dell.chatdemoreally.R;
import com.example.dell.chatdemoreally.WebSocketClientService;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class chatFrag extends Fragment {


    IntentFilter intentFilter;//注册广播接收器用
    webSocketReceiver mwebSocketReceiver;//广播接收器
    WebSocketClientService service;//service对象，需要里面的方法发送信息
    View view;//后面需要用它来初始化组件
    TextView show;
    EditText editMessage;
    Button send;
    @Nullable
    @Override
    /**初始化布局*/
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat,container,false);
        return view;
    }

    @Override
    /**初始化组件，用初始化布局时得到的view*/
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show = view.findViewById(R.id.frag_chat_txv);
        editMessage = view.findViewById(R.id.frag_chat_et);
        send = view.findViewById(R.id.frag_chat_bt);

        //注册广播接收器
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.dell.broadcast.WebSocket_BROADCAST");
        mwebSocketReceiver = new webSocketReceiver();
        getActivity().registerReceiver
                (mwebSocketReceiver,intentFilter);

        //开启服务
        Intent intent = new Intent(getActivity(),WebSocketClientService.class);
        getActivity().bindService
                (intent,mServiceConnection,Context.BIND_AUTO_CREATE);

        //点击发送消息
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editMessage.getText().toString();
                if (message.equals("")){
                    Toast.makeText(getActivity(),"不能发送空消息",Toast.LENGTH_SHORT).show();
                }else {
                    service.sendMsg(message);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver
                (mwebSocketReceiver);//注销广播接收器
        //关闭后台服务
        getActivity().unbindService(mServiceConnection);
    }

    //内部类，用于接收webSocket发出的广播
    class webSocketReceiver extends BroadcastReceiver{
        //只要接收到webSocket发出的广播，就会回调此方法
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("newMessage");
            show.append(message + '\n');
        }
    }

    //匿名内部类，用于连接fragment和service
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((WebSocketClientService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };
}
