package com.jger.BracketVisualizer.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.MatchByPhaseGroupIdQuery
import com.example.SetResultForDQQuery
import com.jger.BracketVisualizer.adapter.BracketsSectionAdapter
import com.jger.BracketVisualizer.customviews.WrapContentHeightViewPager
import com.jger.BracketVisualizer.model.ColomnData
import com.jger.BracketVisualizer.model.CompetitorData
import com.jger.BracketVisualizer.model.MatchData
import com.jger.BracketVisualizer.utility.BracketsUtility
import com.jger.R
import com.jger.transferClass.Test
import com.jger.util.ApolloUtil
import kotlinx.android.synthetic.main.fragment_brackts.*
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Emil on 21/10/17.
 */
class BracketsFragment(var sortedMatchByRound: SortedMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>) :
    Fragment(), ViewPager.OnPageChangeListener {
    private var viewPager: WrapContentHeightViewPager? = null
    private var sectionAdapter: BracketsSectionAdapter? = null
    private var sectionList: ArrayList<ColomnData>? = null
    private var mNextSelectedScreen = 0
    private val mCurrentPagerState = 0
    private val sortedMatchByRoundWinnerBracket =
        HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>()
    private val sortedMatchByRoundLoserBracket =
        HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>()
    private var wait = false
    private var listIdTreated = ArrayList<String>()

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        sortedMatchByRound.onEach { entry ->
            entry.value.sortedBy { node -> node!!.identifier }
            if (entry.key!! > 0) {
                sortedMatchByRoundWinnerBracket[entry.key] = entry.value
            } else {
                sortedMatchByRoundLoserBracket[entry.key] = entry.value
            }

        }


        val view = inflater.inflate(R.layout.fragment_brackts, container, false)
        viewPager =
            containeurs


        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager = containeurs
        initViews()
        setData()
        intialiseViewPagerAdapter()
    }


    private fun setData() {
        sectionList = ArrayList<ColomnData>()
        sortedMatchByRoundWinnerBracket.keys.forEach(Consumer {
            var matchByPhase = ArrayList<MatchData>()
            sortedMatchByRoundWinnerBracket[it]!!.forEach { node: MatchByPhaseGroupIdQuery.Node? ->
                if (node!!.displayScore!!.compareTo("DQ") == 0) {

                    wait=true
                    ApolloUtil.apolloClient
                        .query(SetResultForDQQuery(node!!.id!!))
                        .requestHeaders(
                            ApolloUtil.clientHeader
                        ).enqueue(object : ApolloCall.Callback<SetResultForDQQuery.Data>() {
                            override fun onFailure(e: ApolloException) {
                                TODO("Not yet implemented")
                            }

                            override fun onResponse(response: Response<SetResultForDQQuery.Data>) {
                                if (response.data!!.set!!.slots!![0]!!.standing!!.stats!!.score!!.value == ((-1).toDouble())) {
                                    var matchData = MatchData(
                                        CompetitorData(
                                            response.data!!.set!!.slots!![0]!!.standing!!.entrant!!.name,
                                            "DQ"
                                        ),
                                        CompetitorData(
                                            response.data!!.set!!.slots!![1]!!.standing!!.entrant!!.name,
                                            "Win"
                                        ),
                                        response.data!!.set!!.identifier


                                    )
                                    listIdTreated.add(node!!.id!!)
                                    matchByPhase.add(matchData)
                                } else if (response.data!!.set!!.slots!![1]!!.standing!!.stats!!.score!!.value == ((-1).toDouble())) {
                                    var matchData = MatchData(
                                        CompetitorData(
                                            response.data!!.set!!.slots!![1]!!.standing!!.entrant!!.name,
                                            "DQ"
                                        ),
                                        CompetitorData(
                                            response.data!!.set!!.slots!![0]!!.standing!!.entrant!!.name,
                                            "Win"
                                        ),
                                        response.data!!.set!!.identifier
                                    )
                                    listIdTreated.add(node!!.id!!)
                                    matchByPhase.add(matchData)
                                }
                                wait=false
                            }

                        })
                } else {
                    val matchScoreArray = node!!.displayScore!!.split("-")
                    if (matchScoreArray.size > 1) {
                        val regex =
                            "([[:ascii:]|\\p{L}]+)(\\d)+?\\s-\\s([[:ascii:]|\\p{L}]+)(\\d)".toRegex()
                        val test = regex.find(node!!.displayScore!!, 0)
                        val groupValue = test!!.groupValues
                        var matchData = MatchData(
                            CompetitorData(
                                groupValue[1].trim(),
                                groupValue[2].trim()
                            ),
                            CompetitorData(
                                groupValue[3].trim(),
                                groupValue[4].trim()
                            ),
                            node!!.identifier
                        )
                        listIdTreated.add(node!!.id!!)
                        matchByPhase.add(matchData)
                    }
                }
            }
            while (listIdTreated.size!= sortedMatchByRoundWinnerBracket[it]!!.size){
                Thread.sleep(100)
            }
            val matchTrie = (matchByPhase.sortedWith(compareBy( {it.identifier.length},{it.identifier})))
            sectionList!!.add(ColomnData(ArrayList(matchTrie)))
            listIdTreated.clear()
        })
    }

    private fun intialiseViewPagerAdapter() {
        Test.listParticipant.clear()
        sectionAdapter = BracketsSectionAdapter(childFragmentManager, sectionList!!)
        viewPager!!.offscreenPageLimit = 10
        viewPager!!.adapter = sectionAdapter
        viewPager!!.currentItem = 0
        viewPager!!.pageMargin = -200
        viewPager!!.isHorizontalFadingEdgeEnabled = true
        viewPager!!.setFadingEdgeLength(50)
        viewPager!!.addOnPageChangeListener(this)
    }

    private fun initViews() {
        //itojzej
    }


    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        if (mCurrentPagerState != ViewPager.SCROLL_STATE_SETTLING) {
            // We are moving to next screen on right side
            if (positionOffset > 0.5) {
                // Closer to next screen than to current
                if (position + 1 != mNextSelectedScreen) {
                    mNextSelectedScreen = position + 1
                    //update view here
                    if (getBracketsFragment(position)!!.colomnList[0].height !== BracketsUtility.dpToPx(
                            131
                        )
                    ) {
                        getBracketsFragment(position)!!.shrinkView(BracketsUtility.dpToPx(131))
                    }
                    if (getBracketsFragment(position + 1)!!.colomnList[0].height
                        !== BracketsUtility.dpToPx(131)
                    ) {
                        getBracketsFragment(position + 1)!!.shrinkView(BracketsUtility.dpToPx(131))
                    }
                }
            } else {
                // Closer to current screen than to next
                if (position != mNextSelectedScreen) {
                    mNextSelectedScreen = position
                    //updateViewhere
                    if (getBracketsFragment(position + 1)!!.currentBracketSize ==
                        getBracketsFragment(position + 1)!!.previousBracketSize
                    ) {
                        getBracketsFragment(position + 1)!!.shrinkView(BracketsUtility.dpToPx(131))
                        getBracketsFragment(position)!!.shrinkView(BracketsUtility.dpToPx(131))
                    } else {
                        val currentFragmentSize =
                            getBracketsFragment(position + 1)!!.currentBracketSize
                        val previousFragmentSize =
                            getBracketsFragment(position + 1)!!.previousBracketSize
                        if (currentFragmentSize != previousFragmentSize) {
                            getBracketsFragment(position + 1)!!.resetView()
                            getBracketsFragment(position)!!.shrinkView(BracketsUtility.dpToPx(131))
                        }
                    }
                }
            }
        } else {
            // We are moving to next screen left side
            if (positionOffset > 0.5) {
                // Closer to current screen than to next
                if (position + 1 != mNextSelectedScreen) {
                    mNextSelectedScreen = position + 1
                    //update view for screen
                }
            } else {
                // Closer to next screen than to current
                if (position != mNextSelectedScreen) {
                    mNextSelectedScreen = position
                    //updateviewfor screem
                }
            }
        }
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    fun getBracketsFragment(position: Int): BracketsColomnFragment? {
        var bracktsFrgmnt: BracketsColomnFragment? = null
        val fragments: List<Fragment> =
            childFragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment is BracketsColomnFragment) {
                    bracktsFrgmnt = fragment
                    if (bracktsFrgmnt.sectionNumber == position) break
                }
            }
        }
        return bracktsFrgmnt
    }
}