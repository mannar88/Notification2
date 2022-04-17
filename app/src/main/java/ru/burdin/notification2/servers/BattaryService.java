package ru.burdin.notification2.servers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import ru.burdin.notification2.TTS;
import ru.burdin.notification2.settengs.BattaryModel;

public class BattaryService extends Service implements TextToSpeech.OnInitListener {
    private TTS textToSpeech;
    private BroadcastReceiver broadcastReceiver;
    private  int check;
    private BattaryModel battaryModel;

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
        textToSpeech = new TTS(this);
        battaryModel = BattaryModel.getBattaryModel("battary");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra("level", 0);
                if (battaryModel.getLevels().contains(Integer.toString(level)) && level != check) {
                    String text = "Заряд аккамулятора  " + level + " %";
                    String utteranceId = this.hashCode() + "";
                    textToSpeech.speak(text, battaryModel.getSpeedVoice());
                    check = level;
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
        textToSpeech.speak("Служба выключена", battaryModel.getSpeedVoice());
        unregisterReceiver(broadcastReceiver);
                super.onDestroy();
    }

    @Override
    public void onInit(int i) {

    }
}