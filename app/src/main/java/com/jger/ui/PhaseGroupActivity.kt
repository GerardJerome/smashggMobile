package com.jger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.PhaseGroupByPhaseIdQuery
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jger.R
import com.jger.ui.adapter.PhaseGroupRecyclerAdapter
import com.jger.util.ApolloUtil
import kotlinx.android.synthetic.main.activity_phase_group.*

class PhaseGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase_group)
        supportActionBar!!.title = intent.getStringExtra("phaseGroupName");

        ApolloUtil.apolloClient
            .query(PhaseGroupByPhaseIdQuery(Input.fromNullable(intent.getStringExtra("phaseId")))).requestHeaders(
                ApolloUtil.clientHeader)
            .enqueue(object : ApolloCall.Callback<PhaseGroupByPhaseIdQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    FirebaseCrashlytics.getInstance()
                        .log("Problème dans la requète de recherche de phase group : ${e.message}")
                }

                override fun onResponse(response: Response<PhaseGroupByPhaseIdQuery.Data>) {
                    val adapter = PhaseGroupRecyclerAdapter(response.data!!.phase!!.phaseGroups!!.nodes)
                    this@PhaseGroupActivity.runOnUiThread {
                        phaseGroupRecyclerView.adapter=adapter
                        phaseGroupRecyclerView.layoutManager=LinearLayoutManager(this@PhaseGroupActivity)
                    }
                }

            })
    }
}