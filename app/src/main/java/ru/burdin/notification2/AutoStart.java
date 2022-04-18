package ru.burdin.notification2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.burdin.notification2.servers.BattaryService;
import ru.burdin.notification2.settengs.BattaryModel;
import ru.burdin.notification2.settengs.PreferencesNotifications;

public class AutoStart extends BroadcastReceiver {
    PreferencesNotifications preferencesNotifications;
    BattaryModel battaryModel;
    public AutoStart () {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        preferencesNotifications = PreferencesNotifications.getPreferencesNotifications(context);
battaryModel = BattaryModel.getBattaryModel(preferencesNotifications.getString(context.getResources().getString(R.string.key_battary)));
if (battaryModel.isCheckBox()) {
    Intent intentStart =                             new Intent(context, BattaryService.class);
    intentStart.putExtra("передача", context.getResources().getString(R.string.server_setup));
context.startService(intentStart );
}
            }
}
