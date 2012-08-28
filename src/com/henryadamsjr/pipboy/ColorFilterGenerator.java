package com.henryadamsjr.pipboy;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/*
    Found on StackOverflow
    http://stackoverflow.com/questions/4354939/understanding-the-use-of-colormatrix-and-colormatrixcolorfilter-to-modify-a-draw

    I may or may not use this.
 */

public class ColorFilterGenerator
{
    /**
 * Creates a HUE ajustment ColorFilter
 * http://groups.google.com/group/android-developers/browse_thread/thread/9e215c83c3819953
 * http://gskinner.com/blog/archives/2007/12/colormatrix_cla.html
 * @param value degrees to shift the hue.
 * @return
 */
public static ColorFilter adjustHue( float value )
{
    ColorMatrix cm = new ColorMatrix();

    adjustHue(cm, value);

    return new ColorMatrixColorFilter(cm);
}

/**
 * http://groups.google.com/group/android-developers/browse_thread/thread/9e215c83c3819953
 * http://gskinner.com/blog/archives/2007/12/colormatrix_cla.html
 * @param cm
 * @param value
 */
public static void adjustHue(ColorMatrix cm, float value)
{
    value = cleanValue(value, 180f) / 180f * (float) Math.PI;
    if (value == 0)
    {
        return;
    }
    float cosVal = (float) Math.cos(value);
    float sinVal = (float) Math.sin(value);
    float lumR = 0.213f;
    float lumG = 0.715f;
    float lumB = 0.072f;
    float[] mat = new float[]
    {
            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
            0f, 0f, 0f, 1f, 0f,
            0f, 0f, 0f, 0f, 1f };
    cm.postConcat(new ColorMatrix(mat));
}

protected static float cleanValue(float p_val, float p_limit)
{
    return Math.min(p_limit, Math.max(-p_limit, p_val));
}
}