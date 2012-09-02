package com.henryadamsjr.pipboy.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.henryadamsjr.pipboy.R;

/**
 * Created with IntelliJ IDEA.
 * User: hadams
 * Date: 8/29/12
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class GestureRelativeLayout extends RelativeLayout implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private GestureDetector gestureDetector;
    private Home home;
    private TextView[] categoryViews;


    public GestureRelativeLayout(Context context) {
        super(context);
        initialize(context);
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        home = (Home)context;
        setOnTouchListener(this);
        gestureDetector = new GestureDetector(getContext(), this);
    }

    /*
        This prevents the touch from getting to the ListView
     */

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        ((CustomListView)findViewById(R.id.all_apps)).setDown(motionEvent.getY());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (Math.abs(distanceY) > Math.abs(distanceX)) {
            CustomListView mList = (CustomListView)findViewById(R.id.all_apps);
            mList.scrollSelection((int)e2.getY());
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (Math.abs(distanceY) > Math.abs(distanceX)) {
            float velocity = distanceY / (e2.getEventTime() - e1.getEventTime());
            CustomListView mList = (CustomListView)findViewById(R.id.all_apps);
            mList.flingSelection(velocity);
        } else if (Math.abs(distanceX) > Math.abs(distanceY)) {
            Home home = (Home)getContext();

            int newCategory = home.getSelectedCategory() + (int)(distanceX / Math.abs(distanceX));
            if (newCategory >= home.getCategories().length) {
                newCategory = 0;
            } else if (newCategory < 0) {
                newCategory = home.getCategories().length - 1;
            }

            Intent intent = new Intent();
            intent.setClass(getContext().getApplicationContext(), Home.class);
            intent.putExtra(Home.SELECTED_CATEGORY, newCategory);

            getContext().startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public void selectCategory() {
        for (int i = 0; i < home.getCategories().length; i++) {

            categoryViews[i].setBackgroundResource(0);

            if (home.getSelectedCategory() == i) {
                Drawable drawable = getResources().getDrawable(R.drawable.selection_frame);
                drawable.setColorFilter(Home.FALLOUT_COLOR, PorterDuff.Mode.MULTIPLY);
                categoryViews[i].setBackgroundDrawable(drawable);
            }
        }
    }

    private TextView createCategory(LayoutInflater inflater, int index) {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "monofont.ttf");
        TextView tv = (TextView)inflater.inflate(R.layout.category, this, false);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        textParams.gravity = Gravity.CENTER_VERTICAL;
        textParams.weight = 1.0f;

        String[] categories = home.getCategories();

        tv.setText(categories[index]);

        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTypeface(font);
        tv.setTextColor(Home.FALLOUT_COLOR);
        tv.setTextSize(20);
        tv.setLayoutParams(textParams);

        tv.setBackgroundResource(R.drawable.selection_frame);

        categoryViews[index] = tv;

        return tv;
    }

    private FrameLayout createLine(LayoutInflater inflater) {
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lineParams.weight = 1.0f;
        lineParams.topMargin = 30;
        lineParams.bottomMargin = 30;

        FrameLayout fl = (FrameLayout)inflater.inflate(R.layout.line, this, false);
        fl.setLayoutParams(lineParams);

        return fl;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        super.addView(child, index, params);

        if (child.getId() == R.id.bottom_bar) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();

            categoryViews = new TextView[home.getCategories().length];

            for (int i = 0; i < home.getCategories().length; i++) {

                if (i == 0) {
                    ((LinearLayout)child).addView(createLine(inflater));
                }

                ((LinearLayout)child).addView(createCategory(inflater, i));


                ((LinearLayout)child).addView(createLine(inflater));
            }
            selectCategory();
        }
    }
}
