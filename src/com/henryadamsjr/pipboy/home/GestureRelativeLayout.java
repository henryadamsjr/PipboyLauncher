package com.henryadamsjr.pipboy.home;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private float density = getContext().getResources().getDisplayMetrics().density;

    public GestureRelativeLayout(Context context) {
        super(context);
        initialize();
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setOnTouchListener(this);
        gestureDetector = new GestureDetector(getContext(), this);
    }

    /*
        This prevents the touch from getting to the ListView
     */

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        ((CustomListView)findViewById(R.id.all_apps)).setDown(motionEvent.getY());
        Log.d("Pipboy", String.valueOf(motionEvent.getY()));
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d("Pipboy", "Press");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d("Pipboy", "SingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (Math.abs(distanceY) > Math.abs(distanceX)) {
            CustomListView clv = (CustomListView)findViewById(R.id.all_apps);
            clv.scrollSelection((int)e2.getY());
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d("Pipboy", "LongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

}
