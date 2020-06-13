package com.jger.BracketVisualizer.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jger.BracketVisualizer.adapter.BracketsCellAdapter
import com.jger.BracketVisualizer.model.ColomnData
import com.jger.BracketVisualizer.model.CompetitorData
import com.jger.BracketVisualizer.model.MatchData
import com.jger.BracketVisualizer.utility.BracketsUtility
import com.jger.R
import com.jger.transferClass.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Emil on 21/10/17.
 */
class BracketsColomnFragment : Fragment() {
    private var colomnData: ColomnData? = null
    var sectionNumber = 0
        private set
    var previousBracketSize = 0
        private set
    private var list: ArrayList<MatchData> = ArrayList()
    private var bracketsRV: RecyclerView? = null
    private var adapter: BracketsCellAdapter? = null


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_brackets_colomn, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        extras
        initAdapter()
    }



    private fun initViews() {
        bracketsRV = getView()!!.findViewById(R.id.rv_score_board) as RecyclerView
    }

    val colomnList: ArrayList<MatchData>
        get() = list

    private val extras: Unit
        private get() {
            if (getArguments() != null) {
                list = ArrayList<MatchData>()
                colomnData = getArguments()!!.getSerializable("colomn_data") as ColomnData
                sectionNumber = getArguments()!!.getInt("section_number")
                previousBracketSize = getArguments()!!.getInt("previous_section_size")
                list!!.addAll(colomnData!!.getMatches())
                setInitialHeightForList()
            }
        }

    private fun setInitialHeightForList() {
        for (data in list!!) {
            val newPlayer = Test.listParticipant.size>0 && !Test.listParticipant.contains(data.competitorOne.name) || !Test.listParticipant.contains(data.competitorTwo.name)
            if(newPlayer){
               data.height=(BracketsUtility.dpToPx(131))
                data.originalHeight=(BracketsUtility.dpToPx(131))
            }else if (sectionNumber == 0) {
                data.setHeight(BracketsUtility.dpToPx(131))
                data.originalHeight=(BracketsUtility.dpToPx(131))
            } else if (sectionNumber == 1 && previousBracketSize != list!!.size && !newPlayer) {
                data.setHeight(BracketsUtility.dpToPx(262))
                data.originalHeight=(BracketsUtility.dpToPx(262))
            } else if (sectionNumber == 1 && previousBracketSize == list!!.size && !newPlayer) {
                data.setHeight(BracketsUtility.dpToPx(131))
                data.originalHeight=BracketsUtility.dpToPx(131)
            } else if (previousBracketSize > list!!.size && !newPlayer) {
                data.setHeight(BracketsUtility.dpToPx(262))
                data.originalHeight=BracketsUtility.dpToPx(262)
            } else if (previousBracketSize == list!!.size && !newPlayer) {
                data.setHeight(BracketsUtility.dpToPx(131))
                data.originalHeight=BracketsUtility.dpToPx(131)
            }
            if(!Test.listParticipant.contains(data.competitorTwo.name)){
                Test.listParticipant.add(data.competitorTwo.name)
            }
            if(!Test.listParticipant.contains(data.competitorOne.name)){
                Test.listParticipant.add(data.competitorOne.name)
            }
        }
    }

    fun expandHeight(height: Int) {
        for (data in list!!) {
            data.setHeight(height)
        }
        adapter!!.setList(list)
    }

    fun shrinkView(height: Int) {
        for (data in list!!) {
            data.setHeight(height)
        }
        adapter!!.setList(list)
    }

    private fun initAdapter() {

//        pBar.setVisibility(View.GONE);
        adapter = BracketsCellAdapter(this, getContext()!!, list)
        if (bracketsRV != null) {
            bracketsRV!!.setHasFixedSize(true)
            bracketsRV!!.setNestedScrollingEnabled(false)
            bracketsRV!!.setAdapter(adapter)
            bracketsRV!!.smoothScrollToPosition(0)
            val layoutManager = LinearLayoutManager(getActivity())
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
            bracketsRV!!.setLayoutManager(layoutManager)
            bracketsRV!!.setItemAnimator(DefaultItemAnimator())
        }
    }

    fun resetView() {
        for(data in list){
            data.height=data.originalHeight
        }
        adapter!!.setList(list)
    }

    val currentBracketSize: Int
        get() = list!!.size

}