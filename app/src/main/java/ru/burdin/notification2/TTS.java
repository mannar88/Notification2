package ru.burdin.notification2;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public final  class TTS implements TextToSpeech.OnInitListener {
    private  static  TTS tts;
    private    TextToSpeech textToSpeech;
private  boolean check;
    private TTS(Context context) {
        textToSpeech = new TextToSpeech(context, this);
    }

    public static  TTS getTTS(Context context) {
        if (tts == null) {
        tts = new TTS(context);
        }
return  tts;
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

        } else {
            Log.e("TTS", "Не удалось инициализировать движок!");
        }
    }

    public  void speak ( String text, String textToSpeechEngineInfo, float speed) {
        if (textToSpeechEngineInfo == null) {
            textToSpeechEngineInfo = textToSpeech.getDefaultEngine();
        }
        if (check) {
            textToSpeech.setSpeechRate(speed);
            String utteranceId = this.hashCode() + "";
            textToSpeech.setEngineByPackageName(textToSpeechEngineInfo);
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void destroy(){
        textToSpeech.shutdown();
    }
public List<TextToSpeech.EngineInfo> getEngines () {
        return  textToSpeech.getEngines();
}
}
