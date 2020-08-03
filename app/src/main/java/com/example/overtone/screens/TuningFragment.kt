package com.example.overtone.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.overtone.R
import com.example.overtone.data.DataCreation
import com.example.overtone.data.GuitarString
import com.example.overtone.data.MenuItemData
import com.example.overtone.wheeladapters.WheelTextAdapter
import kotlinx.android.synthetic.main.fragment_tuning.*
import kotlin.collections.ArrayList

class TuningFragment : Fragment(),View.OnClickListener {
    private var guitarStrings:MutableList<GuitarString> = mutableListOf()
    private var menuItemData:ArrayList<MenuItemData> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtn()
//        println("debug of wheel menu item data ${menuItemData[menuItemData.size-1].guitarStringName}")
        setGuitarWheelSpinner()

    }
    private fun setGuitarWheelSpinner(){
        setGuitarStrings()
        setWheelMenuData()
        val wheelTextAdapter = WheelTextAdapter(context, menuItemData)
        wheelSpinner.setAdapter(wheelTextAdapter)
        wheelSpinner.setOnMenuSelectedListener { parent, view, pos ->
            //debugging code
            Toast.makeText(context, "Top Menu selected position:" + pos, Toast.LENGTH_SHORT).show()
            //debugging code
        }
    }

//    private fun setWheelItemData():MutableList<GuitarString> {
//        var guitarStrings = DataCreation.getAllGuitarStrings(context)
//        println(guitarStrings)
//    }
    private fun setGuitarStrings(){
        guitarStrings =  DataCreation.getAllGuitarStrings(context)
        println("debug ${guitarStrings[guitarStrings.size-1].frequency}")
    }

    private fun setWheelMenuData(){
        for(GuitarString in guitarStrings){
            menuItemData.add(MenuItemData(GuitarString.name))
        }
    }



    private fun setBtn(){
        tuneBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            tuneBtn.id ->{ Toast.makeText(context,"TEST RESULT WOO",Toast.LENGTH_LONG).show()}
        }
    }


}