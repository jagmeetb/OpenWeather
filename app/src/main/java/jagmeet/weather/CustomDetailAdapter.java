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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Jagmeet on Dec 1 2016.
 */

public class CustomDetailAdapter extends ArrayAdapter<DetailItem> {
	private SharedPreferences pref;
	private Context context;
	private static String PREF_NAME;

	public CustomDetailAdapter (Context context, int resourceID, List<DetailItem> items){
		super(context, resourceID, items);
		this.context = context;
	}

	private class ViewHolder{
		TextView name;
		TextView temp;
		TextView weather;
		TextView main;
		TextView desc;
		TextView min;
		TextView max;
		TextView humidity;
		TextView sunrise;
		TextView sunset;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		DetailItem detailItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null){
			convertView = mInflater.inflate(R.layout.detail_item, null);

			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.cityName);
			holder.temp = (TextView) convertView.findViewById(R.id.temp);
			holder.weather = (TextView) convertView.findViewById(R.id.WeatherIcon);
			holder.main	= (TextView) convertView.findViewById(R.id.main);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);
			holder.min = (TextView) convertView.findViewById(R.id.min);
			holder.max = (TextView) convertView.findViewById(R.id.max);
			holder.humidity = (TextView) convertView.findViewById(R.id.humidity);
			holder.sunrise = (TextView) convertView.findViewById(R.id.sunrise);
			holder.sunset = (TextView) convertView.findViewById(R.id.sunset);

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

		holder.name.setText(detailItem.getCityName());
		holder.temp.setText(detailItem.getTemp() + append);
		holder.main.setText(detailItem.getMain());
		holder.desc.setText(detailItem.getDescription());
		holder.min.setText(detailItem.getMin());
		holder.max.setText(detailItem.getMax());
		holder.humidity.setText(detailItem.getHumidity());

		long sunr = Long.parseLong(detailItem.getSunrise());
		Date date = new Date(sunr * 1000);
		SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("EST"));
		holder.sunrise.setText("Sunrise: " + format.format(date));

		long suns = Long.parseLong(detailItem.getSunset());
		date = new Date(suns * 1000);
		format = new SimpleDateFormat("h:mm a", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("EST"));
		holder.sunset.setText("Sunset: " + format.format(date));


		Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
		holder.weather.setTypeface(weatherFont);
		int icon = getStringIdentifier(context, "wi_owm_" + detailItem.getWeatherID());
		holder.weather.setText(icon);

		return convertView;
	}

	private static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}


	public static int getStringIdentifier(Context context, String name) {
		return context.getResources().getIdentifier(name, "string", context.getPackageName());
	}
}
