package com.henryadamsjr.pipboy.home;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    private int selectedPosition = 0;
    private int down;
    private int numberOfTextFields = 8;
    private ApplicationsAdapter adapter;
    private ClickableTextView[] views;
    private int firstVisiblePosition = 0;
    private int maxFirstVisiblePosition;

    public CustomListView(Context context) {
        super(context);
        initialize();
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setOrientation(VERTICAL);

        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();

        views = new ClickableTextView[numberOfTextFields];

        for (int i = 0; i < numberOfTextFields; i++) {
            ClickableTextView clv = (ClickableTextView)inflater.inflate(R.layout.application, this, false);
            this.addView(clv);
            views[i] = clv;
            clv.setIndex(i);
        }
    }

    public void selectFromTextView(int index)
    {

    }

    public void setDown(float d) {
        down = (int)d;
    }

    public void scrollSelection(int newY) {

        int yPerRow = getHeight() / numberOfTextFields;

        int distanceY = newY - down;

        if (distanceY != 0) {

            int numOfMovements = distanceY / yPerRow;

            if (numOfMovements != 0) {
                down = down + (numOfMovements * yPerRow);
            }

            int movementDirection = distanceY / Math.abs(distanceY);

            for (int i = 0; i != numOfMovements; i = i + movementDirection) {
                setSelection(selectedPosition + movementDirection);
            }
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

    public void launchSelectedApp()
    {
        ((Activity)getContext()).startActivity(adapter.getItem(selectedPosition).getIntent());
    }

    public void setSelection(int position) {

        if(position < 0)
        {
            position = 0;
        }
        if(position > adapter.getCount() - 1)
        {
            position = adapter.getCount() - 1;
        }

        views[selectedPosition - firstVisiblePosition].deselect();

        if (position < firstVisiblePosition) {
            setFirstVisiblePosition(position);
        } else if (position > firstVisiblePosition + numberOfTextFields - 1) {
            setFirstVisiblePosition(position - numberOfTextFields + 1);
        }

        views[position - firstVisiblePosition].select();

        ImageView iv = (ImageView)((View)getParent().getParent()).findViewById(R.id.app_icon);
        iv.setImageDrawable(adapter.getItem(position).getIcon());

        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public ApplicationInfo getSelectedItem() {
        /*
        if (selectedPosition > -1) {
            return (ApplicationInfo)getItemAtPosition(selectedPosition);
        } else {
            return null;
        }
        */
        return null;
    }
}
