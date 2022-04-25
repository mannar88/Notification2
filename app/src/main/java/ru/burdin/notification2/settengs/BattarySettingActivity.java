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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    private List<TextToSpeech.EngineInfo> listInstalledEngines;
    private  List <String> listInstalledEnginesName;

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
        listInstalledEngines = battaryModel.getTts().getEngines();
        listInstalledEnginesName = new ArrayList<>();
    for (int i = 0; i < listInstalledEngines.size(); i++) {
        listInstalledEnginesName.add(i, listInstalledEngines.get(i).label);
    }
    listInstalledEnginesName.add(0, "Выбрать движок синтезатора речи");
    arrayAdapterSpiner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listInstalledEnginesName
                );
arrayAdapterSpiner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line );
spinner.setAdapter(arrayAdapterSpiner);
adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, battaryModel.getLevels());
        listViewLevel.setAdapter(adapter);
                float prog = battaryModel.getSpeedVoice() * 100;
        seekBarSpeed.setProgress((int) prog);

}

    @Override
    protected void onResume() {
        super.onResume();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    battaryModel.setEngineInfo(listInstalledEngines.get(i - 1).name);
                    battaryModel.load(getApplicationContext());
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        seekBarSpeed.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        battaryModel.setSpeedVoice((float) i / 100);

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
                String utteranceId = this.hashCode() + "";
        battaryModel.getTts().speak(text, battaryModel.getSpeedVoice());
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
