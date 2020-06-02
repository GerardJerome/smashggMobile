package com.jger.BracketVisualizer.customviews

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.jger.BracketVisualizer.application.BracketsApplication
import com.jger.application.MyApplication

/**
 * Created by Emil on 21/10/17.
 */
class WrapContentHeightViewPager(context : Context,attributeSet: AttributeSet) : ViewPager(context,attributeSet) {






    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until getChildCount()) {
            val child: View = getChildAt(i)
            child.measure(
                widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                )
            )
            val h = child.measuredHeight
            if (h > height) height = h
            val screenHeight: Int = MyApplication.getInstance()!!.getScreenHeight()
            if (screenHeight > height) height = screenHeight
            //overriding wrap content feature
            // int[] screenSize = getScreenSIze();
            // height = 1800;
        }
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            height,
            View.MeasureSpec.EXACTLY
        )
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private val screenSIze: IntArray
        private get() {
            val displaymetrics = DisplayMetrics()
            (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displaymetrics)
            val h = displaymetrics.heightPixels
            val w = displaymetrics.widthPixels
            return intArrayOf(w, h)
        }
}