package com.jger.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.TournamentQuery
import com.jger.R
import com.jger.transferClass.SetTransfer
import com.jger.ui.BracketViewerActivity
import com.jger.ui.PhaseActivity
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

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
        holder.itemView.findViewById<TextView>(R.id.nombre_entrant_txt).text =
            """${listEvents!![position]!!.numEntrants.toString()} participant(s)"""
        val date = Date(Integer.parseInt(listEvents!![position]!!.startAt.toString())*1000L)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        holder.itemView.findViewById<TextView>(R.id.start_at_txt).text = dateFormat.format(date)
        Picasso.get().load(listEvents!![position]!!.videogame!!.images!![0]!!.url).resize(200,400)
            .into(holder.itemView.findViewById<ImageView>(R.id.game_image))
        holder.itemView.setOnClickListener { v ->
            run {
                holder.itemView.context.startActivity(Intent(holder.itemView.context,PhaseActivity::class.java).putExtra("eventSlug",listEvents!![position]!!.slug))
            }
        }
    }
}