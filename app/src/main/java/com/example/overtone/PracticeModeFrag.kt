package com.example.overtone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.overtone.data.SingleChord
import kotlinx.android.synthetic.main.fragment_practice_mode.*

/**
 * A simple [Fragment] subclass.
 * Use the [PracticeModeFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class PracticeModeFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var chordList: MutableList<SingleChord>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chordList = MainActivity.getAllSingleChords()
        setUpSpinner(chordList!!) // using !! to signify chordList is not null
    }

    private fun setUpSpinner(arrayToAttach: MutableList<SingleChord>){
        val chordNames = arrayToAttach.map{it.chordName}
        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,chordNames)
        spinner.adapter = arrayAdapter
        setUpSpinnerListener()
    }

    private fun setUpSpinnerListener(){
        spinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    Toast.makeText(requireContext(),"I have been selected boy",Toast.LENGTH_SHORT).show()
                }
            }


        }




    }


}

