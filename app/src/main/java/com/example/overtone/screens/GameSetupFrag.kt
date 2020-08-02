package com.example.overtone.screens
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.overtone.R
import com.example.overtone.metronomePlayer.Metronome
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_game_setup.*
import java.util.ArrayList


class GameSetupFrag : Fragment(),View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener {
    private var count = 0
    private val singleChords = MainActivity.allSingleChords
    private var navController: NavController? = null
    private var dialogOpener: Button? = null
    private var playBtn: Button? = null
    private lateinit var listItems: Array<String>
    private var bpmRep: TextInputLayout? = null
    private var seekBar: SeekBar? = null
    private val STARTING_BPM_VAL = 20
    private val MAX_BPM_VAL = 120
    private val MIN_BPM_VAL = 10
    private var currentBpm = STARTING_BPM_VAL
    private var items:String = "Chords in Rotation"
    private var itemsAndStates:MutableMap<String,Boolean> = mutableMapOf()
    companion object{
        var metronome: Metronome? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        println("home fragment being created $count")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_setup, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        metronome = Metronome(context)
        setBtns(view)
        setTextViews()
        setEditTextsLayout(view)
        setListItems()
        Log.d("TESTING FOR RE ENTRY", "onViewCreated: ")
        setUpSeekBar(view)
        println("debug: before setMapofItems $itemsAndStates")
        setMapofItems()
        println("debug onViewCreated: ${itemsAndStates.values},  list items ${listItems.contentToString()}")
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

    private fun setTextViews() {
        chordsInRotationTxtView.text = items
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

    //onClick listener for dialog list and operator
    override fun onClick(v: View) {
        when (v.id) {
            R.id.openChordDialogBtn -> startChordDialog()
            R.id.BpmEntry ->  bpmRep!!.editText!!.isCursorVisible = true /* to combat the effects of turning cursor of after submission in onEditorAction*/
            R.id.playChords -> {
                count++
                val gameChords = itemsAndStates.filter{ it.value == true }.keys
                ///nav controller transfers info to another fragment,
                ///unpack argument
                if (gameChords.size < 2) {
                    Toast.makeText(context, "Select 2 or more chords, here is $count", Toast.LENGTH_SHORT).show()
                } else {
                    navController = Navigation.findNavController(v)
                    Toast.makeText(context,"WOULD be starting game", Toast.LENGTH_SHORT).show()
                    println("debug: chords to be sent to the next fragment $gameChords")
                    val action = GameSetupFragDirections.actionGameSetupFragToPlayGameFrag(currentBpm,gameChords.toTypedArray())
                    navController?.navigate(action)
//                    navController?.navigate(R.id.action_gameSetupFrag_to_playGameFrag)
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
            chordsInRotationTxtView?.text = items
            ///test code
            println("multiChoiceDebug: ${itemsAndStates.toString()}")
            println("chords appearing/item var val: ${items} --- CheckedItems from map values ${itemsAndStates.values}--- " +
                    " listItems ${listItems.contentToString()}" )
        }

        mBuilder.setNeutralButton("Clear All") { dialogInterface, which ->
            for (key in itemsAndStates.keys) {
                //setting selection state to false
                itemsAndStates[key] = false
            }
            println("debug CLEARED: $itemsAndStates")
            items = noneSelectedTitle
            chordsInRotationTxtView.text = items
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    /// seekBar interface methods
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        //update the textView to show user the scrolled to BPM
        currentBpm = progress
        bpmRep?.editText?.setText("" + currentBpm)
    }
    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    //editorAction interface method
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
