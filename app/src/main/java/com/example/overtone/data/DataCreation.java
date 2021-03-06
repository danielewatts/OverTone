package com.example.overtone.data;
import android.content.Context;
import com.example.overtone.R;
import com.example.overtone.utils.JsonDataRetrieval;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DataCreation {

 private static Gson gson;
 private static ArrayList<ChordGroup> chordGroupsList = new ArrayList<>();
 private static ArrayList<SingleChord> singleChordsList = new ArrayList<>();
 private static final String[] GROUP_NAMES = {"Bar","Open","Popular"};
 private static final String jsonPathChords = "chord.json";
 private static final String jsonPathGuitarStrings = "guitarstrings.json";

    public static ArrayList<SingleChord> getSingleChords(Context context){
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(context,jsonPathChords);
        gson = new Gson();
        singleChordsList = gson.fromJson(jsonString, new TypeToken<ArrayList<SingleChord>>(){}.getType());
        attachChordDiagrams(singleChordsList);
        return singleChordsList;
    }

    public static ArrayList<GuitarString> getAllGuitarStrings(Context context){
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(context,jsonPathGuitarStrings);
        gson = new Gson();
        ArrayList<GuitarString> guitarStrings = new ArrayList<>();
        guitarStrings = gson.fromJson(jsonString,new TypeToken<ArrayList<GuitarString>>(){}.getType());
        return guitarStrings;
    }

    private static void attachChordDiagrams(ArrayList<SingleChord> singleChordsList) {
        ArrayList<Integer> chordDiagramIds = new ArrayList<>();
        //figure out how to automate this, not hard code this code block in
        chordDiagramIds.add(R.drawable.cmajchorddiagram);
        chordDiagramIds.add(R.drawable.amajor);
        chordDiagramIds.add(R.drawable.asevenmajr);
        chordDiagramIds.add(R.drawable.aminor);
        chordDiagramIds.add(R.drawable.bflat);
        chordDiagramIds.add(R.drawable.emajor);
        chordDiagramIds.add(R.drawable.dminor);
        chordDiagramIds.add(R.drawable.gchord);
        chordDiagramIds.add(R.drawable.amj7);
        chordDiagramIds.add(R.drawable.emchord);
        chordDiagramIds.add(R.drawable.dmajor);
        chordDiagramIds.add(R.drawable.fchord);
        chordDiagramIds.add(R.drawable.fsharp);
        chordDiagramIds.add(R.drawable.dsharp);
        chordDiagramIds.add(R.drawable.csharp);

        int index = 0;
        for (SingleChord sg: singleChordsList ) {
            sg.setChordDiagram(chordDiagramIds.get(index));
            index++;
        }
    }


    public static ArrayList<ChordGroup> getCreatedGroups(ArrayList<SingleChord> singleChords){
        //cheap hack, points to refactoring data loading upon app opening
        //makes sure static array does not grow infinitely when fragment is called
        chordGroupsList.clear();
        ////
        Map<DifficultyLevel,ChordGroup> difficultyChordGroups = getDifficultyChordGroups();
        Map<String,ChordGroup> otherGroupings = createOtherChordGroups(GROUP_NAMES);
        FillChordGroups(difficultyChordGroups,otherGroupings,singleChords);
        setAndCombineMusicItems(difficultyChordGroups,otherGroupings);
        return chordGroupsList;
    }

    private static Map<DifficultyLevel,ChordGroup> getDifficultyChordGroups(){
        //uses name value of enum to set chordGroup name and at the same time sets the enum field
        Map<DifficultyLevel,ChordGroup> difficChordGroups = new TreeMap<>();
        for(DifficultyLevel d : DifficultyLevel.values()){
            String nameAndDiffLevel = d.getStrName();
            ChordGroup cg = new ChordGroup(nameAndDiffLevel);
            cg.setChrdGroupDiffLvl(d);
            difficChordGroups.put(d,cg);
        }
        return difficChordGroups;
    }



    private static Map<String,ChordGroup> createOtherChordGroups(String[] groupNames){
        Map<String,ChordGroup> otherGroups = new TreeMap<>();
        //creates all other chordObjects
        for (String name: groupNames){
            ChordGroup cg = new ChordGroup(name);
            otherGroups.put(cg.getChordGroupName(),cg);
        }
        return otherGroups;
    }


    private static void FillChordGroups(Map<DifficultyLevel,ChordGroup> diffLevelGroups,Map<String,ChordGroup> otherGroups,ArrayList<SingleChord> allChords){
        String[] groupNames = new String[otherGroups.keySet().size()];
        groupNames = otherGroups.keySet().toArray(groupNames);
        //goes through whole individual chordList
        for(SingleChord chord : allChords){
            //adds to difficulty chordGroups
            ChordGroup cGroup = diffLevelGroups.get(chord.getDiffLevel());
            cGroup.addToChordList(chord);
            /* checking individual chord for group belongings, placing in correct group by
              finding group from map
             */
            if(chord.isBarChord()){
                otherGroups.get(groupNames[0]).addToChordList(chord);
            }
            if(chord.isOpenChord()){
                otherGroups.get(groupNames[1]).addToChordList(chord);
            }
            if(chord.isPopularChord()){
                otherGroups.get(groupNames[2]).addToChordList(chord);
            }
        }

        otherGroups.get(groupNames[0]).setChrdGroupDiffLvl(DifficultyLevel.Hard);
        otherGroups.get(groupNames[1]).setChrdGroupDiffLvl(DifficultyLevel.Easy);
        otherGroups.get(groupNames[2]).setChrdGroupDiffLvl(DifficultyLevel.Medium);

        //All groups now sorted and should contain their necessary individual chords
    }

    private static void setAndCombineMusicItems(Map<DifficultyLevel,ChordGroup> difLevelMap, Map<String,ChordGroup> otherGroupMap){
        //combine groups held in difLevels map with otherGroupsMap and all singular Chords
        //desired display order is Diflevel groups, others
        chordGroupsList.addAll(difLevelMap.values());
        chordGroupsList.addAll(otherGroupMap.values());
        attachChordGroupImages();
    }

    private static void attachChordGroupImages(){
        for (ChordGroup cg: chordGroupsList) {
            cg.setImageId();
        }
    }












}
