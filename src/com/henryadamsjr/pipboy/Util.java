package com.henryadamsjr.pipboy;

import android.util.DisplayMetrics;

/**
 * Created with IntelliJ IDEA.
 * User: hadams
 * Date: 9/5/12
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util{

    public static int getPXfromDP(DisplayMetrics metrics, float dp) {
        float fpixels = metrics.density * dp;

        return (int)(metrics.density * dp + 0.5f);
    }

}
