package com.jger.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.EventQueryForPhaseIdQuery
import com.jger.R
import com.jger.ui.BracketViewerActivity
import com.jger.ui.PhaseGroupActivity

class PhaseRecyclerAdapter(val listPhase : List<EventQueryForPhaseIdQuery.Phase?>) : RecyclerView.Adapter<PhaseRecyclerAdapter.PhaseViewHolder>() {




    class PhaseViewHolder(v:View) : RecyclerView.ViewHolder(v) {

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.phase_card_view_layout,parent,false)
        return PhaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPhase.size
    }

    override fun onBindViewHolder(holder: PhaseViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.phase_name_txt).text = listPhase[position]!!.name
        holder.itemView.findViewById<TextView>(R.id.nombre_group_phase_txt).text =
            """${listPhase[position]!!.groupCount.toString()} groupe(s)"""
        holder.itemView.setOnClickListener { view ->
            run {
                if(listPhase[position]!!.groupCount!! >1){
                    view.context.startActivity(Intent(view.context,PhaseGroupActivity::class.java).putExtra("phaseId",listPhase[position]!!.id))
                }else{
                    view.context.startActivity(Intent(view.context,BracketViewerActivity::class.java).putExtra("phaseId",listPhase[position]!!.id))

                }
            }
        }
    }
}