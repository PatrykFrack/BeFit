package com.frackowiak.befit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.frackowiak.befit.R;
import com.frackowiak.befit.presenters.ErrandActivityPresenter;

public class ErrandActivity extends AppCompatActivity {

    private ErrandActivityPresenter errandActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WERTZ", "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        setContentView(R.layout.activity_errand);
        Long errandId = getIntent().getExtras().getLong("ErrandId");
        errandActivityPresenter = new ErrandActivityPresenter(this, errandId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        errandActivityPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy(){
        errandActivityPresenter.onDestroy();
        super.onDestroy();
    }

}
