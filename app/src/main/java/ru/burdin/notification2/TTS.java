package ru.burdin.notification2;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener {
    private  TextToSpeech textToSpeech;
private  boolean check;
    public TTS(Context context) {
        textToSpeech = new TextToSpeech(context, this::onInit);
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
//                sayWords();
            }

        } else {
            Log.e("TTS", "Не удалось инициализировать движок!");
        }
    }

    public  void speak ( String text, float speed) {
        if (check) {
            textToSpeech.setSpeechRate(speed);
            String utteranceId = this.hashCode() + "";
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void destroy(){
        textToSpeech.shutdown();
    }
}
