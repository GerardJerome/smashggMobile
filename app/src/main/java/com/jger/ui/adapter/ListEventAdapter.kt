package com.jger.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.TournamentQuery
import com.jger.R

class ListEventAdapter(val listEvents: List<TournamentQuery.Event?>?) : RecyclerView.Adapter<ListEventAdapter.EventViewHolder>() {

    class EventViewHolder(v : View) : RecyclerView.ViewHolder(v){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout,parent,false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listEvents!!.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.event_name_txt).text= listEvents!![position]!!.name
    }
}