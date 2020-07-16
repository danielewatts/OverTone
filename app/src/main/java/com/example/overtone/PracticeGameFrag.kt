package com.example.overtone
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_practice_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class PracticeGameFrag : Fragment(),View.OnClickListener{
    private var chordsInRotation:MutableList<String> = mutableListOf()
    private var bpm:Int? = null
    private var navController:NavController? = null
    private val bpmToMiliFactor =60*1000
    private lateinit var soundPool: SoundPool
    private lateinit var mainHandler: Handler
    private var soundID = 1
    private var countOffVal = 1 /// this is a debugging/logger variable, delete for final product
    private lateinit var gameRunnable:Runnable
    private var launchCount:Int = 0

    /** investigate this runnable*/

//    private val practiceGameTask = object : Runnable {
//        override fun run() {
//
//                //this is where the methods that operate the game need to be called
//                //test first with just playing sound at a passed in beat
////                playGame()
//            var tempoDelay = bpm?.let{ bpmToMiliFactor.div(it).toLong()}
//            println("DEBUG: $tempoDelay ms between cycles of runnable executable")
//            testInfo.text = countOffVal.toString()
//            Log.d("COUNT OFF VAL", "$countOffVal")
//            countOffVal++
//            if (tempoDelay != null) {
//                mainHandler.postDelayed(this,tempoDelay )
//            }
//        }
//    }


    ///this starts the repeating process of selecting a random chord

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_practice_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("DEBUG: ON VIEW CREATED CALLED")
        navController = view?.findNavController()
        StopGameBtn.setOnClickListener(this)
        retrievePassedInfo()
        initSoundPool()
//        startGame()
//        mRunnable = getGameRunnable()
        gameRunnable = getGameRunnable()
        startGame()
    }

    private fun startGame(){
        mainHandler = Handler()
        mainHandler.post(gameRunnable)
    }

    private fun getGameRunnable():Runnable{
        return object : Runnable {
            override fun run() {

                //this is where the methods that operate the game need to be called
                //test first with just playing sound at a passed in beat
//                playGame()
//                soundPool?.play(soundID, 1F, 1F, 0, 0, 1.0F)
                var tempoDelay = bpm?.let { bpmToMiliFactor.div(it).toLong()}
                println("DEBUG: $tempoDelay ms between cycles of runnable executable $countOffVal")
//                testInfo.text = countOffVal.toString()
//                Log.d("COUNT OFF VAL", "$countOffVal")
                countOffVal++
                if (tempoDelay != null) {
                    mainHandler.postDelayed(this,tempoDelay )
                }
            }
        }
    }


//    private fun startGame(){
//        mainHandler = Handler()
//        mainHandler.post(practiceGameTask)
//        //starts looping process of calling certain methods and coroutines at a specified bpm
//    }

    private fun initSoundPool(){
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
        soundID =  soundPool?.load(context,R.raw.wood,1)

    }
    private fun playGame(){
        var playBackRate = 1.00F
        //increased playback rate to decrease latency issues with sound file
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                val time1 = measureTimeMillis {
                     setTextOnMainThread(getRandomChord())
                }
                println("debug: compeleted job1 in $time1 ms.")
            }
            launch {
                val time2 = measureTimeMillis {
                    soundPool?.play(soundID, 1F, 1F, 0, 0, playBackRate)
                }
                println("debug: compeleted job2 in $time2 ms.")
            }

        }

    }

    private suspend fun setTextOnMainThread(input: String){
        //must modify UI elements on main thread
        withContext(Dispatchers.Main){
            testInfo.text = input
        }
    }

    private fun retrievePassedInfo(){
        if(arguments!=null){
            var args = PracticeGameFragArgs.fromBundle(requireArguments())
            bpm = args.tempo
            chordsInRotation = mutableListOf(*args.chordsInGame)
        }
    }
    private fun getRandomChord():String{
        val randomIndex = (0 until chordsInRotation.size).random() // random integer between 0 & size-1
        return chordsInRotation[randomIndex]
    }


    private fun endGame(){
//        soundPool.release()
        mainHandler.removeCallbacks(gameRunnable)
    }

    override fun onDestroy() {
        println("DEBUG: onDESTROY called")
        endGame()
        soundPool.release()
        super.onDestroy()
    }

    override fun onPause() {
        println("DEBUG : onPAUSE CALLED")
        endGame()
        super.onPause()
    }

    override fun onResume() {
        if(launchCount>0){
            println("DEBUG: onRESUME CALLING START GAME AGAIN")
            startGame()
        }
        println("DEBUG : onResume CALLED")
        if(chordsInRotation!=null){
            println("DEBUG: ${chordsInRotation.toString()} and temp is ${bpm}")
        }
        launchCount++
        super.onResume()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            StopGameBtn.id ->{
                endGame()
                soundPool.release()
                navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)
            }
        }
    }










}


