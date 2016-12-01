package jagmeet.weather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Jagmeet on Nov 30 2016.
 */

public class AppPreferenceActivity extends PreferenceActivity{
	public final static String PREF_NAME = "appPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(android.R.id.content, new SimpleUserPrefsFragment());

		transaction.commit();
	}

	public static class SimpleUserPrefsFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);

			PreferenceManager preferenceManager = getPreferenceManager();
			preferenceManager.setSharedPreferencesName("appPreferences");
			addPreferencesFromResource(R.xml.preferences);
		}
	}
}
