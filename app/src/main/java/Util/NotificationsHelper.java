package Util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.restaurantupdated.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import view.OrderFragment;

public
class NotificationsHelper {


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Context context;
    String minutesToWait;

    public NotificationsHelper(Context context, String minutesToWait) {
        this.context = context;
        this.minutesToWait = minutesToWait;

    }

    public void createNotification() {
        if (minutesToWait.equals(Constants.ZERO))
            return;
        createNotificationChannel();

        Intent intent = new Intent(context, OrderFragment.class);
        //flags?
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        //image?
        Notification notification = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Constants.THANK_YOU + user.getEmail() + Constants.EXCLAMATION)
                .setContentText(Constants.ANNOUNCEMENT + minutesToWait + Constants.MIN)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build();

        NotificationManagerCompat.from(context).notify(123, notification);

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = Constants.CHANNEL_ID;
            String description = Constants.CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(name, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }


    }
}
