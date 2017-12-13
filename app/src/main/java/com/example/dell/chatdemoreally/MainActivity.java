package com.example.dell.chatdemoreally;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.chatdemoreally.adapters.FPAdapter;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;//标签栏
    ViewPager viewPager;//滑动界面
    FPAdapter fpAdapter;//适配器，适配Fragment和viewPager

    //private final String address = "ws://localhost:10001/";
    String[] titles = new String[]{"其他界面","聊天界面"};
    int[] icons = new int[]{R.drawable.other,R.drawable.chat};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //fpAdapter的构造函数，需要传入一个FragmentManager
        fpAdapter = new FPAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fpAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i=0;i<titles.length;i++){
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(this).inflate(R.layout.view_tab,null);
        ((ImageView)view.findViewById(R.id.tab_iv)).setImageResource(icons[position]);
        ((TextView)view.findViewById(R.id.tab_tv)).setText(titles[position]);
        return view;
    }

}
