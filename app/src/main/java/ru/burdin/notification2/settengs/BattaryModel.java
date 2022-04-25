package ru.burdin.notification2.settengs;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.google.gson.Gson;

import java.util.ArrayList;

import ru.burdin.notification2.TTS;

public final  class BattaryModel  extends  Setting{

    private  static    BattaryModel battaryModel;
    private ArrayList <String> levels = new ArrayList<>();
public ArrayList<String> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<String> levels) {
        this.levels = levels;
    }

    private  BattaryModel () {
}

public  static  BattaryModel getBattaryModel(String key) {
if (battaryModel == null) {
    battaryModel = create(key);
}
return  battaryModel;
}

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    private  static  BattaryModel create( String serializedData) {
        BattaryModel battaryModel = new BattaryModel();
        Gson gson = new Gson();
        if (serializedData !=null) {
            battaryModel = gson.fromJson(serializedData, BattaryModel.class);
        }
return  battaryModel;
    }

    @Override
    public void setEngineInfo(String engineInfo) {
        super.setEngineInfo(engineInfo);
    }

    @Override
    public String getEngineInfo() {
        return super.getEngineInfo();
    }
}
