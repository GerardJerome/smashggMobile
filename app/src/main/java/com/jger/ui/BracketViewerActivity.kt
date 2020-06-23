package com.jger.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.MatchByPhaseGroupIdQuery
import com.jger.BracketVisualizer.Fragment.BracketsFragment
import com.jger.R
import com.jger.ui.adapter.BracketPagerAdapter
import com.jger.util.ApolloUtil
import com.jger.util.RequestCountUtil
import kotlinx.android.synthetic.main.activity_bracket_viewer.*

class BracketViewerActivity(var requestEnd:Boolean) : AppCompatActivity() {
    init {
        requestEnd=false
    }

    constructor() : this(false) {

    }
    lateinit var chargementTextview: TextView
    var page = 1
    var perPage = 55
    var listMatch = ArrayList<MatchByPhaseGroupIdQuery.Node?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestEnd=false
        setContentView(R.layout.activity_bracket_viewer)
        initialiseBracketsFragment()
        chargementTextview = chargement_textview
    }

    lateinit var viewPagerAdapter: BracketPagerAdapter
    private val sortedMatchByRoundWinnerBracket =
        HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>()
    private val sortedMatchByRoundLoserBracket =
        HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>()


    private fun initialiseBracketsFragment() {
        chargement_textview.visibility = View.VISIBLE

        while (!requestEnd) {
            Thread.sleep(500)
            requestMatch()
        }
        val matchByRound =
            listMatch!!.groupBy { node: MatchByPhaseGroupIdQuery.Node? -> node!!.round } as HashMap
        matchByRound.forEach { entry ->
            val listtrie =
                entry.value.sortedWith(compareBy({ it!!.identifier!!.length }, { it!!.identifier }))
            matchByRound[entry.key] = listtrie
        }
        val sortedMatchByRound = matchByRound.toSortedMap(compareBy { it })
        this@BracketViewerActivity.runOnUiThread {
            sortedMatchByRound.onEach { entry ->
                entry.value.sortedBy { node -> node!!.identifier }
                if (entry.key!! > 0) {
                    sortedMatchByRoundWinnerBracket[entry.key] = entry.value
                } else {
                    sortedMatchByRoundLoserBracket[entry.key] = entry.value
                }

            }
            viewPagerAdapter = BracketPagerAdapter(
                supportFragmentManager,
                sortedMatchByRoundWinnerBracket,
                sortedMatchByRoundLoserBracket
            )
            view_pager.adapter = viewPagerAdapter
            tabs.setupWithViewPager(view_pager)
            this@BracketViewerActivity.findViewById<TextView>(R.id.chargement_textview).visibility =
                View.GONE
        }


    }

    private fun requestMatch() {
        if (!requestEnd) {
            ApolloUtil.apolloClient
                .query(
                    MatchByPhaseGroupIdQuery(
                        intent.getStringExtra("phaseGroupId"),
                        page,
                        perPage
                    )
                )
                .requestHeaders(
                    ApolloUtil.clientHeader
                )
                .enqueue(object : ApolloCall.Callback<MatchByPhaseGroupIdQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(response: Response<MatchByPhaseGroupIdQuery.Data>) {
                        if (response.data!!.phaseGroup!!.sets!!.pageInfo!!.page!! >= page) {
                            listMatch.addAll(response.data!!.phaseGroup!!.sets!!.nodes!!)
                            if (response!!.data!!.phaseGroup!!.sets!!.pageInfo!!.page == response!!.data!!.phaseGroup!!.sets!!.pageInfo!!.totalPages) {
                                requestEnd = true
                            } else {
                                page++
                            }
                        }else{
                            requestEnd=true
                        }
                    }
                })
        }
    }

}