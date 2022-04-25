package ru.burdin.notification2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
if (battaryModel.isCheckBox()) {
    Intent intentStart =                             new Intent(context, BattaryService.class);
    intentStart.putExtra("передача", context.getResources().getString(R.string.server_setup));
context.startService(intentStart );
}
            }
}
