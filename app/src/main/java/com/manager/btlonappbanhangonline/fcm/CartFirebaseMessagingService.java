package com.manager.btlonappbanhangonline.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.manager.btlonappbanhangonline.MainApplication;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.splash.SplashActivity;

import java.util.Map;

public class CartFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
//        RemoteMessage.Notification notification = message.getNotification();
//        if(notification == null) return;;
//        String strTitle = notification.getTitle();
//        String strMessage = notification.getBody();

        Map<String, String> strMap = message.getData();
        if(strMap == null) return;
        String strTitle = strMap.get("user_name");
        String strMessage = strMap.get("description");

        sendNotification(strTitle, strMessage);
    }

    private void sendNotification(String strTitle, String strMessage) {
        @SuppressLint("UnspecifiedImmutableFlag")
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, MainApplication.CHANEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strMessage)
                .setSmallIcon(R.drawable.delivery)
                .setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(manager != null){
            manager.notify(1, notification);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
