package com.jger.BracketVisualizer.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jger.BracketVisualizer.Fragment.BracketsColomnFragment
import com.jger.BracketVisualizer.model.ColomnData
import com.jger.BracketVisualizer.model.CompetitorData
import com.jger.BracketVisualizer.model.MatchData
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Emil on 21/10/17.
 */
class BracketsSectionAdapter(
    fm: FragmentManager,
    var sectionList: ArrayList<ColomnData>,
    val isLooser : Boolean
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        sectionList = formatFirstRound(sectionList)
        bundle.putSerializable("colomn_data", sectionList[position])
        val fragment = BracketsColomnFragment(isLooser,this)
        bundle.putInt("section_number", position)
        if (position > 0) bundle.putInt(
            "previous_section_size",
            sectionList[position - 1].getMatches().size
        ) else if (position == 0) bundle.putInt(
            "previous_section_size",
            sectionList[position].getMatches().size
        )
        if(position>0){
            bundle.putSerializable("lastColomnData", sectionList[position-1])
        }
        if(sectionList.size-1 == position){
            bundle.putBoolean("isLastSection",true)
        }
        if(sectionList.size-2 == position && !isLooser){
            bundle.putBoolean("isWinnerFinnal",true)
        }
        fragment.setArguments(bundle)
        return fragment
    }

    private fun formatFirstRound(sectionList: ArrayList<ColomnData>): ArrayList<ColomnData> {
        val colomnData = null
        val newMatchList = ArrayList<MatchData>()
        if(sectionList[0].matches.size<sectionList[1].matches.size) {

            for( i in 1..sectionList[1].matches.size){
                newMatchList.add(MatchData(CompetitorData("","",false),
                    CompetitorData("VIDE","",false),131,0,"A",false))
            }
            for (match in sectionList[0].matches) {
                for (nextMatch in sectionList[1].matches) {
                    if (match.competitorOne.name == nextMatch.competitorOne.name || match.competitorOne.name == nextMatch.competitorTwo.name || match.competitorTwo.name == nextMatch.competitorOne.name || match.competitorTwo.name == nextMatch.competitorTwo.name) {
                        newMatchList[sectionList[1].matches.indexOf(nextMatch)] = match
                    }
                }
            }

            sectionList[0].matches=newMatchList
        }

        return sectionList
    }

    override fun getCount(): Int {
        return sectionList.size
    }


}