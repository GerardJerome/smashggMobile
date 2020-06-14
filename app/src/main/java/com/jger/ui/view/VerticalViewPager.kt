package com.jger.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class VerticalViewPager(context:Context, attributeSet : AttributeSet) : ViewPager(context,attributeSet) {
    private var enableds = false


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return enableds && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return enableds && super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.enableds = enabled
    }

    fun isPagingEnabled(): Boolean {
        return enableds
    }
}