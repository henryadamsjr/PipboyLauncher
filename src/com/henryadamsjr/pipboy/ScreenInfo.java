package com.henryadamsjr.pipboy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import com.henryadamsjr.pipboy.home.Home;

/**
 * Created with IntelliJ IDEA.
 * User: hadams
 * Date: 6/16/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenInfo {

    private String[] categories;
    private String[] allScreenNames;
    private int currentScreen;
    private Context home;

    /**
     * Constants to relate the screen mode ID to the type of screen.
     */

    public static final int STATS = 0;
    public static final int ITEMS = 1;
    public static final int DATA = 2;

    public ScreenInfo(Context context) {
        home = context;
        allScreenNames = home.getResources().getStringArray(R.array.screen_modes);
        setUpScreenMode(0);
    }

    public void changeScreen(int modifier){
        Log.d(Home.LOG_TAG, "Change Screen Modifier: " + modifier);
        setUpScreenMode(currentScreen + modifier);
    }

    private void setUpScreenMode(int newScreen)   {

        Log.d(Home.LOG_TAG, "Changing to screen: " + newScreen);

        if(newScreen >= allScreenNames.length){
            currentScreen = 0;
        }
        else if(newScreen < 0){
            currentScreen = allScreenNames.length - 1;
        }
        else{
            currentScreen = newScreen;
        }

        Log.d(Home.LOG_TAG, "Current Screen is now: " + currentScreen);

        switch(currentScreen){
            case ITEMS:
                categories = home.getResources().getStringArray(R.array.items);
                break;
            case DATA:
                categories = home.getResources().getStringArray(R.array.data);
                break;
            case STATS:
            default:
                categories = home.getResources().getStringArray(R.array.stats);
        }
    }

    public String getScreenName(){
        return allScreenNames[currentScreen];
    }

    public String[] getCategories() {
        return categories;
    }
}
