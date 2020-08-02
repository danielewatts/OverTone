package com.example.overtone.metronomePlayer

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.example.overtone.R


class Metronome(private var context:Context?) {

    private lateinit var soundPool: SoundPool
    private var soundID =1
    private val playBackRate:Float = 1.0F
    private val topPlayBackRate:Float = 1.5F
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
        //assigning sound member
        soundID =  soundPool?.load(context, R.raw.wood,1)
        }

    fun makeGameSound(){
        //could make capability here to account for 1st beat of every 4/4 measure
        if(beatNumber==1){
            println("MAKING 1st @#!@#@! SOUND !!!!!!!!!!")
            //plays higher pitch to indicate starting beat
            playSoundPool(soundID,topPlayBackRate)
        }
        else{
            println("MAKING regular SOUND ")
            //plays lower pitch to indicate other beats
            playSoundPool(soundID,playBackRate)
        }
        if(beatNumber == 4){
            beatNumber = 1
        }
        else{
            beatNumber++
        }

    }
    private fun playSoundPool(soundId:Int, playBckRate:Float){
        soundPool.play(soundId,1F,1F,0,0,playBckRate)
    }

    fun killMetronome() {
        soundPool.release()
    }



}