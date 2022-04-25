package ru.burdin.notification2.servers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

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
                if (checkInt != level){
                    check = true;
                }
                if (battaryModel.getLevels().contains(Integer.toString(level)) && check) {
                    String text = getResources().getString(R.string.level_notification) + " " + level + " %";
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
        battaryModel.getTts().speak("конец", 1.0f);
                super.onDestroy();
    }

    @Override
    public void onInit(int i) {

    }
}