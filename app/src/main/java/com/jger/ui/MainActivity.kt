package com.jger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.TournamentQuery
import com.jger.R
import com.jger.transferClass.EventTransfer
import com.jger.util.ApolloUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_btn.setOnClickListener { v ->
            run {
                ApolloUtil.apolloClient
                    .query(TournamentQuery("tournament/" + tournament_txt.text.toString()))
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
        }
    }
}
