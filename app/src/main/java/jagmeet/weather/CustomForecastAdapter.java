package jagmeet.weather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Jagmeet on Dec 4 2016.
 */

public class CustomForecastAdapter extends ArrayAdapter<ForecastItem> {
	private SharedPreferences pref;
	private Context context;
	private static String PREF_NAME;

	public CustomForecastAdapter(Context context, int resourceID, List<ForecastItem> items){
		super(context, resourceID, items);
		this.context = context;
	}

	private class ViewHolder{
		TextView temp;
		TextView weather;
		TextView main;
		TextView date;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		ForecastItem forecastItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null){
			convertView = mInflater.inflate(R.layout.forecast_item, null);

			holder = new ViewHolder();

			holder.temp = (TextView) convertView.findViewById(R.id.temp1);
			holder.weather = (TextView) convertView.findViewById(R.id.WeatherIcon1);
			holder.main	= (TextView) convertView.findViewById(R.id.main1);
			holder.date	= (TextView) convertView.findViewById(R.id.date);

			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		PREF_NAME = "appPreferences";
		pref = getPrefs(context);
		String unit = pref.getString("unitPref", null);
		String append = "";
		if (unit.equals("imperial")){
			append = "° F";
		}
		else if (unit.equals("kelvin")){
			append = "° K";
		}
		else{
			append = "° C";
		}

		holder.temp.setText(forecastItem.getTemp() + append);
		holder.main.setText(forecastItem.getMain());


		Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
		holder.weather.setTypeface(weatherFont);
		int icon = getStringIdentifier(context, "wi_owm_" + forecastItem.getId());
		holder.weather.setText(icon);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, position + 1);
		SimpleDateFormat simpleDate =  new SimpleDateFormat("MMM dd");
		holder.date.setText(simpleDate.format(cal.getTime()));
		return convertView;
	}

	private static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}


	public static int getStringIdentifier(Context context, String name) {
		return context.getResources().getIdentifier(name, "string", context.getPackageName());
	}
}
