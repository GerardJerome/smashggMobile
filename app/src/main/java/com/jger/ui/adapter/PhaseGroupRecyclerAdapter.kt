package com.jger.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.PhaseGroupByPhaseIdQuery
import com.jger.R
import com.jger.ui.BracketViewerActivity

class PhaseGroupRecyclerAdapter(val nodes: List<PhaseGroupByPhaseIdQuery.Node?>?) : RecyclerView.Adapter<PhaseGroupRecyclerAdapter.PhaseGroupViewHolder>() {


    class PhaseGroupViewHolder(v:View)  : RecyclerView.ViewHolder(v){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhaseGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.phase_group__card_view_layout,null)
        return PhaseGroupViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nodes!!.size
    }

    override fun onBindViewHolder(holder: PhaseGroupViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.phase_group_name_txt).text=nodes!![position]!!.displayIdentifier
        holder.itemView.setOnClickListener { view ->
            run {
                view.context.startActivity(Intent(view.context,BracketViewerActivity::class.java).putExtra("phaseGroupId",nodes!![position]!!.id))
            }
        }
    }


}