package com.compindia.viewpagerapp.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Kiran on 10-08-2016.
 */
public class AlphaTransition implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        final float normalizedPosition = Math.abs(Math.abs(position) - 1);
        page.setAlpha(normalizedPosition);
       /* int pageWidth = page.getWidth();
        if (position < -1) {

            page.setAlpha(1);
        } else if (position <= 1) {
            page.setTranslationX(-position * (pageWidth / 2));
        } else {
            page.setAlpha(1);
        }*/
    }
}
