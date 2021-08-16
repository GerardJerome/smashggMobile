package com.jger.BracketVisualizer.Fragment

import android.os.Bundle
import android.util.Log
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
        for (data in list!!){
            data.height = BracketsUtility.dpToPx(120);
        }
        /*var indexToRefer = 0
        var dataIndex=0
        for (data in list!!) {
            dataIndex++
            val newPlayer = Test.listParticipant.size>0
                    && (!Test.listParticipant.contains(data.competitorOne.name) && data.competitorOne.name.compareTo("TBD")!=0)
                    || (!Test.listParticipant.contains(data.competitorTwo.name) && data.competitorTwo.name.compareTo("TBD")!=0)
                    || isLooser && (!data.competitorOne.isFromLooser || !data.competitorTwo.isFromLooser)

            //si première colonne
            if(sectionNumber==0 && data.visible){
                    Log.d("JGERARD","--------------------------VISIBLE---------------")
                    Log.d("JGERARD",data.identifier)
                    Log.d("JGERARD",dataIndex.toString())
                    Log.d("JGERARD", (dataIndex*131).toString())
                    Log.d("JGERARD",(dataIndex*BracketsUtility.dpToPx(131)).toString())
                    data.height = dataIndex*BracketsUtility.dpToPx(131)
                Log.d("JGERARD","--------------------------FIN VISIBLE---------------")
            }else if(sectionNumber==0 && !data.visible){
                Log.d("JGERARD","--------------------------PASVISIBLE---------------")
                Log.d("JGERARD",data.identifier)
                Log.d("JGERARD",dataIndex.toString())
                Log.d("JGERARD", (dataIndex*131).toString())
                Log.d("JGERARD",(dataIndex*BracketsUtility.dpToPx(131)).toString())
                data.height=dataIndex*BracketsUtility.dpToPx(131)
                Log.d("JGERARD","--------------------------FIN PASVISIBLE---------------")
                //si pas première colonne et les 2 joueurs ont déjà joué un match et que ce n'est pas le reset GF
            } else if(sectionNumber!=0 && !newPlayer && !isLastSection ){
                if(indexToRefer >= lastList.size || lastList[indexToRefer].height==0){
                    data.height = dataIndex*BracketsUtility.dpToPx(131)
                }else  {
                    if (list.size==1 && sectionNumber!=0){
                        data.height= (lastList[indexToRefer].height+lastList[indexToRefer+1].height)%2
                    }else {
                        data.height =
                            (lastList[indexToRefer].height +BracketsUtility.dpToPx(131))
                    }
                }
                indexToRefer+=2
            }else if((newPlayer) && sectionNumber!=0 && !isLastSection){
                if(indexToRefer>= lastList.size){
                    data.height = dataIndex*BracketsUtility.dpToPx(131)
                }else {
                    data.height = lastList[indexToRefer].height

                    //data.height = BracketsUtility.dpToPx(lastList[indexToRefer].height)
                }
                indexToRefer++
            }else if(isLastSection){
                data.height = lastList[indexToRefer].height
            }
            colomnData?.matches=list
            bracketsSectionAdapter.sectionList[bracketsSectionAdapter.sectionList.indexOf(
                lastColomnData
            ) + 1] = colomnData!!


            if(!Test.listParticipant.contains(data.competitorTwo.name)){
                Test.listParticipant.add(data.competitorTwo.name)
            }
            if(!Test.listParticipant.contains(data.competitorOne.name)){
                Test.listParticipant.add(data.competitorOne.name)
            }


        }*/

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
