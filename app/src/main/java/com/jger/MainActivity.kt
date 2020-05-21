package com.jger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.TournamentQuery
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_btn.setOnClickListener { v ->
            run {
                ApolloClient.builder().serverUrl("https://api.smash.gg/gql/alpha").build()
                    .query(TournamentQuery("tournament/"+tournament_txt.text.toString()))
                    .enqueue(object : ApolloCall.Callback<TournamentQuery.Data>(){
                        override fun onFailure(e: ApolloException) {
                            Log.i("JGER", e.stackTrace.toString())
                        }

                        override fun onResponse(response: Response<TournamentQuery.Data>) {
                            Log.i("JGER",response.data!!.tournament!!.events!!.get(0)!!.name)
                        }

                    })
            }
        }
    }
}
