package com.henryadamsjr.pipboy;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created with IntelliJ IDEA.
 * User: hadams
 * Date: 9/5/12
 * Time: 6:55 PM
 *
 * Place to store utility functions that could be useful in other projects.  Once it gets big enough, it might
 * get its own package.
 */
public class Util{

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }

}
