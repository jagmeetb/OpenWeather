package jagmeet.weather;

import android.app.Activity;
import android.content.Context;
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
	private Context context;

	public CustomListAdapter (Context context, int resourceID, List<RowItem> items){
		super(context, resourceID, items);
		this.context = context;
	}

	private class ViewHolder{
		TextView name;
		TextView temp;
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
			holder.main	= (TextView) convertView.findViewById(R.id.main);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);

			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(rowItem.getCityID());
		holder.temp.setText(rowItem.getTemp());
		holder.main.setText(rowItem.getMain());
		holder.desc.setText(rowItem.getDescription());

		return convertView;

	}
}
