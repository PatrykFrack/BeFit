package com.frackowiak.befit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frackowiak.befit.activities.ErrandActivity;
import com.frackowiak.befit.activities.TestActivity;

public class TestMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View view){
        Button tv = (Button) view;
        Intent intent;
        switch (tv.getId()){
            case R.id.tbtn1:
                startErrand();
                break;
            case R.id.tbtn2:
                intent = new Intent(this, ErrandActivity.class);
                break;
            case R.id.tbtn3:
                intent = new Intent(this, ErrandActivity.class);
                break;
            case R.id.tbtn4:
                intent = new Intent(this, TestActivity.class);
                break;

        }
    }

    private void startErrand(){
        Intent intent = new Intent(this, ErrandActivity.class);
        intent.putExtra("ErrandId", 1l);
        startActivity(intent);
    }

}
