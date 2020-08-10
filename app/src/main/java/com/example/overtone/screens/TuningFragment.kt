package com.example.overtone.screens

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import com.example.overtone.R
import com.example.overtone.data.DataCreation
import com.example.overtone.data.GuitarString
import com.example.overtone.data.MenuItemData
import com.example.overtone.wheeladapters.WheelTextAdapter
import kotlinx.android.synthetic.main.fragment_tuning.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TuningFragment : Fragment(),View.OnClickListener {
    private var guitarStrings:MutableList<GuitarString> = mutableListOf()
    private var menuItemData:ArrayList<MenuItemData> = ArrayList()
    private var isTuning:Boolean = false
    private lateinit var mHandler: Handler
    private lateinit var tuningRunnable: Runnable
    private var testingCount =1

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
        tuningRunnable = getRunnable()


    }


    private fun setGuitarWheelSpinner(){
        setGuitarStrings()
        setWheelMenuData()
        val wheelTextAdapter = WheelTextAdapter(context, menuItemData)
        wheelSpinner.setAdapter(wheelTextAdapter)
        wheelSpinner.setOnMenuSelectedListener { parent, view, pos ->
            //debugging code
            isTuning = true
            startFakeTune()
//            captureAndDisplayFreq()
//            Toast.makeText(context, "Top Menu selected position:" + pos, Toast.LENGTH_SHORT).show()

            //debugging code
        }
    }

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
            tuneBtn.id ->{
//                Toast.makeText(context,"TEST RESULT WOO",Toast.LENGTH_LONG).show()
                stopFakeTune()
            }
        }
    }

    private fun captureAndDisplayFreq(){
        val dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)
        val pdh = PitchDetectionHandler { result, e ->
            val pitchInHz = result.pitch
            run{(Runnable {
                if (isTuning == true) {
                    tuningValueText.text = pitchInHz.toString()
                }
            })}
        }
        val p: AudioProcessor = PitchProcessor(PitchEstimationAlgorithm.FFT_YIN, 22050F, 1024, pdh)
        dispatcher.addAudioProcessor(p)
        val recordingThread = Thread(dispatcher, "Audio Dispatcher") //dispatcher is a runnable object, using thread constructor (runnable targ, String name)
        recordingThread.start()
    }

    private fun startListeneing(){

    }
//    private fun getListeningRunnable(tempo)

    private suspend fun setTextOnMainThread(input:String){
        withContext(Dispatchers.Main){
            tuningValueText.text = input
        }
    }

    private fun startFakeTune(){
        mHandler = Handler()
        mHandler.post(tuningRunnable)
    }
    private fun stopFakeTune(){
        mHandler.removeCallbacks(tuningRunnable)
        Toast.makeText(context,"Stopping tunning operation",Toast.LENGTH_LONG).show()
        println("debug: stop Fake Tune called")
    }

    private fun getRunnable():Runnable{
        return Runnable {
            while(testingCount < 100000){
                println("debugging: testingcount, tuning frag = $testingCount")
                testingCount++}
//                mHandler.postDelayed(this,1000)
        }
    }

    /** investigate why this ^^^^^ does not stop when remove callbacks is called, still freezing user */



}

