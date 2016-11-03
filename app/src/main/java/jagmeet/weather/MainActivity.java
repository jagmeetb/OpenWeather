package jagmeet.weather;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String result = LoadData("city.list.json");
		List<String> finalResult = new ArrayList<String>();
		List<Integer> idList = new ArrayList<Integer>();
		try {
			JSONObject jsonOBJ = new JSONObject(result);
			JSONArray jsonARR = jsonOBJ.getJSONArray("list");

			int jsonLEN = jsonARR.length();

			for (int i = 0; i < jsonLEN; i++){
				JSONObject jsonOBJ2 = jsonARR.getJSONObject(i);

				String cityName = jsonOBJ2.getString("name");
				String countryName = jsonOBJ2.getString("country");

				int id = jsonOBJ2.getInt("_id");
				finalResult.add(cityName + ", " + countryName);
			}
			String[] s = new String[finalResult.size()];
			finalResult.toArray(s);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					this,
					android.R.layout.simple_dropdown_item_1line,
					finalResult
			);

			AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.cityInput);
			textView.setThreshold(3);
			textView.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String LoadData(String inFile) {
		String tContents = "";

		try {
			InputStream stream = getAssets().open(inFile);

			int size = stream.available();
			byte[] buffer = new byte[size];
			stream.read(buffer);
			stream.close();
			tContents = new String(buffer);
		} catch (IOException e) {
			// Handle exceptions here
		}

		return tContents;
	}

	public void citySubmit(View view){
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.cityInput);

		Toast.makeText(getBaseContext(), "YOU SELECTED: " + textView.getText(), Toast.LENGTH_LONG).show();
	}
}