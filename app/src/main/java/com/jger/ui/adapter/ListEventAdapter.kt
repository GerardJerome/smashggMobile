package com.jger.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TournamentQuery
import com.jger.R

class ListEventAdapter(listEvents: List<TournamentQuery.Event?>?) : RecyclerView.Adapter<ListEventAdapter.EventViewHolder>() {
lateinit var  listEvent : List<TournamentQuery.Event>

    class EventViewHolder(v : View) : RecyclerView.ViewHolder(v){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout,parent,false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.
    }
}