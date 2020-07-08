package com.example.overtone
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        start(view)
        //testing code
        ///starts the game
        testInfo.text = chordsInRotation.toString() +" "+ bpm.toString()

    }

    private fun retrievePassedInfo(){
        if(arguments!=null){
            var args = PracticeGameFragArgs.fromBundle(requireArguments())
            bpm = args.tempo
            chordsInRotation = mutableListOf(*args.chordsInGame)
        }


    }

    private fun start(view:View){
        mRunnable = object : Runnable{
            override fun run() {
                testInfo.text = getRandomChord()
                var tempoSeconds = BPM_MILLISECONDS_CONVERSION.div(bpm!!).toLong()
                /// fix this number ^^^^
                if (tempoSeconds != null) {
                    mHandler.postDelayed(this,tempoSeconds)
                }
            }
        }
        mHandler.post(mRunnable)
    }
    private fun stop(view:View){
        mHandler.removeCallbacks(mRunnable)
    }

    private fun getRandomChord():String{
        val randomIndex = (0 until chordsInRotation.size).random() // random integer between 0 & size-1
        return chordsInRotation[randomIndex]
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            StopGameBtn.id ->{
                navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)
                stop(v)
            }

        }

    }








}


