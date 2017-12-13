package com.example.dell.chatdemoreally.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.chatdemoreally.R;


public class otherFrag extends Fragment {

    Button sendImitate;
    View view;
    @Nullable
    @Override
    /**初始化布局*/
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_other,container,false);
        return view;
    }

    @Override
    /**初始化组件，用初始化布局时得到的view*/
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sendImitate = view.findViewById(R.id.send_imitate);
        sendImitate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent
                        ("com.example.dell.broadcast.WebSocket_BROADCAST");
                intent.putExtra("newMessage","模拟数据");
                getActivity().sendBroadcast(intent);
            }
        });
    }
}
