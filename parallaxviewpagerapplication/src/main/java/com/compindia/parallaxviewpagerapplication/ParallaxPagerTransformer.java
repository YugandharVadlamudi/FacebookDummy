package com.compindia.parallaxviewpagerapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * This is working Fine
 * ParallaxPagerTransformer
 * */
public class ParallaxPagerTransformer implements ViewPager.PageTransformer {

    private int id;
    private int border = 5;
    private float speed = 0.10f;
    private String TAG="ParallaxPagerTransformer";

    public ParallaxPagerTransformer(int id) {
        this.id = id;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {

        View parallaxView = view.findViewById(id);
        if (view == null ) {
            Log.w("ParallaxPager", "There is no view");
        }
            if (position > -1 && position < 1) {
                float width = parallaxView.getWidth();
                Log.d(TAG, "transformPage: width->"+width);
                parallaxView.setTranslationX(-((position *width) + speed));
//                float sc = ((float)parallaxView.getWidth() - border)/ view.getWidth();
                float sc = ((float)parallaxView.getWidth() - border)/ view.getWidth();
                if (position == 0) {
                    view.setScaleX(1);
                    view.setScaleY(1);

                } else {
                    view.setScaleX(1);
                    view.setScaleY(1);
                }
            }
    }

    public int setBorder(int px) {
        border = px;
        return border;
    }

    public float setSpeed(float speed) {

        this.speed = speed;
        return this.speed;
    }
}