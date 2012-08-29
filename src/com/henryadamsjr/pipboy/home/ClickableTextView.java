package com.henryadamsjr.pipboy.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.*;
import com.henryadamsjr.pipboy.ApplicationInfo;
import com.henryadamsjr.pipboy.R;

/**
 * Created with IntelliJ IDEA.
 * User: henry.adams
 * Date: 8/27/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClickableTextView extends TextView{

    private ApplicationInfo appInfo;

    private static final int PADDING_LEFT = 30;
    private static final int PADDING_TOP = 5;
    private static final int PADDING_RIGHT = 20;
    private static final int PADDING_BOTTOM = 5;

    private boolean inList = false;

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
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "monofont.ttf");
        setTypeface(font);
        setTextColor(Home.FALLOUT_COLOR);
        setTextSize(20);

        setCustomPadding();
    }

    @Override
    public void setBackgroundResource(int resid) {
        if (resid != 0) {
            Drawable drawable = getResources().getDrawable(resid);
            drawable.setColorFilter(Home.FALLOUT_COLOR, PorterDuff.Mode.MULTIPLY);
            setBackgroundDrawable(drawable);
        } else {
            super.setBackgroundResource(resid);
        }
        setCustomPadding();
    }


    public ApplicationInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(ApplicationInfo appInfo) {
        this.appInfo = appInfo;
        setText(appInfo.getTitle());
        inList = true;
        setCustomPadding();
    }

    private void setCustomPadding() {
        if (inList) {
            setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
        } else {
            setPadding(10, 3, 10, 5);
        }
    }

    public void select()
    {
        setBackgroundResource(R.drawable.selection_frame);
        invalidate();
    }

    public void deselect()
    {
        setBackgroundResource(0);
        invalidate();
    }
}
