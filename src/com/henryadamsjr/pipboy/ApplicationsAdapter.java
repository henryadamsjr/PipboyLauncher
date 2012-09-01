package com.henryadamsjr.pipboy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * GridView adapter to show the list of all installed applications.
 */
public class ApplicationsAdapter extends ArrayAdapter<ApplicationInfo> {

    private ArrayList<ApplicationInfo> mApplications;
    private Context appContext;
    private ApplicationInfo selectedApp;

    public ApplicationInfo getSelectedApp() {
        return selectedApp;
    }

    public void setSelectedApp(ApplicationInfo selectedApp) {
        this.selectedApp = selectedApp;
    }

    public ApplicationsAdapter(Context context, ArrayList<ApplicationInfo> apps) {
        super(context, 0, apps);
        mApplications = apps;
        appContext = context;
    }
}