package com.example.orkhan.nexeber.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.example.orkhan.nexeber.Models.DrawerListItem;
import com.example.orkhan.nexeber.R;

import java.util.ArrayList;

/**
 * Created by Orkhan on 3/28/2018.
 */

public class DrawerListItemAdapter extends ArrayAdapter<DrawerListItem> {

    private Context mContext;
    private CheckedTextView drawerWebsiteTextView;

    public DrawerListItemAdapter(@NonNull Context context, ArrayList<DrawerListItem> arrayList) {
        super(context, 0, arrayList);
        mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DrawerListItem drawerListItem = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item, parent, false);

        drawerWebsiteTextView = convertView.findViewById(R.id.drawer_list_item_website);
        drawerWebsiteTextView.setText(drawerListItem.getWebsite());

        return convertView;
    }
}
