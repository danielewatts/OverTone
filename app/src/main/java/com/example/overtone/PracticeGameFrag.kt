package com.example.overtone
import android.os.Bundle
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
        testInfo.setText(chordsInRotation.toString() +" "+ bpm.toString())

    }

    private fun retrievePassedInfo(){
        if(arguments!=null){
            var args = PracticeGameFragArgs.fromBundle(requireArguments())
            bpm = args.tempo
            chordsInRotation = mutableListOf(*args.chordsInGame)
        }


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            StopGameBtn.id -> navController?.navigate(R.id.action_practiceGameFrag_to_practiceModeFrag)

        }

    }


}