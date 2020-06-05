package com.jger.BracketVisualizer.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.TournamentQuery
import com.jger.BracketVisualizer.adapter.BracketsSectionAdapter
import com.jger.BracketVisualizer.customviews.WrapContentHeightViewPager
import com.jger.BracketVisualizer.model.ColomnData
import com.jger.BracketVisualizer.model.CompetitorData
import com.jger.BracketVisualizer.model.MatchData
import com.jger.BracketVisualizer.utility.BracketsUtility
import com.jger.R
import kotlinx.android.synthetic.main.fragment_brackts.*
import java.util.*

/**
 * Created by Emil on 21/10/17.
 */
class BracketsFragment(sets: TournamentQuery.Sets) : Fragment(), ViewPager.OnPageChangeListener {
    private var viewPager: WrapContentHeightViewPager? = null
    private var sectionAdapter: BracketsSectionAdapter? = null
    private var sectionList: ArrayList<ColomnData>? = null
    private var mNextSelectedScreen = 0
    private val mCurrentPagerState = 0

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
        viewPager=containeurs
        initViews()
        setData()
        intialiseViewPagerAdapter()
    }



    private fun setData() {
        val eventId = activity!!.intent.getIntExtra("EventId",0)

        sectionList = ArrayList<ColomnData>()
        val Colomn1matchesList: ArrayList<MatchData> = ArrayList<MatchData>()
        val colomn2MatchesList: ArrayList<MatchData> = ArrayList<MatchData>()
        val colomn3MatchesList: ArrayList<MatchData> = ArrayList<MatchData>()
        val competitorOne = CompetitorData("Manchester United Fc", "2")
        val competitorTwo = CompetitorData("Arsenal", "1")
        val competitorThree = CompetitorData("Chelsea", "2")
        val competitorFour = CompetitorData("Tottenham", "1")
        val competitorFive = CompetitorData("Manchester FC", "2")
        val competitorSix = CompetitorData("Liverpool", "4")
        val competitorSeven = CompetitorData("West ham ", "2")
        val competitorEight = CompetitorData("Bayern munich", "1")
        val matchData1 = MatchData(competitorOne, competitorTwo)
        val matchData2 = MatchData(competitorThree, competitorFour)
        val matchData3 = MatchData(competitorFive, competitorSix)
        val matchData4 = MatchData(competitorSeven, competitorEight)
        Colomn1matchesList.add(matchData1)
        Colomn1matchesList.add(matchData2)
        Colomn1matchesList.add(matchData3)
        Colomn1matchesList.add(matchData4)
        val colomnData1 = ColomnData(Colomn1matchesList)
        sectionList!!.add(colomnData1)
        val competitorNine = CompetitorData("Manchester United Fc", "2")
        val competitorTen = CompetitorData("Chelsea", "4")
        val competitorEleven = CompetitorData("Liverpool", "2")
        val competitorTwelve = CompetitorData("westham", "1")
        val matchData5 = MatchData(competitorNine, competitorTen)
        val matchData6 = MatchData(competitorEleven, competitorTwelve)
        colomn2MatchesList.add(matchData5)
        colomn2MatchesList.add(matchData6)
        val colomnData2 = ColomnData(colomn2MatchesList)
        sectionList!!.add(colomnData2)
        val competitorThirteen = CompetitorData("Chelsea", "2")
        val competitorForteen = CompetitorData("Liverpool", "1")
        val matchData7 = MatchData(competitorThirteen, competitorForteen)
        colomn3MatchesList.add(matchData7)
        val colomnData3 = ColomnData(colomn3MatchesList)
        sectionList!!.add(colomnData3)
    }

    private fun intialiseViewPagerAdapter() {
        sectionAdapter = BracketsSectionAdapter(getChildFragmentManager(), sectionList!!)
        viewPager!!.setOffscreenPageLimit(10)
        viewPager!!.setAdapter(sectionAdapter)
        viewPager!!.setCurrentItem(0)
        viewPager!!.setPageMargin(-200)
        viewPager!!.setHorizontalFadingEdgeEnabled(true)
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
                    if (getBracketsFragment(position)!!.colomnList[0].height
                        !== BracketsUtility.dpToPx(131)
                    ) getBracketsFragment(position)!!.shrinkView(BracketsUtility.dpToPx(131))
                    if (getBracketsFragment(position + 1)!!.colomnList[0].height
                        !== BracketsUtility.dpToPx(131)
                    ) getBracketsFragment(position + 1)!!.shrinkView(BracketsUtility.dpToPx(131))
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
                            getBracketsFragment(position + 1)!!.expandHeight(
                                BracketsUtility.dpToPx(
                                    262
                                )
                            )
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