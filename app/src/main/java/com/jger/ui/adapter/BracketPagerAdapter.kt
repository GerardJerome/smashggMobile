package com.jger.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.MatchByPhaseGroupIdQuery
import com.jger.BracketVisualizer.Fragment.BracketsFragment

class BracketPagerAdapter(
    fm: FragmentManager,
    val sortedMatchByRoundWinnerBracket: HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>,
    val sortedMatchByRoundLoserBracket: HashMap<Int?, List<MatchByPhaseGroupIdQuery.Node?>>
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
       when(position){
            0 -> return BracketsFragment(sortedMatchByRoundWinnerBracket,false,this)
            1 -> return BracketsFragment(sortedMatchByRoundLoserBracket,true,this)
        }
        return BracketsFragment(sortedMatchByRoundLoserBracket,true,this)
    }

    override fun getCount(): Int {
        if(sortedMatchByRoundLoserBracket.size>1){
            return 2
        }
        return 1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return if(count==1){
                "Bracket"
            } else{
                "Winner Bracket"
            }
            1 -> return "Loser Bracket"
        }
        return null
    }
}