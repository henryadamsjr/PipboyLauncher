package com.henryadamsjr.pipboy.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.henryadamsjr.pipboy.R;
import com.henryadamsjr.pipboy.ScreenInfo;
import com.henryadamsjr.pipboy.Util;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: hadams
 * Date: 8/29/12
 * Time: 12:55 AM
 * <p/>
 * Allows the RelativeLayout to respond to gestures.
 */
public class GestureRelativeLayout extends RelativeLayout implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private GestureDetector gestureDetector;
    private Home home;
    private ScreenInfo screenInfo;

    public GestureRelativeLayout(Context context) {
        super(context);
        if (!isInEditMode()) {
            initialize(context);
        }
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initialize(context);
        }
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initialize(context);
        }
    }

    private void initialize(Context context) {
        Log.d(Home.LOG_TAG, "Initializing layout");
        home = (Home) context;
        setOnTouchListener(this);
        gestureDetector = new GestureDetector(getContext(), this);
        screenInfo = new ScreenInfo(home);
    }

    /*
        This prevents the touch from getting to the ListView
     */

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        ((CustomListView) findViewById(R.id.all_apps)).setDown(motionEvent.getY());
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
            CustomListView mList = (CustomListView) findViewById(R.id.all_apps);
            mList.scrollSelection((int) e2.getY());
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(Home.LOG_TAG, "X: " + distanceX + " Y: " + distanceY);
        if (Math.abs(distanceY) > Math.abs(distanceX)) {
            float velocity = distanceY / (e2.getEventTime() - e1.getEventTime());
            CustomListView mList = (CustomListView) findViewById(R.id.all_apps);
            mList.flingSelection(velocity);
        } else if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > 1000) {
            screenInfo.changeScreen((int) (distanceX / Math.abs(distanceX)));
            setUpScreen();
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public void selectCategory(TextView selectedCategory) {
        ArrayList<TextView> categoryViews = getCategoryViews();

        for(TextView categoryView : categoryViews){
            categoryView.setBackgroundResource(0);

            if (categoryView.equals(selectedCategory)){
                Log.d(Home.LOG_TAG, "Selected: " + categoryView.getText());
                Drawable drawable = getResources().getDrawable(R.drawable.selection_frame);
                drawable.setColorFilter(home.getPipboyColor(), PorterDuff.Mode.MULTIPLY);
                categoryView.setBackgroundDrawable(drawable);
            }

            categoryView.setPadding(
                    Util.convertDpToPixel(10, getContext()),  //left
                    Util.convertDpToPixel(1, getContext()),   //top
                    Util.convertDpToPixel(10, getContext()),  //right
                    Util.convertDpToPixel(3, getContext()));  //bottom
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        super.addView(child, index, params);

        /**if (child.getId() == R.id.middle_section && !isInEditMode()) {
         LinearLayout ll = (LinearLayout) child;
         ll.removeView(findViewById(R.id.left_bar));
         }                         */

        if (child.getId() == R.id.bottom_bar && !isInEditMode()) {

            ArrayList<TextView> categoryViews = getCategoryViews();

            for(TextView categoryView: categoryViews){
                categoryView.setTypeface(home.getFont());
                categoryView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        selectCategory((TextView) view);
                    }
                });
            }

            setUpScreen();
            selectCategory(categoryViews.get(0));
        }
    }

    private ArrayList<TextView> getCategoryViews(){

        ArrayList<TextView> categoryViews = new ArrayList<TextView>();
        LinearLayout ll = (LinearLayout)findViewById(R.id.bottom_bar);

        for (int i = 0; i < ll.getChildCount(); i++) {
            if (ll.getChildAt(i) instanceof TextView) {
                categoryViews.add((TextView) ll.getChildAt(i));
            }
        }

        return categoryViews;
    }

    public void setUpScreen() {
        String[] categories = screenInfo.getCategories();
        ArrayList<TextView> categoryViews = getCategoryViews();
        TextView screenName = (TextView) findViewById(R.id.screen_mode_name);
        if(screenName != null){
            screenName.setText(screenInfo.getScreenName());
            screenName.setTypeface(home.getFont());
        }

        for(int i = 0; i < categoryViews.size(); i++){
            if(i < categories.length){
                categoryViews.get(i).setText(categories[i]);
            }
        }
        setUpBattery();
    }

    private void changeBatteryText(String newBatteryLevel){
        TextView battery = (TextView)findViewById(R.id.battery_text);
        battery.setText(newBatteryLevel);
        battery.setTypeface(home.getFont());
    }

    private void setUpBattery(){
        final Handler handler = new Handler();

        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                int percent = (level*100)/scale;

                final String newBatteryLevel = "BP " + String.valueOf(percent) + "/100";
                handler.post( new Runnable() {

                    public void run() {
                        changeBatteryText(newBatteryLevel);
                    }
                });
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);

        home.registerReceiver(mBatInfoReceiver, filter);
    }
}
