package com.example.overtone.metronomePlayer

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.example.overtone.R
import java.util.logging.Handler


class Metronome(private var context:Context) {

    private lateinit var soundPool: SoundPool
    private var soundID =1
    private var playBackRate:Float = 1.0F
    private var beatNumber:Int = 1

    init {
        ////call all soundPool initializer and set all values in class
        createSoundPool()

    }

    private fun createSoundPool(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            soundPool = SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        ///assigning sound member
        soundID =  soundPool?.load(context, R.raw.wood,1)
    }

    fun makeSound(){
        //could make capability here to account for 1st beat of every 4/4 measure
        if(beatNumber==1){
            //plays higher pitch to indicate starting beat
            soundPool?.play(soundID, 1F, 1F, 0, 0, 1.5F)
        }
        else{
            //plays lower pitch to indicate other beats
            soundPool?.play(soundID, 1F, 1F, 0, 0, playBackRate)
        }
        if(beatNumber == 4){
            beatNumber = 1
        }
        else{
            beatNumber++
        }


    }




}