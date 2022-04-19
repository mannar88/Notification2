package ru.burdin.notification2.settengs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ru.burdin.notification2.R;
import ru.burdin.notification2.TTS;

public class BattarySettingActivity extends AppCompatActivity   {

    private TTS tts;
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
        preferencesNotifications = PreferencesNotifications.getPreferencesNotifications(this);
        battaryModel = BattaryModel.getBattaryModel(preferencesNotifications.getString(getResources().getString(R.string.key_battary)));
        setContentView(R.layout.activity_battary_setting);
        tts = TTS.getTTS(this);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        listViewLevel = findViewById(R.id.listViewBattarySetting);
        editTextLevel = findViewById(R.id.editTextLevel);
        spinner = findViewById(R.id.spinner);
        listInstalledEngines = tts.getEngines();
        listInstalledEnginesName = new ArrayList<>();
        for (int i = 0; i < listInstalledEngines.size(); i++) {
            listInstalledEnginesName.add(listInstalledEngines.get(i).label);
        }
        arrayAdapterSpiner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listInstalledEnginesName
                );
        arrayAdapterSpiner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapterSpiner);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, battaryModel.getLevels());
        listViewLevel.setAdapter(adapter);
                float prog = battaryModel.getSpeedVoice() * 100;
        seekBarSpeed.setProgress((int) prog);
        seekBarSpeed.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        battaryModel.setSpeedVoice((float) i / 100);
                        preferencesNotifications.save("battary", battaryModel.serialize());
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

    @Override
    protected void onResume() {
        super.onResume();
battaryModel.setEngineInfo(listInstalledEngines.get(spinner.getSelectedItemPosition()));
        //        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
        listViewLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                battaryModel.getLevels().remove(((TextView) itemClicked).getText());
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.level_delete),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onbuttoBattaryTestSpeakClick(View view) {

    battaryModel.setEngineInfo(listInstalledEngines.get(spinner.getSelectedItemPosition()));
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
                tts.speak(text, battaryModel.getEngineInfo().name, battaryModel.getSpeedVoice());
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
                tts.speak("Такой уровень уже имеется", battaryModel.getEngineInfo().name, battaryModel.getSpeedVoice());
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
    preferencesNotifications.save("battary", battaryModel.serialize());
        super.onPause();

    }

    }