package com.example.covidinfrectedmap;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs = 2;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs){
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        Log.d("position: ", String.valueOf(position));
        switch(position){
            case 0:
                ListFragment listFragment = new ListFragment();
                return listFragment;
            case 1:
                MapFragment mapFragment = new MapFragment();
                return mapFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
