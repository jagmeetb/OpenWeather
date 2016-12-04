package jagmeet.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jagmeet on Nov 17 2016.
 */

public class DetailActivity extends AppCompatActivity{
    private String cityID;
    private String cityName;
    private String weatherID;
    private String temp;
    private String main;
    private String description;
	private List<DetailItem> detailItem;
	private List<ForecastItem> forecastItems;

	int passedID;
	String passedName;

	private SharedPreferences savedState;
	private SharedPreferences prefs;
	private List<Integer> idList;
	private ListView listView;
	private ListView listView2;
	private String apiKey = "4344d513b3eb2b292a658049c6cccf5d";
	private static Context appContext;
	Typeface weatherFont;

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_detail);

        //TextView textView = (TextView) findViewById(R.id.debug);
        Intent intent = getIntent();
        passedID = intent.getIntExtra("cityID", 0);
		passedName = intent.getStringExtra("cityName");
        //textView.setText(String.valueOf(passedID));

		appContext = this;
		weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");

		getSupportActionBar().setTitle(passedName);
    }

	@Override
	protected void onPause(){
		super.onPause();
	}

	@Override
	protected void onResume(){
		super.onResume();

		idList = new ArrayList<Integer>();
		idList.add(passedID);

		detailItem = new ArrayList<DetailItem>();
		forecastItems = new ArrayList<ForecastItem>();
		SharedPreferences sp = getSharedPreferences("appPreferences", MODE_PRIVATE);
		String preftest = sp.getString("unitPref", null);

		new DetailActivity.ReadJSONFeedTask().execute(
				// API key is required
				"http://api.openweathermap.org/data/2.5/group?id=" + String.valueOf(passedID) + "&units=" + preftest + "&APPID=" +
						apiKey
		);

		new DetailActivity.ReadJSONFeedTask2().execute(
				// API key is required
				"http://api.openweathermap.org/data/2.5/forecast?id=" + String.valueOf(passedID) + "&units=" + preftest + "&APPID=" +
						apiKey
		);
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

				JSONObject jsonObject = new JSONObject( result ); // {


				JSONArray jsonArray = jsonObject.getJSONArray( "list" );
				String id, name, wID, temp, main, desc, min, max, humidity, sunrise, sunset;

				JSONObject jsonObject2 = jsonArray.getJSONObject(0); // {
				name = jsonObject2.getString("name");   // "name":
				id = jsonObject2.getString("id");
				JSONObject jsonObject4 = jsonObject2.getJSONObject( "main" );   // "main":{
				temp = jsonObject4.getString( "temp" );           // "temp":
				min = jsonObject4.getString("temp_min");
				max = jsonObject4.getString("temp_max");
				humidity = jsonObject4.getString("humidity");
				JSONArray jsonArray1 = jsonObject2.getJSONArray("weather");
				JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
				wID = jsonObject3.getString("id");
				main = jsonObject3.getString("main");
				desc = jsonObject3.getString("description");
				JSONObject jsonObject1 = jsonObject2.getJSONObject("sys");
				sunrise = jsonObject1.getString("sunrise");
				sunset = jsonObject1.getString("sunset");
				Log.d( "JSON:sunrise", sunrise );
				Log.d( "JSON:sunset", sunset );
				DetailItem item = new DetailItem(id, name, temp, min, max, humidity, wID, main, desc, sunrise, sunset);
				detailItem.add(item);

				listView = (ListView) findViewById(R.id.detail);
				CustomDetailAdapter adapter = new CustomDetailAdapter(appContext, R.layout.detail_item, detailItem);
				listView.setAdapter(adapter);
				listView.setClickable(false);


			} catch ( Exception e ) { Log.d( "JSON", e.toString() ); }


		}
	} // end class ReadJSONFeedTask

	private class ReadJSONFeedTask2 extends AsyncTask<String, List<RowItem>, String> { // gerneric types: <...>

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

				JSONObject jsonObject = new JSONObject( result ); // {


				JSONArray jsonArray = jsonObject.getJSONArray( "list" );
				int sz = jsonArray.length();
				int start = sz%8;
				for (int i = start; i < sz; i+= 8) {
					String id, main, temp, desc;

					JSONObject jsonObject2 = jsonArray.getJSONObject(i); // {
					JSONObject jsonMain = jsonObject2.getJSONObject("main");
					temp = jsonMain.getString("temp");   // "name":
					Log.d("FORECAST TEMP", temp);
					JSONArray jsonWeather = jsonObject2.getJSONArray("weather");
					JSONObject jsonWeather1 = jsonWeather.getJSONObject(0);
					id = jsonWeather1.getString("id");
					Log.d("FORECAST ID", id);
					main = jsonWeather1.getString("main");
					desc = jsonWeather1.getString("description");
					ForecastItem item = new ForecastItem(id, main, desc, temp);
					forecastItems.add(item);
				}
				listView2 = (ListView) findViewById(R.id.forecast);
				CustomForecastAdapter adapter = new CustomForecastAdapter(appContext, R.layout.forecast_item, forecastItems);
				listView2.setAdapter(adapter);



			} catch ( Exception e ) { Log.d( "JSON", e.toString() ); }


		}
	} // end class ReadJSONFeedTask

	public static int getStringIdentifier(Context context, String name) {
		return context.getResources().getIdentifier(name, "string", context.getPackageName());
	}
}
