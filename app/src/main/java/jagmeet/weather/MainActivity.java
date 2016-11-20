package jagmeet.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends AppCompatActivity /*implements MyListFragment.OnDataTransferListener */{
	private SharedPreferences savedState;
	private List<RowItem> rowItems;
	private ListView listView;
	private String apiKey = "4344d513b3eb2b292a658049c6cccf5d";
	private static Context appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appContext = this;
		savedState = getSharedPreferences("idSaved", MODE_PRIVATE);
		String x = savedState.getString("list1", "");
		TextView txt = (TextView) findViewById(R.id.message);
	//	txt.setText(String.valueOf(x));
		txt.setText(x);

		rowItems = new ArrayList<RowItem>();


/*
		try {
			String s = readJSONFeed("http://api.openweathermap.org/data/2.5/group?id=" + x + "&units=metric&APPID=" +
					apiKey);
			txt.setText(s);
		}
		catch (Exception e){	}*/

		new ReadJSONFeedTask().execute(

				// API key is required
				"http://api.openweathermap.org/data/2.5/group?id=" + x + "&units=metric&APPID=" +
						apiKey
		);

		//create arraylist

	}

	/*
		TODO	implement fragment

	@Override
	public void onDataTransfer(int cityID){

	}
	*/

	public void cityAdd(View view){
		startActivity(new Intent(this, SecondActivity.class));
	}


	private String readJSONFeed( String urlString ) {

		StringBuilder stringBuilder = new StringBuilder();

		// 1. HTTP processing
		//    - connect to a web service by using the HTTP GET method

		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet( urlString );

		try {


			Log.d("JSON", "HTTPClinet: execute " + urlString);
			HttpResponse response = client.execute( httpGet );

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();

			if (statusCode == 200) {

				HttpEntity entity = response.getEntity();

				InputStream content = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader( content ) );

				String line;

				// 2. Build the JSON string
				while ((line = reader.readLine()) != null) {

					stringBuilder.append(line);
				}
			} else {
				Log.d( "JSON", "Failed to download file" );
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.e ("JSON", stringBuilder.toString() );
		return stringBuilder.toString();
	}

	private class ReadJSONFeedTask extends AsyncTask<String, List<RowItem>, String> { // gerneric types: <...>

		// 1. invoked on the background thread (i.e. asynchronous processing)!
		protected String doInBackground( String... urls ) {
			return readJSONFeed( urls[0] ); // get the JSON string by web service and return it
		}

		// 2. post-processing
		// - result: the JSON string returned by doInBackground( )
		// - invoked on the UI thread
		protected void onPostExecute( String result ) {
			try {
				Log.d( "JSON", result );

        		/* a) parsing of the JSON file sent from OpenWeatherMap
        		      - JSONObject, JSONArray ( {...}, [...] )
        		 */
				JSONObject jsonObject = new JSONObject( result ); // {

				JSONArray jsonArray = jsonObject.getJSONArray( "list" ); // "list":[
				Log.d("JSON", "test1");
				for (int i = 0; i < jsonArray.length(); i++) {

					String name, temp, main, desc;

					JSONObject jsonObject2 = jsonArray.getJSONObject(i); // {
					name = jsonObject2.getString("name");   // "name":
					JSONObject jsonObject4 = jsonObject2.getJSONObject( "main" );   // "main":{
					temp = jsonObject4.getString( "temp" );           // "temp":
					JSONArray jsonArray1 = jsonObject2.getJSONArray("weather");
					JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
					main = jsonObject3.getString("main");
					desc = jsonObject3.getString("description");
					RowItem item = new RowItem(name, temp, main, desc);
					Log.d("JSON", name);
					Log.d("JSON", temp);
					Log.d("JSON", main);
					Log.d("JSON", desc);
					rowItems.add(item);
					Log.d("JSON", String.valueOf(rowItems.size()));
				} // end for

				listView = (ListView) findViewById(R.id.weList);
				CustomListAdapter adapter = new CustomListAdapter(appContext, R.layout.list_item, rowItems);
				listView.setAdapter(adapter);
				//txt.setText(String.valueOf(rowItems.size()));

			} catch ( Exception e ) { Log.d( "JSON", e.toString() ); }


		}
	} // end class ReadJSONFeedTask
}