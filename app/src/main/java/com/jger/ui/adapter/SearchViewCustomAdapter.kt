package com.jger.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.TempQuery
import com.jger.R
import com.jger.ui.MainActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class SearchViewCustomAdapter(val context : Context, var listTournament: List<TempQuery.Node?>?) : RecyclerView.Adapter<SearchViewCustomAdapter.TournamentViewHolder>() {





    class TournamentViewHolder(v : View) : RecyclerView.ViewHolder(v)  {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tournament_card_view_layout,parent,false)
        return TournamentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTournament!!.size
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.event_name_txt).text= listTournament!![position]!!.name
        holder.itemView.findViewById<TextView>(R.id.contact_txt).text = listTournament!![position]!!.primaryContact
        Picasso.get().load(listTournament!![position]!!.images!![0]!!.url).resize(200,400)
            .into(holder.itemView.findViewById<ImageView>(R.id.tournament_image))

    }

    fun updateList(nodes: List<TempQuery.Node?>?) {
        (context as MainActivity).runOnUiThread {
            listTournament=nodes
            notifyDataSetChanged()
        }

    }


}