package com.example.dell.chatdemoreally.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dell.chatdemoreally.fragments.chatFrag;
import com.example.dell.chatdemoreally.fragments.otherFrag;

/**
 * Created by dell on 2017/12/13.
 */

public class FPAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"其他界面","聊天界面"};
    public FPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0){
            fragment = new otherFrag();
        }
        if (position == 1){
            fragment = new chatFrag();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles[position];
//    }
}
