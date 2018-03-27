package com.example.orkhan.nexeber.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.orkhan.nexeber.Fragments.FragmentAllLanguages;
import com.example.orkhan.nexeber.Fragments.FragmentAzerbaijani;
import com.example.orkhan.nexeber.Fragments.FragmentEnglish;
import com.example.orkhan.nexeber.Fragments.FragmentRussian;
import com.example.orkhan.nexeber.R;

/**
 * Created by Orkhan on 3/23/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_FRAGMENTS = 4;
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentAllLanguages();
            case 1:
                return new FragmentAzerbaijani();
            case 2:
                return new FragmentEnglish();
            case 3:
                return new FragmentRussian();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.all_languages);
            case 1:
                return mContext.getString(R.string.azerbaijani_lang);
            case 2:
                return mContext.getString(R.string.english_lang);
            case 3:
                return mContext.getString(R.string.russian_lang);
            default:
                return null;
        }
    }
}
