package jagmeet.weather;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Jagmeet on Nov 17 2016.
 */

public class MyListFragment extends Fragment{

	private OnDataTransferListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_list, container, false);

		ListView list = (ListView) view.findViewById(R.id.weatherList);

		return view;
	}

	public interface OnDataTransferListener{
		public void onDataTransfer(int cityID);
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);

		if(activity instanceof OnDataTransferListener){
			listener = (OnDataTransferListener) activity;
		}
		else{
			throw new ClassCastException(activity.toString());
		}
	}

	@Override
	public void onDetach(){
		super.onDetach();
		listener = null;
	}
}
