package com.henryadamsjr.pipboy.home;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.henryadamsjr.pipboy.ApplicationInfo;

/**
 * Created with IntelliJ IDEA.
 * User: henry.adams
 * Date: 8/29/12
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomListView extends LinearLayout {

    private int selectedPosition = -1;
    private int down;
    private int numOfRows;
    private int yPerRow;

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDown(float d) {
        down = (int)d;
    }

    public void scrollSelection(int newY) {


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

    public void setSelection(int position) {

        /*if (position < 0) {
            position = 0;
        } else if (position > getChildCount()) {
            //position = getChildCount() - 1;
        }

        if (getLastVisiblePosition() == -1 || position == selectedPosition) {
            return;
        }

        ClickableTextView tv = (ClickableTextView)getChildAt(selectedPosition - getFirstVisiblePosition());
        if (tv != null) {
            tv.deselect();
        }

        if (position <= getFirstVisiblePosition() || position >= getLastVisiblePosition()) {
            smoothScrollToPosition(position);
        }


        ClickableTextView tv2 = (ClickableTextView)getChildAt(position - getFirstVisiblePosition());
        if (tv2 != null) {
            tv2.select();
        }
        */
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

    @Override
    protected void onDraw(Canvas canvas) {
        /*super.onDraw(canvas);

        numOfRows = getLastVisiblePosition() - getFirstVisiblePosition();
        yPerRow = getHeight() / numOfRows;

        if (selectedPosition == -1) {
            setSelection(0);
        }*/
    }
}
