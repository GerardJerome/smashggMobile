package com.jger.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

class VerticalViewPager(context:Context, attributeSet : AttributeSet) : ViewPager(context,attributeSet) {
    init {
        setPageTransformer(true, VerticalPage())
        overScrollMode= View.OVER_SCROLL_NEVER

    }

    fun getIntercambioXY(ev : MotionEvent) : MotionEvent{
        ev.setLocation((ev.y /height)*width,(ev.x /width)*height)
        return ev
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val intercepted = super.onInterceptTouchEvent(getIntercambioXY(ev!!))
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(getIntercambioXY(ev!!))
    }

    class VerticalPage : ViewPager.PageTransformer{
        override fun transformPage(page: View, position: Float) {
            if(position<-1){
                page.alpha=0F
            }else if(position<=1){
                page.alpha=1F
                page.translationX=page.width * -position
                page.translationY = position*page.height
            }else{
                page.alpha=0F
            }
        }

    }
}