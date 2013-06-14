package com.henryadamsjr.pipboy.home;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.henryadamsjr.pipboy.ApplicationInfo;
import com.henryadamsjr.pipboy.ApplicationsAdapter;
import com.henryadamsjr.pipboy.R;

/**
 * Created with IntelliJ IDEA.
 * User: henry.adams
 * Date: 8/29/12
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomListView extends LinearLayout {

    private static final int PADDING_LEFT = 30;
    private static final int PADDING_TOP = 5;
    private static final int PADDING_RIGHT = 20;
    private static final int PADDING_BOTTOM = 5;

    private int selectedPosition = 0;
    private int down;
    private int numberOfTextFields = 8;
    private ApplicationsAdapter adapter;
    private TextView[] views;
    private int firstVisiblePosition = 0;
    private int maxFirstVisiblePosition;

    public CustomListView(Context context) {
        super(context);
        if (!isInEditMode()) {
            initialize();
        }
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initialize();
        }
    }

    private void initialize() {
        setOrientation(VERTICAL);

        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();


        views = new TextView[numberOfTextFields];

        for (int i = 0; i < numberOfTextFields; i++) {
            TextView tv = (TextView) inflater.inflate(R.layout.application, this, false);
            tv.setTypeface(Home.FONT);
            tv.setTextColor(Home.FALLOUT_COLOR);
            tv.setTextSize(20);
            tv.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);

            this.addView(tv);
            views[i] = tv;
        }
    }

    public void selectFromTextView(int index) {

    }

    public void setDown(float d) {
        down = (int) d;
    }

    public void flingSelection(float velocity) {
        scrollSelectionBy(((int) velocity / getYPerRow()) * 2);
    }

    private void scrollSelectionBy(int numOfMovements) {


        if (numOfMovements != 0) {
            down = down + (numOfMovements * getYPerRow());
            int movementDirection = numOfMovements / Math.abs(numOfMovements);

            for (int i = 0; i != numOfMovements; i = i + movementDirection) {
                setSelection(selectedPosition + movementDirection);
            }
        }
    }

    private int getYPerRow() {
        return getHeight() / numberOfTextFields;
    }

    public void scrollSelection(int newY) {

        int distanceY = newY - down;

        if (distanceY != 0) {

            scrollSelectionBy(distanceY / getYPerRow());

        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ApplicationsAdapter adapter) {
        this.adapter = adapter;
        maxFirstVisiblePosition = adapter.getCount() - numberOfTextFields;
        setFirstVisiblePosition(0);
        setSelection(0);
    }

    private void setFirstVisiblePosition(int fvp) {
        if (fvp < 0) {
            fvp = 0;
        }

        if (fvp > maxFirstVisiblePosition) {
            firstVisiblePosition = maxFirstVisiblePosition;
        } else {
            firstVisiblePosition = fvp;
        }

        for (int i = firstVisiblePosition; i < firstVisiblePosition + numberOfTextFields; i++) {
            views[i - firstVisiblePosition].setText(adapter.getItem(i).getTitle());
        }
    }

    public void launchSelectedApp() {
        getContext().startActivity(adapter.getItem(selectedPosition).getIntent());
    }

    public void select(TextView selection) {
        Drawable drawable = getResources().getDrawable(R.drawable.selection_frame);
        drawable.setColorFilter(Home.FALLOUT_COLOR, PorterDuff.Mode.MULTIPLY);
        selection.setBackgroundDrawable(drawable);

        selection.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
    }

    public void deselect(TextView deselection) {
        deselection.setBackgroundResource(0);

        deselection.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
    }

    public void setSelection(int position) {

        if (position < 0) {
            position = 0;
        }
        if (position > adapter.getCount() - 1) {
            position = adapter.getCount() - 1;
        }

        deselect(views[selectedPosition - firstVisiblePosition]);

        if (position < firstVisiblePosition) {
            setFirstVisiblePosition(position);
        } else if (position > firstVisiblePosition + numberOfTextFields - 1) {
            setFirstVisiblePosition(position - numberOfTextFields + 1);
        }

        select(views[position - firstVisiblePosition]);

        ImageView iv = (ImageView) ((View) getParent().getParent()).findViewById(R.id.app_icon);
        iv.setImageDrawable(adapter.getItem(position).getIcon());

        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
