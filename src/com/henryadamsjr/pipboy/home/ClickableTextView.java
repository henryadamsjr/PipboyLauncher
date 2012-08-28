package com.henryadamsjr.pipboy.home;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.henryadamsjr.pipboy.ApplicationInfo;
import com.henryadamsjr.pipboy.ApplicationsAdapter;
import com.henryadamsjr.pipboy.R;

/**
 * Created with IntelliJ IDEA.
 * User: henry.adams
 * Date: 8/27/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClickableTextView extends TextView implements View.OnTouchListener, View.OnLongClickListener {

    private ApplicationInfo appInfo;

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

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "monofont.ttf");
        setTypeface(font);
        setTextColor(Home.FALLOUT_COLOR);
    }

    public boolean onLongClick(View view) {
        ListView lv = (ListView)view.getParent();
        ApplicationsAdapter aa = (ApplicationsAdapter)lv.getAdapter();
        ApplicationInfo appInfo = aa.getItem(lv.getPositionForView(this));
        view.getContext().startActivity(appInfo.getIntent());
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ListView lv = (ListView)view.getParent();
            ApplicationsAdapter aa = (ApplicationsAdapter)lv.getAdapter();
            ApplicationInfo appInfo = aa.getItem(lv.getPositionForView(view));
            aa.setSelectedApp(appInfo);
            LinearLayout ll = (LinearLayout)lv.getParent();
            ImageView iv = (ImageView)ll.findViewById(R.id.app_icon);
            iv.setImageDrawable(appInfo.getIcon());

            int i = lv.getFirstVisiblePosition();
            View v;

            while ((v = lv.getChildAt(i - lv.getFirstVisiblePosition())) != null) {
                v.setBackgroundResource(0);
                i++;
            }

            view.setBackgroundResource(R.drawable.selection_frame);
        }
        return false;
    }

    @Override
    public void setBackgroundResource(int resid) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        if(resid != 0)
        {
            Drawable drawable = getResources().getDrawable(resid);
            drawable.setColorFilter(Home.FALLOUT_COLOR, PorterDuff.Mode.MULTIPLY);
            setBackgroundDrawable(drawable);
        }
        else
        {
            super.setBackgroundResource(resid);
        }
        setPadding(left, top, right, bottom);
    }

    public ApplicationInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(ApplicationInfo appInfo) {
        this.appInfo = appInfo;
        setText(appInfo.getTitle());
    }
}
