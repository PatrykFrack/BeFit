package com.frackowiak.befit.other;

import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by PFRACKOW on 16.12.2015.
 */
public class TestMeasurementManager {

    private File measurementsFile;

    private void createDirectory(String directory){
        File file = new File(Environment.getExternalStorageDirectory().getPath() + directory);
        file.mkdirs();
    }

    private void createMeasurementFile(){
        Calendar cal = Calendar.getInstance();
        String fileName = "BeFit_" + cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND) + ".txt";
        measurementsFile = new File(Environment.getExternalStorageDirectory().getPath() + "/BeFit/" + fileName);

        Log.d("BeFit", "Created file: " + Environment.getExternalStorageDirectory().getPath());
    }

    private void saveMeasurementToFile(){
        FileOutputStream outputStream;
        Calendar cal = Calendar.getInstance();
        String content = "Alarm: " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "\n";
        Log.d("TEST_ALARM", "Save to file: " + content);
        try {
            outputStream = new FileOutputStream(measurementsFile, true);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
