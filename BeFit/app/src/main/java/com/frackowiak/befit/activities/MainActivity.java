package com.frackowiak.befit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frackowiak.befit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        int number = sharedPref.getInt("isLogged", 0);
        if(number == 0) {
            //Open the login activity and set this so that next it value is 1 then this conditin will be false.
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            //Open this Home activity
        }
    }
}
