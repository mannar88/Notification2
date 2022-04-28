package ru.burdin.notification2.settengs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import ru.burdin.notification2.R;
import ru.burdin.notification2.TTS;

public class BattarySettingActivity extends AppCompatActivity  implements  TextToSpeech.OnInitListener {

    private SeekBar seekBarSpeed;
    private BattaryModel battaryModel;
    private PreferencesNotifications preferencesNotifications;
    private BroadcastReceiver broadcastReceiver;
    private ListView listViewLevel;
    private EditText editTextLevel;
    private ArrayAdapter<String> adapter;
private Spinner spinner;
    private  ArrayAdapter arrayAdapterSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battary_setting);
    preferencesNotifications = PreferencesNotifications.getPreferencesNotifications(this);
    battaryModel = BattaryModel.getBattaryModel(preferencesNotifications.getString(getResources().getString(R.string.key_battary)));
    seekBarSpeed = findViewById(R.id.seekBarSpeed);
        listViewLevel = findViewById(R.id.listViewBattarySetting);
        editTextLevel = findViewById(R.id.editTextLevel);
        spinner = findViewById(R.id.spinner);
        TextView textView = findViewById(R.id.speed);
//textView.setText(battaryModel.time((System.currentTimeMillis() -TimeUnit.MINUTES.toMillis(11)), System.currentTimeMillis(), 93, 92));
//        textView.setText(battaryModel.log);
        battaryModel.setSpeenner(spinner, arrayAdapterSpiner);
adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, battaryModel.getLevels());
        listViewLevel.setAdapter(adapter);
battaryModel.setSeekBar(seekBarSpeed);
}

    @Override
    protected void onResume() {
        super.onResume();
battaryModel.setEngineInfo(getApplicationContext(), spinner);
battaryModel.setSpeedVoice(seekBarSpeed);
    }


    public void onbuttoBattaryTestSpeakClick(View view) {
        speakTest();
        registerReceiver(
                        broadcastReceiver,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                );


}

    private void speakTest() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra("level", 0);
                String text = getResources().getString(R.string.level_notification) + " " + level + " %";
battaryModel.speak(text);
                unregisterReceiver(broadcastReceiver);
            }
        };

    }

    public void onbuttoBattarySaveLevelClick(View view) {
            String res = editTextLevel.getText().toString();
        if (checkInt(res)) {
            if (!battaryModel.getLevels().contains(res)) {
                battaryModel.getLevels().add(res);
                battaryModel.getLevels().sort(new Comparator<String>() {

                    @Override
                    public int compare(String s, String t1) {
                        return Integer.compare(Integer.parseInt(s), Integer.parseInt(t1));
                    }
                });
                adapter.notifyDataSetChanged();
                editTextLevel.setText("");
            } else {
                battaryModel.getTts().speak("Такой уровень уже имеется", battaryModel.getSpeedVoice());

            }
        } else {

        }
    }

    private boolean checkInt(String string) {
        boolean res = true;
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            res = false;
            editTextLevel.setText("");
            Toast.makeText(getApplicationContext(), "Не верное значение",
                    Toast.LENGTH_SHORT).show();
        }
        return res;
    }

    @Override
    protected void onPause() {
    preferencesNotifications.save(getResources().getString(R.string.key_battary), battaryModel.serialize());
        super.onPause();

    }

    @Override
    public void onInit(int i) {
        Toast.makeText(getApplicationContext(), Integer.toString(i),
                Toast.LENGTH_SHORT).show();
}
}
