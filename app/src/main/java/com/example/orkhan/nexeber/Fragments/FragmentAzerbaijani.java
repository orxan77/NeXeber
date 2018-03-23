package com.example.orkhan.nexeber.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orkhan.nexeber.R;

/**
 * Created by Orkhan on 3/23/2018.
 */

public class FragmentAzerbaijani extends Fragment {

    public FragmentAzerbaijani(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.azerbaijani_fragment, container,false);
        return view;
    }
}
