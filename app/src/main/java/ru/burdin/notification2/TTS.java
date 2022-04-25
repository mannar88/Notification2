package ru.burdin.notification2;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ru.burdin.notification2.settengs.BattaryModel;

public   class TTS implements TextToSpeech.OnInitListener {
    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }

    public void setTextToSpeech(TextToSpeech textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    private TextToSpeech textToSpeech;
    public boolean check;
    public int res = -1;
    private BattaryModel battaryModel = BattaryModel.getBattaryModel("battary");

    public TTS(Context context, String engineInfo) {

        if (engineInfo == null) {
            textToSpeech = new TextToSpeech(context, this::onInit);

        } else {
            textToSpeech = new TextToSpeech(context, this::onInit, engineInfo);
        }
            }

    public void reStart(String engineInfo) {
        textToSpeech.setEngineByPackageName(engineInfo);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("ru");
            int result = textToSpeech.setLanguage(locale);


            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            } else {
                check = true;
            }
        }
    }

    public void speak(String text, float speed) {
        if (check) {
            String utteranceId = this.hashCode() + "";
            textToSpeech.setSpeechRate(speed);
            res = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

public void destroy(){
        textToSpeech.shutdown();
    }

    public List<TextToSpeech.EngineInfo> getEngines () {
        return  textToSpeech.getEngines();
}

public Set<Voice> getVoices () {
return textToSpeech.getVoices();
    }

    public  int error () {
        return  textToSpeech.isLanguageAvailable(Locale.getDefault());
    }
}
