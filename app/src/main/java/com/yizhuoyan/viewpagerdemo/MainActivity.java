package com.yizhuoyan.viewpagerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {


    /**
     * viewPager 适配器
     */
    class MyPagerAdapter extends PagerAdapter {
        private final Context context;
        private List<View> pages = new ArrayList<View>();

        MyPagerAdapter(Context context) {
            this.context = context;
            initPagers();
        }

        private TextView createTextView(String txt) {
            TextView view = new TextView(context);
            view.setTextSize(100);
            view.setText(txt);
            view.setGravity(Gravity.CENTER);
            return view;
        }

        private void initPagers() {
            //load pages
            pages.add(createTextView("D"));
            for (char i = 'A'; i <= 'D'; i++) {
                pages.add(createTextView(i + ""));
            }
            pages.add(createTextView("A"));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pages.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pages.get(position));
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view ==o;
        }
    }

    ;


    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private LinearLayout dotLinearLayout;
    private int currentIndex = -1;
    private int totalDot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) this.findViewById(R.id.main_vp_test);
        viewPager.setAdapter((myPagerAdapter = new MyPagerAdapter(this)));
        viewPager.setOnPageChangeListener(this);
        dotLinearLayout = (LinearLayout) findViewById(R.id.main_ll_dot);
        initDot(myPagerAdapter.getCount() - 2);
        viewPager.setCurrentItem(1);

    }

    private void initDot(int amount) {
        totalDot = amount;
        for (int i = 0; i < amount; i++) {
            View dotView = new ImageView(this);
            dotView.setBackgroundResource(R.drawable.main_dot);
            dotView.setEnabled(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            dotLinearLayout.addView(dotView, params);
        }
        this.onPageSelected(0);

    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i > 0 && i < myPagerAdapter.getCount() - 1) {
            i--;
            if (currentIndex != -1) {
                dotLinearLayout.getChildAt(currentIndex).setEnabled(false);
            }
            dotLinearLayout.getChildAt(i).setEnabled(true);
            currentIndex = i;
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (viewPager.getCurrentItem() == myPagerAdapter.getCount() - 1) {
                viewPager.setCurrentItem(1, false);
                return;
            }
            if (viewPager.getCurrentItem() == 0) {
                viewPager.setCurrentItem(myPagerAdapter.getCount() - 2, false);
                return;
            }
        }

    }
}
