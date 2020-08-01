package com.example.overtone
import android.os.Bundle
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
    private var chordsSelectedView: TextView? = null
    private var bpmRep: TextInputLayout? = null
    private val singleChords = MainActivity.allSingleChords
    private var seekBar: SeekBar? = null
    private val STARTING_BPM_VAL = 20
    private val MAX_BPM_VAL = 120
    private val MIN_BPM_VAL = 10
    private var currentBpm = STARTING_BPM_VAL
    private var items:String = "Chords in Rotation"
    private var itemsAndStates:MutableMap<String,Boolean> = mutableMapOf()
    companion object {
        var metronome: Metronome? = null
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        metronome = Metronome(context)
        setBtns(view)
        setTextViews(view)
        setEditTextsLayout(view)
        setListItems()
        setUpSeekBar(view)
        setMapofItems()
        Log.d("TESTING FOR RE ENTRY", "onViewCreated: ")
        checkedItems = BooleanArray(listItems.size)
    }
    private fun setMapofItems(){
        //set keys as listItems array
        if(itemsAndStates.isEmpty()) {
            for (chordName in listItems) {
                itemsAndStates.put(chordName, false)
            }
        }
    }
    private fun setBtns(view: View) {
        dialogOpener = view.findViewById(R.id.openChordDialogBtn)
        dialogOpener?.setOnClickListener(this)
        playBtn = view.findViewById(R.id.playChords)
        playBtn?.setOnClickListener(this)
    }

    private fun setTextViews(view: View) {
        chordsSelectedView = view.findViewById(R.id.chordsInRotationTxtView)
        chordsSelectedView?.text = items
    }

    private fun setEditTextsLayout(v: View) {
        bpmRep = v.findViewById(R.id.TextInputLayout)
        bpmRep?.editText?.setOnEditorActionListener(this)
        bpmRep?.editText?.setOnClickListener(this)
        bpmRep?.editText?.setText(currentBpm.toString())
    }

    private fun setListItems() {
        val chordNames = ArrayList<String>()
        for (sg in singleChords!!) {
            chordNames.add(sg.name)
        }
        listItems = chordNames.toTypedArray()
    }

    private fun setUpSeekBar(view: View) {
        seekBar = view.findViewById(R.id.SeekBarBpm)
        seekBar?.min = MIN_BPM_VAL
        seekBar?.max = MAX_BPM_VAL
        seekBar?.progress = STARTING_BPM_VAL
        //set the seekbarChangeListener AFTER boundaries and initial condition is set
        seekBar?.setOnSeekBarChangeListener(this)

    }

    //checkedItems and listItems are parallel arrays, indices where true occurs are the locations
    //of the checked chord names
    val chordsInRotation: Array<String>
        get() {
            val chordsInRot = ArrayList<String>()
            //checkedItems and listItems are parallel arrays, indices where true occurs are the locations
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
            R.id.BpmEntry ->  bpmRep!!.editText!!.isCursorVisible = true /* to combat the effects of turning cursor of after submission in onEditorAction*/
            R.id.playChords -> {
                val gameChords = itemsAndStates.filter{ it.value == true }.keys
                ///nav controller transfers info to another fragment,
                if (gameChords.size < 2) {
                    Toast.makeText(context, "Select 2 or more chords", Toast.LENGTH_SHORT).show()
                } else {
                    navController = Navigation.findNavController(v)
                    Toast.makeText(context,"WOULD be starting game",Toast.LENGTH_SHORT).show()
                    println("debug: chords to be sent to the next fragment $gameChords")
                    val action = PracticeModeFragmentDirections.actionPracticeModeFragToPracticeGameFrag(currentBpm, gameChords.toTypedArray())
                    navController!!.navigate(action)
                    navController?.navigate(R.id.action_practiceModeFrag_to_practiceGameFrag)
                }
            }
        }
    }

    private fun startChordDialog() {
        println("Status of chord dialog variables $itemsAndStates")
        val noneSelectedTitle = "No chords selected"
        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle("Chords")
        mBuilder.setMultiChoiceItems(itemsAndStates.keys.toTypedArray(), itemsAndStates.values.toBooleanArray()) { dialogInterface, position, isChecked ->
            val selectedChord = listItems[position]
            //mark boolean value of map if item has been checked by user or not
            itemsAndStates[selectedChord] = isChecked
            //value is now marked as checked = true or checked = false
        }

        mBuilder.setCancelable(false)
        mBuilder.setPositiveButton("Finish") { dialogInterface, which ->
            val selectedChords = itemsAndStates.filter{ it.value == true }.keys
            var chords = selectedChords.toString().filterNot { it=='[' || it== ']' }
            if(chords!=""){
                items = chords
            }
            ///test code
            chordsSelectedView?.text = items
            ///test code
            println("multiChoiceDebug: ${itemsAndStates.toString()}")
            println("chords appearing/item var val: ${items} --- CheckedItems from map values ${itemsAndStates.values}-- " +
                    " listItems ${listItems.contentToString()}" )
        }

        mBuilder.setNeutralButton("Clear All") { dialogInterface, which ->
            for (key in itemsAndStates.keys) {
                //setting selection state to false
                itemsAndStates[key] = false
            }
            println("debug CLEARED: $itemsAndStates")
            items = noneSelectedTitle
            chordsSelectedView?.text = items
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    ///tracking change in BPM to the user
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        //update the textView to show user the scrolled to BPM
        currentBpm = progress
        bpmRep?.editText?.setText("" + currentBpm)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        //plays sample BPM for user when seekBar is no longer being touched
        Toast.makeText(context,"Tracked",Toast.LENGTH_SHORT).show()
//        playSample((1000*60).div(currentBpm.toLong()))
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
        var barVal = bpmRep?.editText?.text.toString()
        var seekVal = barVal.toInt()
        //check to see if user entered BPM is higher than MAX
        if (seekVal > MAX_BPM_VAL) {
            seekVal = MAX_BPM_VAL
            Toast.makeText(context, "Max BPM is $MAX_BPM_VAL", Toast.LENGTH_SHORT).show()
        }
        //check if entered BPM is lower than min
        if (seekVal < MIN_BPM_VAL) {
            seekVal = MIN_BPM_VAL
            Toast.makeText(context, "Min BPM is $MIN_BPM_VAL", Toast.LENGTH_SHORT).show()
        }
        currentBpm = seekVal
        seekBar?.progress = seekVal
        bpmRep?.editText?.setText(seekVal.toString())
        bpmRep?.editText?.isCursorVisible = false
        return false
    }

}