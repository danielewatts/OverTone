package com.example.overtone
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
    private lateinit var mp:MediaPlayer
    private lateinit var soundPool: SoundPool
    lateinit var mainHandler: Handler
    private var soundID = 1
    private var countOffVal = 1
    private lateinit var mRunnable:Runnable

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
        navController = view?.findNavController()
        StopGameBtn.setOnClickListener(this)
        retrievePassedInfo()
        initSoundPool()
//        startGame()
        mRunnable = proGamerMoves()
        proStart(mRunnable)
    }

    private fun proStart(runnable: Runnable){
        mainHandler = Handler()
        mainHandler.post(runnable)

    }

    private fun proGamerMoves():Runnable{
        return object : Runnable {
            override fun run() {

                //this is where the methods that operate the game need to be called
                //test first with just playing sound at a passed in beat
                playGame()
                var tempoDelay = bpm?.let { bpmToMiliFactor.div(it).toLong()}
                println("DEBUG: $tempoDelay ms between cycles of runnable executable")
//                testInfo.text = countOffVal.toString()
//                Log.d("COUNT OFF VAL", "$countOffVal")
//                countOffVal++
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
        var playBackRate = 1.95F
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


//    private fun endGame(){
//        soundPool.release()
//        mainHandler.removeCallbacks(practiceGameTask)
//    }

//    override fun onDestroy() {
//        endGame()
//        super.onDestroy()
//    }

//    override fun onPause() {
//        mainHandler.removeCallbacks(practiceGameTask)
//        super.onPause()
//    }

//    override fun onResume() {
//        startGame()
//        super.onResume()
//    }

    override fun onClick(v: View?) {
        when(v!!.id){
            StopGameBtn.id ->{
//                endGame()
                ///maybe on destroy ?
                navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)
            }
        }
    }










}


