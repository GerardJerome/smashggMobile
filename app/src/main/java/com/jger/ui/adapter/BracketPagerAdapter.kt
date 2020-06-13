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
            0 -> return BracketsFragment(sortedMatchByRoundWinnerBracket)
            1 -> return BracketsFragment(sortedMatchByRoundLoserBracket)
        }
        return BracketsFragment(sortedMatchByRoundLoserBracket)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Winner Bracket"
            1 -> return "Loser Bracket"
        }
        return null
    }
}