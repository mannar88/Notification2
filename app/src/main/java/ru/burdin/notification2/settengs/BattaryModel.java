package ru.burdin.notification2.settengs;

import android.graphics.ColorSpace;

import com.google.gson.Gson;

import java.util.ArrayList;

public final  class BattaryModel {

    private  static    BattaryModel battaryModel;
    private  boolean checkBox = false;
private  float speedVoice = 1.0f;
private ArrayList <String> levels = new ArrayList<>();

    public ArrayList<String> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<String> levels) {
        this.levels = levels;
    }

    public float getSpeedVoice() {
        return speedVoice;
    }

    public void setSpeedVoice(float speedVoice) {
        this.speedVoice = speedVoice;
    }

    private  BattaryModel () {

}

public  static  BattaryModel getBattaryModel(String key) {
if (battaryModel == null) {
    battaryModel = create(key);
}
return  battaryModel;
}

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    private  static  BattaryModel create(String serializedData) {
        BattaryModel battaryModel = new BattaryModel();
        Gson gson = new Gson();
        if (serializedData !=null) {
            battaryModel = gson.fromJson(serializedData, BattaryModel.class);
        }
return  battaryModel;
    }
    }
