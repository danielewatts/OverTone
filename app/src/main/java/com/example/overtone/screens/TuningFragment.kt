package com.example.overtone.screens

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import be.tarsos.dsp.AudioDispatcher
//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import com.example.overtone.R
import com.example.overtone.data.DataCreation
import com.example.overtone.data.GuitarString
import com.example.overtone.data.MenuItemData
import com.example.overtone.utils.PermissionHandler
import com.example.overtone.wheeladapters.WheelTextAdapter
import kotlinx.android.synthetic.main.fragment_tuning.*
import kotlinx.coroutines.*
import kotlin.math.abs


class TuningFragment : Fragment(),View.OnClickListener{
    private var guitarStrings:MutableList<GuitarString> = mutableListOf()
    private var currentGuitarString:GuitarString? = null
    private var guitarStringMap:MutableMap<String,GuitarString> = mutableMapOf()
    private var menuItemData:ArrayList<MenuItemData> = ArrayList()
    private var isTuning:Boolean = false
    private var permGrabbed = false

    private var permissions:Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
    private var permissionAll:Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtn()
        setGuitarWheelSpinner()
        getPermissions()
        isTuning = true

    }


    private fun getPermissions(){
    if(PermissionHandler.hasPermissions(context,permissions)!=true){
        PermissionHandler.getNeededPermissions(context,activity,permissions,permissionAll)
    }
    while(!permGrabbed){
        if(PermissionHandler.hasPermissions(context,permissions)){
            println("Permission granted to start audioRecording")
            startTune()
            permGrabbed = true
        }
    }
}

    private fun startTune(){
        val dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)
        val pdh = PitchDetectionHandler { result, e ->
            val pitchInHz = result.pitch
            activity?.runOnUiThread {
                println("THREAD IS RUNNING ")
                if (isTuning == true) {
                    val pitchRangeInfo = getValidity(currentGuitarString,pitchInHz)
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

    //UI is updated according to the sharpness/flatness indicated by pitchValidityPoints
    private fun updatePitchInfo(pitchValidityPoints: Int) {
        var pitchInfo = if (pitchValidityPoints<=-8){
            "choose a lower string if possible"
        }
        else if (pitchValidityPoints>=8){
            "choose a higher string if possible"
        }
        ///pitch is within the range of it's target , user is probably tuning the correct string
        else{
            "$pitchValidityPoints"
        }
        tuningValueText.text = pitchInfo

    }

    private fun getValidity(guitarString: GuitarString?,detectedPitch:Float):Int{
        return (detectedPitch-guitarString!!.frequency).toInt()
    }


    private fun setGuitarWheelSpinner(){
        setGuitarStrings()
        setWheelAndGuitarMapData()
        val wheelTextAdapter = WheelTextAdapter(context, menuItemData)
        wheelSpinner.setAdapter(wheelTextAdapter)
        wheelSpinner.setOnMenuSelectedListener { parent, view, pos ->

            //remove toast for final product
            Toast.makeText(context,"Selected ${guitarStringMap[menuItemData[pos].guitarStringName]} chord",Toast.LENGTH_SHORT).show()

            val guitarNameStringSelected = menuItemData[pos].guitarStringName
            val guitarStringSelected = guitarStringMap[guitarNameStringSelected]
            currentGuitarString = guitarStringSelected
        }
    }

    private fun setGuitarStrings(){
        guitarStrings =  DataCreation.getAllGuitarStrings(context)
        for (guitarString in guitarStrings){
            guitarString.setFreqRange()
        }
        println(" setGuitarStrings Debug: ${guitarStrings.size}")
    }

    private fun setWheelAndGuitarMapData(){
        for(guitarString in guitarStrings){
            menuItemData.add(MenuItemData(guitarString.name))
            guitarStringMap[guitarString.name] = guitarString
        }
    }

    private fun setBtn(){
        tuneBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            tuneBtn.id ->{
                ///kill the tuning functionality...might just be for testing / early development
                isTuning = false
                Toast.makeText(context,"Stopping Tune",Toast.LENGTH_LONG).show()
            }
        }
    }












}

