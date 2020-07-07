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
    private val BPM_MILLISECONDS_CONVERSION =60*1000

    ///this starts the repeating process of selecting a random chord
    private val mToastRunnable: Runnable = object : Runnable {
        override fun run() {
            //// action to be done on a timer
            Toast.makeText(context, "This is a delayed toast", Toast.LENGTH_SHORT).show()
            testInfo.text = getRandomChord()


            //// Repeatable Action
            var delay = (1/400)
            mHandler.postDelayed(this, 4000)
        }
    }

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
        //testing code
        ///starts the game
        startGame()
        testInfo.text = chordsInRotation.toString() +" "+ bpm.toString()

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

    private fun startGame() {
        //mHandler.postDelayed(mToastRunnable, 5000);
        mToastRunnable.run()
    }

    private fun stopRepeating() {
        mHandler.removeCallbacks(mToastRunnable)
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            StopGameBtn.id ->{
                stopRepeating()
                navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)

            }

        }

    }








}


