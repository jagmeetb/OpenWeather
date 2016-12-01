package jagmeet.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_detail);

        TextView textView = (TextView) findViewById(R.id.debug);
        Intent intent = getIntent();
        int id = intent.getIntExtra("cityID", 0);
        textView.setText(String.valueOf(id));
    }
}
