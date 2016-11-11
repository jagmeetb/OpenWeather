package jagmeet.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
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
	private SharedPreferences savedState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		savedState = getSharedPreferences("idSaved", MODE_PRIVATE);
		String x = savedState.getString("list1", "");
		TextView txt = (TextView) findViewById(R.id.message);
	//	txt.setText(String.valueOf(x));
		txt.setText(x);
	}


	public void cityAdd(View view){
		startActivity(new Intent(this, SecondActivity.class));
	}
}