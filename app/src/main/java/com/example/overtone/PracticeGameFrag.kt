package com.example.overtone
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.overtone.metronomePlayer.Metronome
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
    private lateinit var mainHandler: Handler
    private var soundID = 1
    private var countOffVal = 1 /// this is a debugging/logger variable, delete for final product
    private lateinit var gameRunnable:Runnable
    private var launchCount:Int = 0
    private var metro:Metronome? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_practice_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        metro = PracticeModeFrag.metronome /// creating metronome in the other fragment ensures
        // that sound player is loaded and ready to go
//        initMetronome()
        StopGameBtn.setOnClickListener(this)
        retrievePassedInfo()
        gameRunnable = getGameRunnable()
        startGame()
    }

//    private fun initMetronome(){
//        metro = Metronome(context)
//    }

    private fun startGame(){
        mainHandler = Handler()
        mainHandler.post(gameRunnable)
    }

    private fun getGameRunnable():Runnable{
        return object : Runnable {
            override fun run() {

                playGame()
                var tempoDelay = bpm?.let { bpmToMiliFactor.div(it).toLong()}
//                println("DEBUG: $tempoDelay ms between cycles of runnable executable $countOffVal")
//                countOffVal++
                if (tempoDelay != null) {
                    mainHandler.postDelayed(this,tempoDelay )
                }

            }
        }
    }

    private fun playGame(){
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                val time1 = measureTimeMillis {
                    if(countOffVal<=4){
                        setTextOnMainThread(countOffVal.toString())
                        println("debug: printing countOffVal $countOffVal")
                        countOffVal++
                    }
                    else{
                        setTextOnMainThread(getRandomChord())
                    }
                }
                println("debug: compeleted job1 in $time1 ms.")
            }
            launch {
                val time2 = measureTimeMillis {
                    if(countOffVal==1)
                        println("debug: countOffVal $countOffVal ************")

                    metro?.makeSound()
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
        mainHandler.removeCallbacks(gameRunnable)
    }

    override fun onDestroy() {
        println("DEBUG: onDESTROY called")
        endGame()
        metro?.killMetronome()
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
                navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)
            }
        }
    }










}


