package com.henryadamsjr.pipboy;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * GridView adapter to show the list of all installed applications.
 */
public class ApplicationsAdapter extends ArrayAdapter<ApplicationInfo> {

    private ArrayList<ApplicationInfo> mApplications;
    private int selectedPosition = 0;
    private Context appContext;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public ApplicationsAdapter(Context context, ArrayList<ApplicationInfo> apps) {
        super(context, 0, apps);
        mApplications = apps;
        appContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ApplicationInfo info = mApplications.get(position);

        if (convertView == null) {
            final LayoutInflater inflater = ((Activity) appContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.application, parent, false);
        }

        final ClickableTextView textView = (ClickableTextView) convertView.findViewById(R.id.label);

        Drawable selectionFrame = getContext().getResources().getDrawable(R.drawable.selection_frame);
        selectionFrame.setColorFilter(Home.FALLOUT_COLOR, PorterDuff.Mode.MULTIPLY);

        if (position == selectedPosition) {
            textView.setBackgroundDrawable(selectionFrame);
        }
        else {
            textView.setBackgroundResource(R.drawable.not_selected_frame);
        }

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "monofont.ttf");
        textView.setTypeface(font);
        textView.setText(info.title);
        textView.setTextColor(Home.FALLOUT_COLOR);

        return convertView;
    }
}