package com.compindia.parallaxviewpagerapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Integer[] vpSlideArray = {R.drawable.ic_vp_four
            , R.drawable.ic_vp_three
            , R.drawable.ic_vp_two};

    private ViewPager viewPager;
    private ImageView imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpVIews();
    }

    private void setUpVIews() {
        context = getApplicationContext();
        viewPager = (ViewPager) findViewById(R.id.vp_paralax);
        viewPager.setAdapter(new ViewPageAdapter());
        viewPager.setPageTransformer(true, new com.compindia.parallaxviewpagerapplication.ParallaxPagerTransformer(R.id.iv_slideimages));
        /*ParallaxPageTransformer parallaxPageTransformer = new ParallaxPageTransformer()
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.drawable.ic_vp_four, 2, 2))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.drawable.ic_vp_three, 4, 4));
        viewPager.setPageTransformer(true,parallaxPageTransformer);
*/
//        viewPager.setPageTransformer(true,new ParallaxPagerTransformer(R.id.vp_paralax));
    }
    class ViewPageAdapter extends PagerAdapter {
        LayoutInflater mLayoutInflater;
        public ViewPageAdapter() {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return vpSlideArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mLayoutInflater.inflate(R.layout.layout_vp_slideimages,container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_slideimages);
            imageView.setImageResource(vpSlideArray[position]);
            container.addView(view);

            /*imageView = new ImageView(getApplicationContext());
            *//*imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(),vpSlideArray[position]
                    ,getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight()));*//*
            imageView.setImageResource(vpSlideArray[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ((ViewPager) container).addView(imageView);*/
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);
        }
    }
}
