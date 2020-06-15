package com.jger.BracketVisualizer.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.example.MatchByPhaseGroupIdQuery
import com.example.SetResultForDQQuery
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jger.BracketVisualizer.adapter.BracketsSectionAdapter
import com.jger.BracketVisualizer.customviews.WrapContentHeightViewPager
import com.jger.BracketVisualizer.model.ColomnData
import com.jger.BracketVisualizer.model.CompetitorData
import com.jger.BracketVisualizer.model.MatchData
import com.jger.BracketVisualizer.utility.BracketsUtility
import com.jger.R
import com.jger.transferClass.Test
import com.jger.ui.BracketViewerActivity
import com.jger.ui.MainActivity
import com.jger.util.ApolloUtil
import com.jger.util.RequestCountUtil
import kotlinx.android.synthetic.main.activity_bracket_viewer.*
import kotlinx.android.synthetic.main.fragment_brackts.*
import java.util.function.Consumer
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Emil on 21/10/17.
 */
class BracketsFragment(var sortedMatchByRound: HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>) :
    Fragment(), ViewPager.OnPageChangeListener {
    private var viewPager: WrapContentHeightViewPager? = null
    private var sectionAdapter: BracketsSectionAdapter? = null
    private var sectionList: ArrayList<ColomnData>? = null
    private var mNextSelectedScreen = 0
    private val mCurrentPagerState = 0
    private var wait = false
    private var listIdTreated = ArrayList<String>()

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
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
        sortedMatchByRound.keys.forEach(Consumer {
            var matchByPhase = ArrayList<MatchData>()
            sortedMatchByRound[it]!!.forEach { node: MatchByPhaseGroupIdQuery.Node? ->
                        var matchData = MatchData(
                            CompetitorData(
                                node!!.slots!![0]!!.standing!!.entrant!!.name,
                                node!!.slots!![0]!!.standing!!.stats!!.score!!.value!!.toInt().toString().replace("-1.0","DQ")
                            ),
                            CompetitorData(
                                node!!.slots!![1]!!.standing!!.entrant!!.name,
                                node!!.slots!![1]!!.standing!!.stats!!.score!!.value!!.toInt().toString().replace("-1.0","DQ")
                            ),
                            node!!.identifier
                        )
                        listIdTreated.add(node!!.id!!)
                        matchByPhase.add(matchData)


            }
            val matchTrie =
                (matchByPhase.sortedWith(compareBy({ it.identifier.length }, { it.identifier })))
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