    package ru.burdin.notification2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import ru.burdin.notification2.servers.BattaryService;
import ru.burdin.notification2.settengs.BattaryModel;
import ru.burdin.notification2.settengs.BattarySettingActivity;
import ru.burdin.notification2.settengs.PreferencesNotifications;

    public class MainActivity extends AppCompatActivity  {

    private CheckBox mChechBoxBattary;
    private Button buttonBattery;
    private BattaryService  battaryService;
private BattaryModel battaryModel;
private PreferencesNotifications preferencesNotifications;
@Override    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mChechBoxBattary = findViewById(R.id.checkBoxBattary);
    buttonBattery = findViewById(R.id.buttonSettingBattary);
    preferencesNotifications = PreferencesNotifications.getPreferencesNotifications(this);
    battaryModel = BattaryModel.getBattaryModel(preferencesNotifications.getString(getResources().getString(R.string.key_battary)));

    mChechBoxBattary.setChecked(battaryModel.isCheckBox());
if (savedInstanceState == null) {
    startStopServer();
}
}

        @Override
    protected void onPause() {
                    preferencesNotifications.save("battary", battaryModel.serialize());
            super.onPause();
}

    public void onCheckboxClicked(View view) {
startStopServer();
}

    public void onbuttoSettingBattaryClick(View view) {
Intent intent = new Intent(this, BattarySettingActivity.class);
        startActivity(intent);
}

private  void  startStopServer() {
    if (mChechBoxBattary.isChecked()) {
        Intent intent =                             new Intent(MainActivity.this, BattaryService.class);
        intent.putExtra("передача", getResources().getString(R.string.server_setup));
        startService(intent );
        battaryModel.setCheckBox(true);
    }else {
        battaryModel.setCheckBox(false);
        stopService(
                new Intent(MainActivity.this, BattaryService.class
                ));
    }
    buttonBattery.setEnabled(battaryModel.isCheckBox());

}
    }