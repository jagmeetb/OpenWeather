package jagmeet.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private List<String> cityCountryNameList = new ArrayList<String>();
    private List<Integer> idList = new ArrayList<Integer>();
    private List<String> savedCityList = new ArrayList<String>();
    private List<Integer> savedIdList = new ArrayList<Integer>();
    private SharedPreferences savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Add a city");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //  1.  read data from JSON file
        //  2.  load "cityName, Country" into cityCountryNameList
        //  3.  load cities' ID's into idList
        String result = LoadData("city.list.json");
        if (cityCountryNameList.isEmpty()) {
            try {
                JSONObject jsonOBJ = new JSONObject(result);
                JSONArray jsonARR = jsonOBJ.getJSONArray("list");

                int jsonLEN = jsonARR.length();

                for (int i = 0; i < jsonLEN; i++) {
                    JSONObject jsonOBJ2 = jsonARR.getJSONObject(i);

                    String cityName = jsonOBJ2.getString("name");
                    String countryName = jsonOBJ2.getString("country");

                    int id = jsonOBJ2.getInt("_id");
                    cityCountryNameList.add(cityName + ", " + countryName);
                    idList.add(id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //  create ArrayAdapter for editText, and load it with city+country data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cityCountryNameList
        );
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.cityInput);
        textView.setThreshold(3);
        textView.setAdapter(adapter);

        //  load previously selected cities into savedIdList
        savedState = getSharedPreferences("idSaved", MODE_PRIVATE);
        String loadedStringList = savedState.getString("list1", "");
        String[] tempArray = loadedStringList.split(",");
        for (int i = 0;i < tempArray.length; i++){
            try {
                savedIdList.add(Integer.parseInt(tempArray[i]));
            } catch (NumberFormatException e) {
                // do something else, or nothing at all.
            }
        }
    }

    //this function returns a string that contains the input from a read file
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

    /*  onClick function for "Add city" button

        1. ensures user input is a valid city
        2.

    */
    public void citySubmit(View view) {
        int sz = cityCountryNameList.size();
        boolean keepgoing = true;
        boolean alreadySaved = false;
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.cityInput);
        String input = textView.getText().toString();
        for (int i = 0; i < sz && keepgoing; i++){

            if (input.equals(cityCountryNameList.get(i))){
                keepgoing = false;
                for (int j = 0; j < savedIdList.size(); j++){
                    if (savedIdList.get(j).equals(idList.get(i))){
                        alreadySaved = true;
                        j = savedIdList.size();
                    }
                }
                if (!alreadySaved) {
                    savedIdList.add(idList.get(i));
                }
            }
        }

        if (!keepgoing && !alreadySaved){
            Toast.makeText(getBaseContext(), "YOU SELECTED: " + textView.getText(), Toast.LENGTH_SHORT).show();
            Editor editor = savedState.edit();
            int[] x = new int[savedIdList.size()];
            for (int i = 0; i < x.length; i++){
                x[i] = savedIdList.get(i);
            }
            String res = "";
            for (int i = 0; i < x.length; i++){
                if (i < (x.length -1)) {
                    res += String.valueOf(x[i]) + ",";
                }
                else{
                    res += String.valueOf(x[i]);
                }
            }
            editor.putString("list1", res);
            editor.apply();


        }
        else if (!keepgoing && alreadySaved){
            Toast.makeText(getBaseContext(), "YOU'VE ALREADY SAVED: " + textView.getText(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getBaseContext(), "You did not select a valid city",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}