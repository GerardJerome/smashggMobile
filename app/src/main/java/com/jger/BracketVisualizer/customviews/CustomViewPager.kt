package com.jger.BracketVisualizer.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by Emil on 21/10/17.
 */
class CustomViewPager(
    context: Context?,
    attrs: AttributeSet?,var ok : Boolean
) : ViewPager(context!!, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (ok) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return if (ok) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.ok=enabled
    }

}