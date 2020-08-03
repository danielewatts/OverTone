package com.example.overtone.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.overtone.R
import kotlinx.android.synthetic.main.fragment_tuning.*

class TuningFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtn()
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