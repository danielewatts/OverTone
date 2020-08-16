package com.example.overtone.screens

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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


class TuningFragment : Fragment(),View.OnClickListener{
    private var guitarStrings:MutableList<GuitarString> = mutableListOf()
    private var menuItemData:ArrayList<MenuItemData> = ArrayList()
    private var isTuning:Boolean = false
    private lateinit var mHandler: Handler
    private lateinit var tuningRunnable: Runnable
    private var testingCount =1
    private var freqMidPt:Float = 82.4F

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtn()
        setGuitarWheelSpinner()
        isTuning = true
        startTune()
    }


    private fun startTune(){
        val dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)
        val pdh = PitchDetectionHandler { result, e ->
            val pitchInHz = result.pitch
            activity?.runOnUiThread {
                println("THREAD IS RUNNING ")
                if (isTuning == true) {
//                    tuningValueText.text = pitchInHz.toString()
                    val pitchRangeInfo = getValidity(pitchInHz)
                    updatePitchInfo(pitchRangeInfo)


                } else {
                    dispatcher.stop()
                    println("audio dispatcher stopped")
                }
            }
        }
        val p: AudioProcessor = PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050F, 1024, pdh)
        dispatcher.addAudioProcessor(p)
        val recordingThread = Thread(dispatcher, "Audio Dispatcher") //dispatcher is a runnable object, using thread constructor (runnable targ, String name)
        recordingThread.start()

    }

    private fun updatePitchInfo(pitchValidityPoints: Int) {
        tuningValueText.text = pitchValidityPoints.toString()
    }

    private fun getValidity(userFrequency:Float):Int{
        var pointsOff = 0
        /* logic to see how high or low currentFreq is, perhaps return a value to indicate sharp or flat */
        // #1 makes call to


        return pointsOff
    }


    private fun setGuitarWheelSpinner(){
        setGuitarStrings()
        setWheelMenuData()
        val wheelTextAdapter = WheelTextAdapter(context, menuItemData)
        wheelSpinner.setAdapter(wheelTextAdapter)
        wheelSpinner.setOnMenuSelectedListener { parent, view, pos ->
            //debugging code
            /** logic to return / modify global freq midPt */



//            startFakeTune()
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
//                stopFakeTune()
                isTuning = false
                Toast.makeText(context,"Stopping Tune",Toast.LENGTH_LONG).show()
            }
        }
    }












}

