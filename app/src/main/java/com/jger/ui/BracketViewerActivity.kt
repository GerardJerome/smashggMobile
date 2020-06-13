package com.jger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.MatchByPhaseGroupIdQuery
import com.jger.BracketVisualizer.Fragment.BracketsFragment
import com.jger.R
import com.jger.util.ApolloUtil

class BracketViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bracket_viewer)
        initialiseBracketsFragment()

    }

    private var bracketFragment: BracketsFragment? = null


    private fun initialiseBracketsFragment() {
        ApolloUtil.apolloClient
            .query(MatchByPhaseGroupIdQuery(Input.fromNullable(intent.getStringExtra("phaseGroupId"))))
            .requestHeaders(
                ApolloUtil.clientHeader
            )
            .enqueue(object : ApolloCall.Callback<MatchByPhaseGroupIdQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(response: Response<MatchByPhaseGroupIdQuery.Data>) {
                    val listSet = response.data!!.phaseGroup!!.sets!!.nodes
                    val matchByRound =
                        listSet!!.groupBy { node: MatchByPhaseGroupIdQuery.Node? -> node!!.round } as HashMap
                    matchByRound.forEach { entry ->
                        val listtrie = entry.value.sortedWith(compareBy({it!!.identifier!!.length},{it!!.identifier}))
                        matchByRound[entry.key] = listtrie
                    }
                    val sortedMatchByRound = matchByRound.toSortedMap(compareBy { it })
                    this@BracketViewerActivity.runOnUiThread {
                        bracketFragment = BracketsFragment(sortedMatchByRound)
                        val manager: FragmentManager = supportFragmentManager
                        val transaction: FragmentTransaction = manager.beginTransaction()
                        transaction.replace(
                            R.id.containeur,
                            bracketFragment!!,
                            "brackets_home_fragment"
                        )
                        transaction.commit()
                        manager.executePendingTransactions()
                    }

                }

            })


    }
}