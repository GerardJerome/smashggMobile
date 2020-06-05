package com.jger.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.TempQuery
import com.example.TournamentQuery
import com.jger.R
import com.jger.transferClass.EventTransfer
import com.jger.ui.ListEventActivity
import com.jger.ui.MainActivity
import com.jger.util.ApolloUtil
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
        return if(listTournament!=null) {
            listTournament!!.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tournament_name_txt).text= listTournament!![position]!!.name
        holder.itemView.findViewById<TextView>(R.id.contact_txt).text = listTournament!![position]!!.primaryContact
        if(listTournament!![position]!!.images!!.isNotEmpty()) {
            Picasso.get().load(listTournament!![position]!!.images!![0]!!.url).resize(200, 400)
                .into(holder.itemView.findViewById<ImageView>(R.id.tournament_image))
        }
        holder.itemView.setOnClickListener{
            ApolloUtil.apolloClient
                .query(
                    TournamentQuery(listTournament!![position]!!.slug!!)
                )
                .requestHeaders(ApolloUtil.clientHeader)
                .enqueue(object : ApolloCall.Callback<TournamentQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d("jerom", e.toString())
                    }

                    override fun onResponse(response: Response<TournamentQuery.Data>) {
                        EventTransfer.listEvents=response.data!!.tournament!!.events!!
                        it.context.startActivity(Intent(it.context,ListEventActivity::class.java))
                    }

                })
        }

    }

    fun updateList(nodes: List<TempQuery.Node?>?) {
        (context as MainActivity).runOnUiThread {
            listTournament=nodes
            notifyDataSetChanged()
        }

    }


}