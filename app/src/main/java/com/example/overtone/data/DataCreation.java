package com.example.overtone.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DataCreation {

 static Gson gson;
 static ArrayList<ChordGroup> chordGroupsList = new ArrayList<>();
 static final String[] GROUP_NAMES = {"Bar","Open","Popular"};

    public static ArrayList<ChordGroup> getCreatedGroups(Context cntx){
        ArrayList<SingularMusicItemDm> allChords = getAllChords(cntx);
        Map<DifficultyLevel,ChordGroup> difficultyChordGroups = getDifficultyChordGroups();
        Map<String,ChordGroup> otherGroupings = createOtherChordGroups(GROUP_NAMES);
        FillChordGroups(difficultyChordGroups,otherGroupings,allChords);
        setAndCombineMusicItems(difficultyChordGroups,otherGroupings);
        return chordGroupsList;
    }

    private static ArrayList<SingularMusicItemDm> getAllChords(Context context){
        String jsonPath = "chord.json";
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(context,jsonPath);
        gson = new Gson();
        ArrayList<SingularMusicItemDm> singularMusicItemDms = gson.fromJson(jsonString, new TypeToken<ArrayList<SingularMusicItemDm>>(){}.getType());
        return singularMusicItemDms;
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


    private static void FillChordGroups(Map<DifficultyLevel,ChordGroup> diffLevelGroups,Map<String,ChordGroup> otherGroups,ArrayList<SingularMusicItemDm> allChords){
        String[] groupNames = new String[otherGroups.keySet().size()];
        groupNames = otherGroups.keySet().toArray(groupNames);
        //goes through whole individual chordList
        for(SingularMusicItemDm chord : allChords){
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
