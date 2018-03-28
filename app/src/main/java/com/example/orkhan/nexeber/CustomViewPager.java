package com.example.orkhan.nexeber;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Orkhan on 3/28/2018.
 */

public class CustomViewPager extends ViewPager {

    private boolean isEnabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isEnabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isEnabled)
            return super.onInterceptTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnabled)
            return super.onTouchEvent(ev);
        return false;
    }

    public void setPagingEnabled(boolean enabled){
        this.isEnabled = enabled;
    }
}
