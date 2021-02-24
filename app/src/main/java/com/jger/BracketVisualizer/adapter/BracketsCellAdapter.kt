package com.jger.BracketVisualizer.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jger.BracketVisualizer.Fragment.BracketsColomnFragment
import com.jger.BracketVisualizer.model.MatchData
import com.jger.BracketVisualizer.viewholder.BracketsCellViewHolder
import com.jger.R
import java.util.*

/**
 * Created by Emil on 21/10/17.
 */
class BracketsCellAdapter(
    bracketsColomnFragment: BracketsColomnFragment,
    context: Context,
    list: ArrayList<MatchData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val fragment: BracketsColomnFragment
    private val context: Context
    private var list: ArrayList<MatchData>
    private var handler = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.layout_cell_brackets, parent, false)
        return BracketsCellViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder: BracketsCellViewHolder? = null
        if (holder is BracketsCellViewHolder) {
            viewHolder = holder as BracketsCellViewHolder?
            setFields(viewHolder, position)
        }
    }



    private fun setFields(viewHolder: BracketsCellViewHolder?, position: Int) {
        handler =
            Handler().postDelayed({ viewHolder!!.setAnimation(list[position].height) }, 100)
        viewHolder!!.teamOneName.text = list[position].competitorOne.name
        viewHolder!!.teamTwoName.text = list[position].competitorTwo.name
        viewHolder!!.teamOneScore.text = list[position].competitorOne.score
        viewHolder!!.teamTwoScore.text = list[position].competitorTwo.score
    }



    fun setList(colomnList: ArrayList<MatchData>) {
        list = colomnList
        notifyDataSetChanged()
    }

    init {
        fragment = bracketsColomnFragment
        this.context = context
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size
    }
}