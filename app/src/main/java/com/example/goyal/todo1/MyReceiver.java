package com.example.goyal.todo1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getExtras() != null){

            NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("mychannelid","Expenses Channel", NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);

            }

            long id = intent.getLongExtra("id",-1);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(),"mychannelid");
            builder.setContentTitle("Alarm");
            builder.setContentText("Alarm Received");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);

            Intent intent1 = new Intent(context.getApplicationContext(),MainActivity.class);
            intent1.putExtra("id",id);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),2,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            manager.notify(1,notification);
        }


    }
}
