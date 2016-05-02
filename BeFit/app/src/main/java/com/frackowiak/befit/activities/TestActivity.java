package com.frackowiak.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frackowiak.befit.R;
import com.frackowiak.befit.ipm.ble.receiver.IBLEListener;
import com.frackowiak.befit.rest.UpdateBeaconsAndErrandsService;
import com.frackowiak.befit.rest.ConnectionService;


public class TestActivity extends AppCompatActivity implements IBLEListener{

    private static final int RSSI_CONST = -50;
    private String testUUID = "F2A74FC4-7625-44DB-9B08-CB7E130B2029";
    private UpdateBeaconsAndErrandsService updateBeaconsAndErrandsService = new UpdateBeaconsAndErrandsService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    private void changeText(String text) {
        TextView textView = (TextView) findViewById(R.id.tvTest);
        textView.setText(text);
    }

    @Override
    public void updateBLEInfo(String info) {
        Log.d("BeFitTest", "info");
    }

    public void onClick(View view) {
        test();
    }

    private void test() {
        startService(new Intent(this, ConnectionService.class));
    }
}