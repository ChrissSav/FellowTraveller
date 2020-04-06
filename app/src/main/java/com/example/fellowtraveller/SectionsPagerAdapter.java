package com.example.fellowtraveller;

import android.text.style.UpdateAppearance;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ProsforesFragment prosforesFragment  = new ProsforesFragment();
    private HomeFragment homeFragment  = new HomeFragment();
    private SearchFragment searchFragment  = new SearchFragment();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.i("SectionsPagerAdapter","SectionsPagerAdapter");

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.i("SectionsPagerAdapter","requestsFragment");
                return prosforesFragment;

            case 1:
                Log.i("SectionsPagerAdapter","homeFragment");
                return homeFragment;
            case 2:
                Log.i("SectionsPagerAdapter","searchFragment");
                return searchFragment;
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

                return "Εμπλεκόμενες";
            default:
                return null;
        }

    }
}