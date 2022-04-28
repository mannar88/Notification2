package ru.burdin.notification2.settengs;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.burdin.notification2.TTS;

public class Setting {

    private  boolean checkBox = false;
    private  float speedVoice = 1.0f;
    private List<TextToSpeech.EngineInfo> listInstalledEngines;
private  String engineInfo;
private  transient TTS tts;

public  void  setSpeenner ( Spinner speenner, Adapter adapter){
List <TextToSpeech.EngineInfo> listInstalledEngines = new  ArrayList<>();
    listInstalledEngines = getTts().getEngines();
List     listInstalledEnginesName = new ArrayList<>();
    for (int i = 0; i < listInstalledEngines.size(); i++) {
        listInstalledEnginesName.add(i, listInstalledEngines.get(i).label);
    }
    listInstalledEnginesName.add(0, "Выбрать движок синтезатора речи");
   ArrayAdapter arrayAdapterSpiner = new ArrayAdapter<String>(speenner.getContext(),
            android.R.layout.simple_spinner_item,listInstalledEnginesName
    );
    arrayAdapterSpiner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line );
    speenner.setAdapter(arrayAdapterSpiner);
}

public  void  setSeekBar (SeekBar s ) {
    float prog = speedVoice * 100;
s.setProgress((int)prog);

}
public String getEngineInfo() {
        return engineInfo;
    }

    public TTS getTts() {
        return tts;
    }

    public void setEngineInfo(Context context, Spinner spinner) {
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i != 0) {
                engineInfo = getTts().getEngines().get(i - 1).name;
            load(context);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });

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

    public void setSpeedVoice(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
speedVoice = (float)i / 100;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


    }

public  void  speak (String text) {
    getTts().speak(text, speedVoice);
}



}
