package com.example.overtone
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.overtone.metronomePlayer.Metronome
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class PracticeModeFragment: Fragment(), View.OnClickListener, OnSeekBarChangeListener, OnEditorActionListener {
    private var navController: NavController? = null
    private var dialogOpener: Button? = null
    private var playBtn: Button? = null
    private lateinit var listItems: Array<String>
    private lateinit var checkedItems: BooleanArray
    private var chordsSelected: TextView? = null
    private var bpmRep: TextInputLayout? = null
    private val singleChords = MainActivity.getAllSingleChords()
    private val selectedChordNames = ArrayList<Int>()
    private var seekBar: SeekBar? = null
    private val STARTING_BPM_VAL = 10
    private val MAX_BPM_VAL = 120
    private val MIN_BPM_VAL = 1
    private var currentBpm = 0
    private var moves = 0
    private lateinit var gameRunnable:Runnable
    private lateinit var mainHandler: Handler



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        metronome = Metronome(context)
        mainHandler = Handler()
        setBtns(view)
        setTextViews(view)
        setEditTextsLayout(view)
        setListItems()
        Log.d("TESTING FOR RE ENTRY", "onViewCreated: ")
        setUpSeekBar(view)
        checkedItems = BooleanArray(listItems.size)
    }

    fun setBtns(view: View) {
        dialogOpener = view.findViewById(R.id.openChordDialogBtn)
        dialogOpener?.setOnClickListener(this)
        playBtn = view.findViewById(R.id.playChords)
        playBtn?.setOnClickListener(this)
    }

    fun setTextViews(view: View) {
        chordsSelected = view.findViewById(R.id.chordsInRotation)
    }

    fun setEditTextsLayout(v: View) {
        bpmRep = v.findViewById(R.id.TextInputLayout)
        bpmRep?.editText!!.setOnEditorActionListener(this)
        bpmRep?.editText!!.setOnClickListener(this)
    }

    fun setListItems() {
        val chordNames = ArrayList<String>()
        for (sg in singleChords) {
            chordNames.add(sg.name)
        }
        listItems = chordNames.toTypedArray()
    }

    fun setUpSeekBar(view: View) {
        seekBar = view.findViewById(R.id.SeekBarBpm)
        seekBar?.setOnSeekBarChangeListener(this)
        seekBar?.min = MIN_BPM_VAL
        seekBar?.max = MAX_BPM_VAL
        seekBar?.progress = STARTING_BPM_VAL
        currentBpm = STARTING_BPM_VAL
    }

    //checkedItems and listItems are parrallel arrays, indices where true occurs are the locations
    //of the checked chord names
    val chordsInRotation: Array<String>
        get() {
            val chordsInRot = ArrayList<String>()
            //checkedItems and listItems are parrallel arrays, indices where true occurs are the locations
            //of the checked chord names
            for (i in checkedItems.indices) {
                if (checkedItems[i] == true) {
                    chordsInRot.add(listItems[i])
                }
            }
            return chordsInRot.toTypedArray()
        }

    //onClick listener for dialog list and operator
    override fun onClick(v: View) {
        when (v.id) {
            R.id.openChordDialogBtn -> startChordDialog()
            R.id.BpmEntry ->                 /* to combat the effects of turning cursor of after submission in onEditorAction*/bpmRep!!.editText!!.isCursorVisible = true
            R.id.playChords -> {
                ///nav controller transfers info to another fragment,
                ///unpack argument
                val chordsInRotation = chordsInRotation
                if (chordsInRotation.size < 2) {
                    Toast.makeText(context, "Select 2 or more chords", Toast.LENGTH_SHORT).show()
                } else {
                    navController = Navigation.findNavController(v)
                    val action = PracticeModeFragDirections.actionPracticeModeFragToPracticeGameFrag(currentBpm, chordsInRotation)
                    navController!!.navigate(action)
                }
            }
        }
    }

    private fun startChordDialog() {
        val mBuilder = AlertDialog.Builder(requireContext())
        selectedChordNames.clear() /// keeping list clean when fragment gets used again
        mBuilder.setTitle("Chords available to be selected")
        mBuilder.setMultiChoiceItems(listItems, checkedItems) { dialogInterface, position, isChecked ->
            if (isChecked) {
                selectedChordNames.add(position)
            } else {
                selectedChordNames.remove(Integer.valueOf(position))
            }
        }
        mBuilder.setCancelable(false)
        mBuilder.setPositiveButton("OK") { dialogInterface, which ->
            var item = ""
            for (i in selectedChordNames.indices) {
                item = item + listItems[selectedChordNames[i]]
                if (i != selectedChordNames.size - 1) {
                    item = "$item, "
                }
            }
            Log.d("Right before text is getting Set", item)
            chordsSelected!!.text = item
        }
        mBuilder.setNeutralButton("Clear All") { dialogInterface, which ->
            for (i in checkedItems.indices) {
                checkedItems[i] = false
                selectedChordNames.clear()
                chordsSelected!!.text = ""
            }
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    ///tracking change in BPM to the user
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        currentBpm = progress
        //updating BPM display to keep in sync with progress bar position
        bpmRep!!.editText!!.setText("" + currentBpm)
        println("DEBUG progress bar changed ")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    ///called when user releases seekbar toggle
    override fun onStopTrackingTouch(seekBar: SeekBar) {
        ///play sample beat, want BPM in this method// run runnable
        metronome?.makeSound()
        playSample((1000*60).div(currentBpm).toLong())
        println("DEBUG stopped MOVING SEEKBAR ")
        Toast.makeText(context,"BRO",Toast.LENGTH_SHORT).show()
        moves++

    }

    private fun playSample(tempo:Long){
        gameRunnable = getBpmSampleRunnable(tempo)
        startSample()
    }
    private fun getBpmSampleRunnable(tempo:Long):Runnable{
        return object : Runnable {
            override fun run() {
                metronome?.makeSound()
                println("SAMPLE runnninh")
                mainHandler.postDelayed(this,tempo)
            }
        }
    }



    private fun startSample(){
        mainHandler.post(gameRunnable)
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        val barVal = bpmRep!!.editText!!.text.toString()
        var seekVal = barVal.toInt()
        //check to see if user entered BPM is higher than MAX
        if (seekVal > MAX_BPM_VAL) {
            seekVal = MAX_BPM_VAL
            Toast.makeText(context, "Max BPM is $MAX_BPM_VAL", Toast.LENGTH_SHORT).show()
        }
        if (seekVal < MIN_BPM_VAL) {
            seekVal = MIN_BPM_VAL
            Toast.makeText(context, "Min BPM is $MIN_BPM_VAL", Toast.LENGTH_SHORT).show()
        }
        currentBpm = seekVal
        seekBar!!.progress = seekVal
        bpmRep!!.editText!!.setText(seekVal.toString())
        bpmRep!!.editText!!.isCursorVisible = false
        return false
    }

    companion object {
        var metronome: Metronome? = null
    }
}