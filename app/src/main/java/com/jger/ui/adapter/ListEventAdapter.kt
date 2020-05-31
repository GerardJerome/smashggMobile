package com.jger.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TournamentQuery

class ListEventAdapter(listEvents: List<TournamentQuery.Event?>?) : RecyclerView.Adapter<ListEventAdapter.EventViewHolder>() {
lateinit var  listEvent : List<TournamentQuery.Event>

    class EventViewHolder(v : View) : RecyclerView.ViewHolder(v){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return 
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}