package com.example.overtone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.overtone.data.ChordGroup
import com.example.overtone.data.DifficultyLevel
import com.example.overtone.data.SingleChord
import com.example.overtone.recyclerview.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
/**notice the fragment import to have access to view components*/
import kotlinx.android.synthetic.main.fragment_group_details.*
import java.util.*

class ChordGroupDetailsFrag : Fragment() {
    private var sentChordGroup: ChordGroup? = null
    /** testing image list remove when ready */
    private var chordDiagramIds: MutableList<Int> = mutableListOf()
    private var adapter:ViewPagerAdapter? = null
    private var namesIds = mutableMapOf<String,Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()
        setUpViewPagerAdapter(chordDiagramIds)
        setUpTabLayout()
    }

    private fun retrievePassedFragmentData() {
        if (arguments != null) {
            val args = ChordGroupDetailsFragArgs.fromBundle(requireArguments())
            //receiving chord group data object that was clicked on
            sentChordGroup = args.chordGroupClickedOn
        }
    }

    private fun setChordDiagramIds() {
        chordDiagramIds = ArrayList()
        for(id in namesIds.values){
            chordDiagramIds.add(id)
        }
    }

    private fun setUpViewPagerAdapter(chordDiagramIds:MutableList<Int>) {
        adapter = ViewPagerAdapter(chordDiagramIds)
        viewPager.adapter = adapter
    }

    private fun setUpTabLayout(){
        //keeping material principles, let view be scrollable if there are 5+ components
        if(chordDiagramIds.size<5){
            tabLayout.tabMode = TabLayout.MODE_FIXED
        }
        var titles = namesIds.keys.toList()
        TabLayoutMediator(tabLayout,viewPager){tab, position ->
//            tab.text = "Tab ${position+1}"
            tab.text ="${titles[position]}"
        }.attach()
    }
    private fun mapNamesToIds(){
        for (singleChord in sentChordGroup?.chordList!!){
            //short hand for namesIds.put(singleChord.chordName,singleChord.imageID)
            namesIds[singleChord.chordName] = singleChord.imageID
        }
    }
    private fun initializeData() {
        retrievePassedFragmentData()
        mapNamesToIds()
        setChordDiagramIds()
    }



}