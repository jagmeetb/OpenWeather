package jagmeet.weather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;

/**
 * Created by Jagmeet on Nov 30 2016.
 */

public class AppPreferenceActivity extends PreferenceActivity{
	public final static String PREF_NAME = "appPreferences";
	private static Context context;
	private static Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(android.R.id.content, new SimpleUserPrefsFragment());

		transaction.commit();
		context = this;
		intent = getIntent();

	}

	public static class SimpleUserPrefsFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);

			PreferenceManager preferenceManager = getPreferenceManager();
			preferenceManager.setSharedPreferencesName("appPreferences");
			addPreferencesFromResource(R.xml.preferences);

			Preference myPref = (Preference) findPreference("testNotif");
			myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
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

					Notification notification = builder.setContentTitle("Demo")
							.setContentText(list.get(0))
							.setTicker("New message!")
							.setSmallIcon(R.mipmap.ic_launcher)
							.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
							.setContentIntent(pendingIntent).build();

					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(0, notification);
					return true;
				}
			});

		}
	}
}
