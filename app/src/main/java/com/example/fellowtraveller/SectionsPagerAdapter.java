package com.example.fellowtraveller;

import android.text.style.UpdateAppearance;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProsforesFragment requestsFragment = new ProsforesFragment();
                Log.e("case 0","case 0");
                return requestsFragment;
            case 1:
                HomeFragment chatsFragment = new HomeFragment();
                Log.e("case 1","case 1");
                return chatsFragment;
            case 2:
                SearchFragment friendsFragment = new SearchFragment();
                Log.e("case 2","case 2");
                return friendsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Προσφορές";
            case 1:

                return "Αρχική";
            case 2:

                return "Αναζητήσεις";
            default:
                return null;
        }

    }
}