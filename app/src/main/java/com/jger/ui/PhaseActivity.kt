package com.jger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.EventQueryForPhaseIdQuery
import com.example.TempQuery
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jger.R
import com.jger.ui.adapter.PhaseRecyclerAdapter
import com.jger.util.ApolloUtil
import kotlinx.android.synthetic.main.activity_phase.*

class PhaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase)
        ApolloUtil.apolloClient
            .query(EventQueryForPhaseIdQuery(intent.getStringExtra("eventSlug"))).requestHeaders(ApolloUtil.clientHeader)
            .enqueue(object : ApolloCall.Callback<EventQueryForPhaseIdQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    FirebaseCrashlytics.getInstance()
                        .log("Problème dans la requète de recherche de phase : ${e.message}")
                }

                override fun onResponse(response: Response<EventQueryForPhaseIdQuery.Data>) {
                    val adapter : PhaseRecyclerAdapter = PhaseRecyclerAdapter(response.data!!.event!!.phases!!)
                    this@PhaseActivity.runOnUiThread(Runnable {
                        phaseRecyclerView.adapter=adapter
                        phaseRecyclerView.layoutManager = LinearLayoutManager(this@PhaseActivity)
                    })

                }

            })
    }
}