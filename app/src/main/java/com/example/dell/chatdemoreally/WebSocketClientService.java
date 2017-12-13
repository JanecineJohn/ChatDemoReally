package com.example.dell.chatdemoreally;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientService extends Service {
    WebSocketClient mWebSocketClient;
    final String address = "ws://10.243.6.27:8080/websocket/onClass";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    //内部类，里面提供方法，返回此service对象
    public class MyBinder extends Binder{
        public WebSocketClientService getService(){
            return WebSocketClientService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeConnect();
    }

    //连接服务器
    private void connect(){
        new Thread(){
            @Override
            public void run() {
                try {
                    mWebSocketClient = new WebSocketClient(new URI(address)) {
                        @Override
                        public void onOpen(ServerHandshake serverHandshake) {
                            Log.d("webSocket相关信息---->","onOpen，建立webSocket连接");
                            sendMsg("成功连接到服务器");
                        }

                        @Override
                        public void onMessage(String s) {
                            //当有服务端发送来消息的时候，回调此函数
                            Log.d("webSocket相关信息---->","onMessage，服务端发送过来的信息为：" + s);
                            Intent intent = new Intent
                                    ("com.example.dell.broadcast.WebSocket_BROADCAST");
                            intent.putExtra("newMessage",s);
                            sendBroadcast(intent);
                        }

                        @Override
                        public void onClose(int i, String s, boolean b) {
                            //连接断开
                            Log.d("webSocket相关信息---->","onClose，连接断开"+ i + "/" + s + "/" + b);
                            closeConnect();
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("webSocket相关信息---->","onError，出错：" + e);
                        }
                    };
                    mWebSocketClient.connect();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //断开连接
    private void closeConnect(){
        try {
            mWebSocketClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mWebSocketClient = null;
        }
    }
    //发送信息
    public void sendMsg(String msg){
        mWebSocketClient.send(msg);
    }
}
