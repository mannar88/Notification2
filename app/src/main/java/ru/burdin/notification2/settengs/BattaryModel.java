package ru.burdin.notification2.settengs;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
public transient  String log;
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

    public  String  time (long time1, long time2, int i1, int i2) {
        long res = time2 - time1 ;
        long res2 = i1 - i2;
        double res3= (double)(res / res2);
long res4 = (long)(i1 * res3 + time2);
Date date = new Date();
date.setTime(res4);
Format timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
log = res4 + "";
return  timeFormat.format(date);
    }

}
