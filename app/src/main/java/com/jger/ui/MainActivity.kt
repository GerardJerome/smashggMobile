package com.jger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.TempQuery
import com.example.TournamentQuery
import com.jger.R
import com.jger.transferClass.EventTransfer
import com.jger.ui.adapter.SearchViewCustomAdapter
import com.jger.util.ApolloUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var adapter = SearchViewCustomAdapter(this,ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TournamentRecyclerView.adapter=adapter
        TournamentRecyclerView.layoutManager=LinearLayoutManager(this)
        tournament_sView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.length>3) {
                    ApolloUtil.apolloClient
                        .query(TempQuery("Wanted")).requestHeaders(ApolloUtil.clientHeader)
                        .enqueue(object : ApolloCall.Callback<TempQuery.Data>() {
                            override fun onFailure(e: ApolloException) {
                                TODO("Not yet implemented")
                            }

                            override fun onResponse(response: Response<TempQuery.Data>) {
                                adapter.updateList(response.data!!.tournaments!!.nodes)


                            }

                        })
            }
                return true
            }

        })


        /*search_btn.setOnClickListener { v ->
            run {
                ApolloUtil.apolloClient
                    .query(TournamentQuery("tournament/" + tournament_txt.text.toString()
                        .replace("[^A-Za-z0-9 ]", "-")
                        .replace(" ","-")
                        .replace("-{2,}","-")
                        .replace("-$","")))
                    .requestHeaders(ApolloUtil.clientHeader)
                    .enqueue(object : ApolloCall.Callback<TournamentQuery.Data>() {
                        override fun onFailure(e: ApolloException) {
                            Log.d("jerom", e.toString())
                        }

                        override fun onResponse(response: Response<TournamentQuery.Data>) {
                            EventTransfer.listEvents=response.data!!.tournament!!.events!!
                            startActivity(Intent(this@MainActivity,ListEventActivity::class.java))
                        }

                    })
            }
        }*/
    }
}
