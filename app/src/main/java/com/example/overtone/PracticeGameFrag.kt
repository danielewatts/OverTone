package com.example.overtone
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_practice_game.*

class PracticeGameFrag : Fragment(),View.OnClickListener{
    private var chordsInRotation:MutableList<String> = mutableListOf()
    private var bpm:Int? = null
    private var navController:NavController? = null
    private var mHandler = Handler()
    private var mRunnable:Runnable = Runnable {  }
    private val BPM_MILLISECONDS_CONVERSION =60*1000
    private lateinit var mp:MediaPlayer

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
        initiliazeMediaPlayer()
        startGame()
    }

    private fun retrievePassedInfo(){
        if(arguments!=null){
            var args = PracticeGameFragArgs.fromBundle(requireArguments())
            bpm = args.tempo
            chordsInRotation = mutableListOf(*args.chordsInGame)
        }
    }

    private fun startGame(){
        var count = 1
        mRunnable = object : Runnable{
            override fun run() {
                if(count<=4){
                    //in warm up condition, display tempo countdown
                    testInfo.text = count.toString()
                    count++
                }
                else{
                    //no longer in warm up countdown transition, display the random chords
                    testInfo.text = getRandomChord()
                    //var tempoSeconds = BPM_MILLISECONDS_CONVERSION.div(bpm!!).toLong()
                }
                playSound()
                var tempoSeconds = BPM_MILLISECONDS_CONVERSION.div(bpm!!).toLong()
                mHandler.postDelayed(this, tempoSeconds)
            }
        }
        mHandler.post(mRunnable)
    }

    private fun stopGame(){
        mHandler.removeCallbacks(mRunnable)
    }

    private fun getRandomChord():String{
        val randomIndex = (0 until chordsInRotation.size).random() // random integer between 0 & size-1
        return chordsInRotation[randomIndex]
    }
    private fun initiliazeMediaPlayer(){
        mp = MediaPlayer.create(context,R.raw.wood)
        mp.isLooping = true
    }
    private fun playSound(){
        ///logic fo playing sound on request
        mp.start()

    }
    private fun stopMetroSound(){
        mp.stop()
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            StopGameBtn.id ->{
                stopMetroSound()
                navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)
                stopGame()
            }
        }
    }










}


