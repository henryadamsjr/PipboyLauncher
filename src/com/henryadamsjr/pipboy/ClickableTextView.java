package com.henryadamsjr.pipboy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: henry.adams
 * Date: 8/27/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClickableTextView extends TextView implements View.OnTouchListener, View.OnLongClickListener {

    private Drawable selectionFrame;

    public ClickableTextView(Context context) {
        super(context);
        initialize();
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setOnTouchListener(this);
        setOnLongClickListener(this);
        setBackgroundResource(R.drawable.not_selected_frame);

        Resources res = getContext().getResources();
        selectionFrame = res.getDrawable(R.drawable.selection_frame);
        selectionFrame.setColorFilter(Home.FALLOUT_COLOR, PorterDuff.Mode.MULTIPLY);
    }

    public boolean onLongClick(View view) {
        Log.d("Pipboy", "Long CLik");
        ListView lv = (ListView) view.getParent();
        ApplicationsAdapter aa = (ApplicationsAdapter) lv.getAdapter();
        ApplicationInfo appInfo = aa.getItem(lv.getPositionForView(this));
        view.getContext().startActivity(appInfo.intent);
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ListView lv = (ListView) view.getParent();
            ApplicationsAdapter aa = (ApplicationsAdapter)lv.getAdapter();
            ApplicationInfo appInfo = aa.getItem(lv.getPositionForView(view));
            aa.setSelectedApp(appInfo);
            LinearLayout ll = (LinearLayout) lv.getParent();
            ImageView iv = (ImageView) ll.findViewById(R.id.app_icon);
            iv.setImageDrawable(appInfo.icon);

            int i = lv.getFirstVisiblePosition();
            TextView tv;

            while((tv = (TextView)lv.getChildAt(i - lv.getFirstVisiblePosition())) != null)
            {
                tv.setBackgroundResource(R.drawable.not_selected_frame);
                i++;
            }

            view.setBackgroundDrawable(selectionFrame);
        }
        return false;
    }
}
