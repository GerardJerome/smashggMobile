package com.jger.BracketVisualizer.utility;

import android.util.DisplayMetrics;

import com.jger.BracketVisualizer.application.BracketsApplication;
import com.jger.application.MyApplication;


/**
 * Created by Emil on 21/10/17.
 */

public class BracketsUtility {
    public static int dpToPx(int dp) {

        DisplayMetrics displayMetrics = MyApplication.Companion.getInstance().getBaseContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
