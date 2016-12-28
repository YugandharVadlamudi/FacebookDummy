package com.compindia.viewpagerapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.compindia.viewpagerapp.Fragments.FristFragment;
import com.compindia.viewpagerapp.Fragments.SecoundFragment;
import com.compindia.viewpagerapp.Fragments.ThirdFragment;
import com.compindia.viewpagerapp.utils.ParallaxPageTransformerFragments;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends FragmentActivity {
    Integer[] vpSlideArray = {R.drawable.ic_vp_four
            , R.drawable.ic_vp_three
            , R.drawable.ic_vp_two};
    private int viewPagerPosition;
    private ViewPagerFragments viewPagerFragments;

    private Handler handler;
    private Runnable runnable;
    private Context context;
    private LayoutInflater layoutInflator;
    private LinearLayout linerLinearLayout;
    private ViewPager vpViewPager;
    private TextView tvSentenceOne;
    private TextView tvSentenceTwo;
    private TextView tvSentenceThree;
    private ImageView imageView;
    private RelativeLayout rlViewPager;
    private ViewPageAdapter viewPageAdapter;
    private List<Fragment> listFragments;
    private int viewPagerPositionPageSelect;
    private float sc;
    private String TAG = ViewPagerActivity.class.getSimpleName();
    private String[] stringText1;
    private String[] stringText2;
    private String[] stringText3;


//    private ParallaxVeiwPager vpViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        setUpViews();
    }


    private void setUpViews() {
        stringText1 = getResources().getStringArray(R.array.text1);
        stringText2 = getResources().getStringArray(R.array.text2);
        stringText3 = getResources().getStringArray(R.array.text3);
        Log.d(TAG, "setUpViews:stringText1-> " + stringText1);
        context = getApplicationContext();
        listFragments = new ArrayList<>();
        listFragments.add(new FristFragment());
        listFragments.add(new SecoundFragment());
        listFragments.add(new ThirdFragment());
        layoutInflator = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        vpViewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        tvSentenceOne = (TextView) findViewById(R.id.tv_viewpager_one);
        tvSentenceTwo = (TextView) findViewById(R.id.tv_viewpager_two);
        tvSentenceThree = (TextView) findViewById(R.id.tv_viewpager_three);
        rlViewPager = (RelativeLayout) findViewById(R.id.rl_viewpager);
        linerLinearLayout = (LinearLayout) findViewById(R.id.ll_textviews);
        setAadapters();
    }


    private void setAadapters() {
        viewPageAdapter = new ViewPageAdapter();
//        vpViewPager.setAdapter(viewPageAdapter);//ImageSlider with out Fragments
        viewPagerFragments = new ViewPagerFragments(getSupportFragmentManager());
        vpViewPager.setAdapter(viewPagerFragments);//ImageSlider with out Fragments
        vpViewPager.setOffscreenPageLimit(vpSlideArray.length);
//        vpViewPager.setPageTransformer(true, new ParallaxPagerTransformer(R.id.fl_fragments));
//        vpViewPager.setPageTransformer(true, new ParallaxPagerTransformer(R.id.iv_slideimages));
        pageTransformerFragments();//with fragments views
        vpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                tvSentenceOne.setAlpha(positionOffset);
                Log.d(TAG, "onPageScrolled: positionOffset->"+positionOffset);
                if (positionOffset == 0.0) {

                    linerLinearLayout.setAlpha(0.9138889f);
                } else {
                    linerLinearLayout.setAlpha(positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerPositionPageSelect = position;
                /*LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.layout_framelayout, null);*/
                if (position == 1) {
                    ((SecoundFragment) viewPagerFragments.getItem(position)).onU
                }
                tvSentenceOne.setVisibility(View.INVISIBLE);
                tvSentenceTwo.setVisibility(View.INVISIBLE);
                tvSentenceThree.setVisibility(View.INVISIBLE);
                rlViewPager.setVisibility(View.INVISIBLE);
                tvSentenceOne.setText(stringText1[position]);
                tvSentenceTwo.setText(stringText2[position]);
                tvSentenceThree.setText(stringText3[position]);
//                displayTextViewsThread();
//                displayTextViewsThreadAnimation();
                displayOnlyWithAnimation();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (viewPagerPosition == 0 || viewPagerPosition == 2) {
//                            handler.removeCallbacksAndMessages(null);
                        } else {
                            tvSentenceOne.setVisibility(View.INVISIBLE);
                            tvSentenceTwo.setVisibility(View.INVISIBLE);
                            tvSentenceThree.setVisibility(View.INVISIBLE);
                            rlViewPager.setVisibility(View.INVISIBLE);
                            tvSentenceOne.clearAnimation();
                            handler.removeCallbacksAndMessages(null);
//                            handler.getLooper().quit();
                        }
                        break;
                }
            }
        });
        tvSentenceOne.setText(stringText1[0]);
        tvSentenceTwo.setText(stringText2[0]);
        tvSentenceThree.setText(stringText3[0]);
//
        handler = new Handler();
//        displayTextViewsThreadAnimation();
        displayOnlyWithAnimation();
    }

    private void pageTransformerFragments() {
        ParallaxPageTransformerFragments parallaxPageTransformerFragments = new ParallaxPageTransformerFragments();
        parallaxPageTransformerFragments.addViewToParallax(new ParallaxPageTransformerFragments.ParallaxTransformInformation(R.id.iv_fragment_first, 2, 2));
        parallaxPageTransformerFragments.addViewToParallax(new ParallaxPageTransformerFragments.ParallaxTransformInformation(R.id.iv_fragment_secound, 4, 4));
        parallaxPageTransformerFragments.addViewToParallax(new ParallaxPageTransformerFragments.ParallaxTransformInformation(R.id.iv_fragment_third, 6, 6));
        vpViewPager.setPageTransformer(true, parallaxPageTransformerFragments);
    }

    /**
     * ViewPagerAdapter for ImageSlide
     */
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
        public View instantiateItem(ViewGroup container, int position) {
            View view = mLayoutInflater.inflate(R.layout.layout_vp_slideimages, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_slideimages);
            imageView.setImageResource(vpSlideArray[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);
        }
    }

    class ViewPagerFragments extends FragmentPagerAdapter {
        public ViewPagerFragments(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new FristFragment();
                    FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
                    return fragment;
                case 1:
                    fragment = new SecoundFragment();
                    return fragment;
//                            new SecoundFragment();
                case 2:
                    fragment = new ThirdFragment();
                    return fragment;
//                    return new ThirdFragment();
                default:
                    return null;
            }
//            return null;
        }

/*
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
*/
    }

    class ViewPageAdapterFragment extends PagerAdapter {
        LayoutInflater mLayoutInflater;

        public ViewPageAdapterFragment() {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            Log.e(TAG, "getCount: size->" + listFragments.size());
            return listFragments.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((FrameLayout) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            Log.e(TAG, "instantiateItem: ViewGroup->" + container + "\n position->" + position);
            View view = mLayoutInflater.inflate(R.layout.layout_framelayout, container, false);
            FrameLayout imageView = (FrameLayout) view.findViewById(R.id.fl_fragments);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.no_animation, R.anim.no_animation);
            fragmentTransaction.replace(R.id.fl_fragments, listFragments.get(position));
//            fragmentTransaction.commit();

//                imageView.setImageResource(vpSlideArray[position]);
            container.addView(view);
            return view;
//            return null;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((FrameLayout) object);
        }
    }


    /**
     * ParallaxEffectTransformation pageTransfromation
     * viewPager
     */


    private void displayTextViewsThreadAnimation() {
        runnable = new Runnable() {
            @Override
            public void run() {
                tvSentenceOne.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvSentenceTwo.setVisibility(View.VISIBLE);
                        animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                tvSentenceThree.setVisibility(View.VISIBLE);
                                animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        rlViewPager.setVisibility(View.VISIBLE);
                                        animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                                        rlViewPager.startAnimation(animation);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                tvSentenceThree.startAnimation(animation);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        tvSentenceTwo.startAnimation(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                tvSentenceOne.setAnimation(animation);
            }
        };
        handler.postDelayed(runnable, 100);
    }
    public void displayOnlyWithAnimation() {
        tvSentenceOne.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvSentenceTwo.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvSentenceThree.setVisibility(View.VISIBLE);
                        animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                rlViewPager.setVisibility(View.VISIBLE);
                                animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha);
                                rlViewPager.startAnimation(animation);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        tvSentenceThree.startAnimation(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                tvSentenceTwo.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tvSentenceOne.setAnimation(animation);
    }
}
