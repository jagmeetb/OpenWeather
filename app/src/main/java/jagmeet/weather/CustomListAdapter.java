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

import java.util.List;

/**
 * Created by Jagmeet on Nov 17 2016.
 */

public class CustomListAdapter extends ArrayAdapter<RowItem>{
	private SharedPreferences pref;
	private Context context;
	private static String PREF_NAME;

	public CustomListAdapter (Context context, int resourceID, List<RowItem> items){
		super(context, resourceID, items);
		this.context = context;
	}

	private class ViewHolder{
		TextView name;
		TextView temp;
		TextView weather;
		TextView main;
		TextView desc;

	}

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;

		RowItem rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null){
			convertView = mInflater.inflate(R.layout.list_item, null);

			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.cityName);
			holder.temp = (TextView) convertView.findViewById(R.id.temp);
			holder.weather = (TextView) convertView.findViewById(R.id.WeatherIcon);
			holder.main	= (TextView) convertView.findViewById(R.id.main);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);

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

		holder.name.setText(rowItem.getCityName());
		holder.temp.setText(rowItem.getTemp() + append);
		holder.main.setText(rowItem.getMain());
		holder.desc.setText(rowItem.getDescription());

		Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
		holder.weather.setTypeface(weatherFont);
		int icon = getStringIdentifier(context, "wi_owm_" + rowItem.getWeatherID());
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
