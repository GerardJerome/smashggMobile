package com.jger.BracketVisualizer.viewholder

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jger.BracketVisualizer.animation.SlideAnimation
import com.jger.R

/**
 * Created by Emil on 21/10/17.
 */
class BracketsCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val teamOneName: TextView
    val teamTwoName: TextView
    val teamOneScore: TextView
    val teamTwoScore: TextView
    val identifier : TextView

    private var animation: Animation? = null
    private val rootLayout: RelativeLayout
    fun setAnimation(height: Int) {
        animation = SlideAnimation(
            rootLayout, rootLayout.height,
            height
        )
        animation!!.interpolator = LinearInterpolator()
        animation!!.duration = 200
        rootLayout.animation = animation
        rootLayout.startAnimation(animation)
    }

    init {
        teamOneName = itemView.findViewById<View>(R.id.team_one_name) as TextView
        teamTwoName = itemView.findViewById<View>(R.id.team_two_name) as TextView
        teamOneScore = itemView.findViewById<View>(R.id.team_one_score) as TextView
        teamTwoScore = itemView.findViewById<View>(R.id.team_two_score) as TextView
        rootLayout = itemView.findViewById<View>(R.id.layout_root) as RelativeLayout
        identifier = itemView.findViewById(R.id.team_title)

    }
}