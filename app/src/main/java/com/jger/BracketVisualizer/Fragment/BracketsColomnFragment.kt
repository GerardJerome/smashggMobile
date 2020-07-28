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
import com.jger.BracketVisualizer.adapter.BracketsSectionAdapter
import com.jger.BracketVisualizer.model.ColomnData
import com.jger.BracketVisualizer.model.MatchData
import com.jger.BracketVisualizer.utility.BracketsUtility
import com.jger.R
import com.jger.transferClass.Test
import kotlin.collections.ArrayList

/**
 * Created by Emil on 21/10/17.
 */
class BracketsColomnFragment(
    val isLooser: Boolean,
    val bracketsSectionAdapter: BracketsSectionAdapter
) : Fragment() {
    private var colomnData: ColomnData? = null
    var sectionNumber = 0
        private set
    var previousBracketSize = 0
        private set
    private var list: ArrayList<MatchData> = ArrayList()
    var bracketsRV: RecyclerView? = null
    private var adapter: BracketsCellAdapter? = null
    private var isLastSection =false
    private var isWinnerFinalSection = false
    private var lastList : ArrayList<MatchData> = ArrayList()
    private var lastColomnData : ColomnData? = null


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
                lastColomnData = arguments!!.getSerializable("lastColomnData") as ColomnData?
                colomnData = getArguments()!!.getSerializable("colomn_data") as ColomnData
                sectionNumber = getArguments()!!.getInt("section_number")
                previousBracketSize = getArguments()!!.getInt("previous_section_size")
                isLastSection = arguments!!.getBoolean("isLastSection")
                isWinnerFinalSection = arguments!!.getBoolean("isWinnerFinnal")
                if(lastColomnData!=null) {
                    lastList.addAll(lastColomnData!!.matches)
                }
                list!!.addAll(colomnData!!.getMatches())
                setInitialHeightForList()
            }
        }

    private fun setInitialHeightForList() {
        var indexToRefer = 0
        for (data in list!!) {
            val playerFromLastMatch  = sectionNumber!=0 && lastList.size>list.indexOf(data) &&   (data.competitorOne.name.compareTo(lastList[list.indexOf(data)].competitorOne.name)==0
                    || data.competitorOne.name.compareTo(lastList[list.indexOf(data)].competitorTwo.name)==0
                    || data.competitorTwo.name.compareTo(lastList[list.indexOf(data)].competitorOne.name)==0
                    || data.competitorTwo.name.compareTo(lastList[list.indexOf(data)].competitorTwo.name)==0)
            val newPlayer = Test.listParticipant.size>0
                    && (!Test.listParticipant.contains(data.competitorOne.name) && data.competitorOne.name.compareTo("TBD")!=0)
                    || (!Test.listParticipant.contains(data.competitorTwo.name) && data.competitorTwo.name.compareTo("TBD")!=0)
                    || isLooser && (!data.competitorOne.isFromLooser || !data.competitorTwo.isFromLooser)


            if(sectionNumber==0 && data.visible){
                    data.height = BracketsUtility.dpToPx(131)
            }else if(sectionNumber==0 && !data.visible){
                data.height=BracketsUtility.dpToPx(131)
            } else if(sectionNumber!=0 && !newPlayer  ){
                if(indexToRefer >= lastList.size || lastList[indexToRefer].height==0){
                    data.height = BracketsUtility.dpToPx(131)
                }else  {
                    data.height = lastList[indexToRefer].height+BracketsUtility.dpToPx((sectionNumber-1)*131)
                }
                indexToRefer+=2
            }else if((newPlayer) && sectionNumber!=0){
                if(indexToRefer>= lastList.size){
                    data.height = BracketsUtility.dpToPx(131)
                }else {
                    data.height = lastList[indexToRefer].height

                    //data.height = BracketsUtility.dpToPx(lastList[indexToRefer].height)
                }
                indexToRefer++
            }
            colomnData?.matches=list
            bracketsSectionAdapter.sectionList[bracketsSectionAdapter.sectionList.indexOf(
                lastColomnData
            ) + 1] = colomnData!!


            /////////////////////////////////////////////////////////////////////////////////////////
            /*if(newPlayer || previousBracketSize< list.size || isLastSection && sectionNumber!=0){
               data.height=(BracketsUtility.dpToPx((sectionNumber) * 131))
            }else if (sectionNumber == 0) {
                data.setHeight(BracketsUtility.dpToPx((sectionNumber+1) *131))
            } else if (sectionNumber == 1 && previousBracketSize != list!!.size && !newPlayer) {
                data.setHeight(BracketsUtility.dpToPx(((sectionNumber+1) *131)))
            } else if (sectionNumber == 1 && previousBracketSize == list!!.size && !newPlayer) {
                data.setHeight(BracketsUtility.dpToPx((sectionNumber+1) *131))
            } else if (previousBracketSize > list!!.size && !newPlayer) {
                data.height = BracketsUtility.dpToPx(((sectionNumber+1) *131))
            } else if (previousBracketSize == list!!.size && !newPlayer) {
                data.originalHeight = BracketsUtility.dpToPx((sectionNumber + 1) * 131)
            }*/

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