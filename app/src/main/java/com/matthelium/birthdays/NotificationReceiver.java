package com.matthelium.birthdays;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.matthelium.birthdays.db.MyDbManager;

import java.util.Calendar;
import java.util.List;

public class NotificationReceiver extends BroadcastReceiver {
    private MyDbManager myDbManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
    }

    @SuppressLint("MissingPermission")
    private void sendNotification(Context context) {

        myDbManager.openDb();
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DAY_OF_MONTH);

        List<User> myFriendsList = myDbManager.getUsersWithBirthday(Integer.toString(day) + month + Integer.toString(year));
        myDbManager.closeDb();
        for (User friend : myFriendsList) {
            Calendar bur = Calendar.getInstance();
            bur.setTime(friend.birthday);


           if (bur.get(Calendar.MONTH) == today.get(Calendar.MONTH) && bur.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "chanel_id")
                        .setContentTitle("Не забудь поздравить")
                        .setContentText("")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(1, builder.build());

           }
        }
    }
}