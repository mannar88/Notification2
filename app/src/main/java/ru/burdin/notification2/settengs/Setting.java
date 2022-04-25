package ru.burdin.notification2.settengs;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;

import java.util.HashSet;
import java.util.Set;

import ru.burdin.notification2.TTS;

public class Setting {

    private  boolean checkBox = false;
    private  float speedVoice = 1.0f;

    private  String engineInfo;
private  transient TTS tts;
public   transient Set<Voice> voices = new HashSet<>();



    public String getEngineInfo() {
        return engineInfo;
    }

    public TTS getTts() {
        return tts;
    }

    public void setEngineInfo(String engineInfo) {
        this.engineInfo = engineInfo;
    }

public   void  load (Context context) {
    tts = new TTS(context, engineInfo);
}

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public float getSpeedVoice() {
        return speedVoice;
    }

    public void setSpeedVoice(float speedVoice) {
        this.speedVoice = speedVoice;
    }




}
