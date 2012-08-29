package com.henryadamsjr.pipboy.home;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
public class CustomListView extends ListView{

    private int selectedPosition = -1;

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

    private void initialize()
    {

    }

    @Override
    public void setSelection(int position) {

        ClickableTextView tv = (ClickableTextView)getChildAt(selectedPosition - getFirstVisiblePosition());
        if(tv != null)
        {
            tv.deselect();
        }

        if(position < getFirstVisiblePosition() || position > getLastVisiblePosition())
        {
            smoothScrollToPosition(position);
        }

        tv = (ClickableTextView)getChildAt(position - getFirstVisiblePosition());
        if(tv != null)
        {
            tv.select();
        }

        selectedPosition = position;
    }

    @Override
    public int getSelectedItemPosition() {
        return selectedPosition;
    }

    @Override
    public ApplicationInfo getSelectedItem() {
        if(selectedPosition > -1)
        {
            return (ApplicationInfo)getItemAtPosition(selectedPosition);
        }
        else
        {
            return null;
        }
    }
}
