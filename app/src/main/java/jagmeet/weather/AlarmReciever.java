package jagmeet.weather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;

/**
 * Created by Jagmeet on Dec 3 2016.
 */

public class AlarmReciever extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		ArrayList<String> list;
		list = intent.getStringArrayListExtra("list");
		String msg = "";

		for (int i = 0; i < list.size(); i++){
			msg += list.get(i) + "\n";
		}

		Intent notificationIntent = new Intent(context, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(notificationIntent);

		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		Notification notification = builder.setContentTitle("Weather Report")
				.setContentText(list.get(0))
				.setTicker("New message!")
				.setSmallIcon(R.mipmap.ic_launcher)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentIntent(pendingIntent).build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
}
