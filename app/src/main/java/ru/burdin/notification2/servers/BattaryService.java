package ru.burdin.notification2.servers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import ru.burdin.notification2.R;
import ru.burdin.notification2.TTS;
import ru.burdin.notification2.settengs.BattaryModel;
import ru.burdin.notification2.settengs.PreferencesNotifications;

public class BattaryService extends Service implements TextToSpeech.OnInitListener {


private  int checkInt;
    private BroadcastReceiver broadcastReceiver;
    private  boolean check = true;
    private  PreferencesNotifications preferencesNotifications;
    private  BattaryModel battaryModel;
    private  long time;
    private  int changed;
    public BattaryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
         PreferencesNotifications preferencesNotifications = PreferencesNotifications.getPreferencesNotifications(this);
         battaryModel = BattaryModel.getBattaryModel(preferencesNotifications.getString(getResources().getString(R.string.key_battary)));
         battaryModel.load(getApplicationContext());
                broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra("level", 0);
                if (time == 0) {
                    time = System.currentTimeMillis();
                changed = level;
                }
                if (checkInt != level){
                    check = true;
                }
                if (battaryModel.getLevels().contains(Integer.toString(level)) && check) {
String text = getResources().getString(R.string.level_notification) + " " + level + " %";
if (level < changed) {
Date date = new Date();
date.setTime(time);
battaryModel.log = "старое время " + date.toString();
    date.setTime(System.currentTimeMillis());
battaryModel.log = battaryModel.log + " нсейчас " + date.toString();
battaryModel.log = battaryModel.log + changed + " " + level;
text = text +" этого хватит примерно до: " + battaryModel.time(time, System.currentTimeMillis(), changed, level);
time = System.currentTimeMillis();
changed = level;
}
    String utteranceId = this.hashCode() + "";
                    battaryModel.getTts().speak(text, battaryModel.getSpeedVoice());
                    check = false;
                checkInt = level;
                }
            }
        };

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(
                broadcastReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        );
        Toast.makeText(this, intent.getStringExtra("передача"),
                Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        battaryModel.speak(getResources().getString(R.string.server_finish));
                super.onDestroy();
    }

    @Override
    public void onInit(int i) {

    }
}