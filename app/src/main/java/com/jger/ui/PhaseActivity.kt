package com.jger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.example.EventQueryForPhaseIdQuery
import com.example.TempQuery
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jger.R
import com.jger.ui.adapter.PhaseRecyclerAdapter
import com.jger.util.ApolloUtil
import kotlinx.android.synthetic.main.activity_phase.*

class PhaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase)
        FirebaseApp.initializeApp(this)
        ApolloUtil.apolloClient
            .query(EventQueryForPhaseIdQuery(intent.getStringExtra("eventSlug"))).requestHeaders(ApolloUtil.clientHeader)
            .enqueue(object : ApolloCall.Callback<EventQueryForPhaseIdQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    if ((e as ApolloHttpException).code() == 429) {
                        this@PhaseActivity.runOnUiThread {
                            Toast.makeText(
                                this@PhaseActivity.applicationContext,
                                "Cotât de requête dépassé veuillez réessayer ultérieurement",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        this@PhaseActivity.finish()
                    }
                    FirebaseApp.initializeApp(this@PhaseActivity)
                    FirebaseCrashlytics.getInstance()
                        .log("Problème dans la requète de recherche de match : ${e.message}")
                }

                override fun onResponse(response: Response<EventQueryForPhaseIdQuery.Data>) {
                    if (response.data!!.event!!.phases != null) {
                        val adapter: PhaseRecyclerAdapter =
                            PhaseRecyclerAdapter(response.data!!.event!!.phases!!)
                        this@PhaseActivity.runOnUiThread(Runnable {
                            phaseRecyclerView.adapter = adapter
                            phaseRecyclerView.layoutManager =
                                LinearLayoutManager(this@PhaseActivity)
                        })

                    }else{
                        this@PhaseActivity.runOnUiThread{
                            Toast.makeText(this@PhaseActivity,"Aucune phase dans cet Event",Toast.LENGTH_LONG).show()
                            this@PhaseActivity.finish()
                        }
                    }
                }
            })
    }
}