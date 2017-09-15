package com.example.twishisagwal.gyroscope;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends Activity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private int flag = 1;
    private int Flag = 1;
    FileWriter fos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create our Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);


        Button btn = (Button)findViewById(R.id.button);
        Button btnn = (Button)findViewById(R.id.button2);



            // Assign TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                Log.v("Merged", "Media mounted");
                File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                Log.v("Merged", "ExternalStoragePublicDirectory");
                File file = new File(f, "gyroscope.csv");
                Log.v("Merged", "created");
                fos = null;
                try {
                    fos = new FileWriter(file);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag = 2;

            }
        });

        btnn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag = 1;
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (flag == 2) {
            xText.setText("X: " + event.values[0]);
            yText.setText("Y: " + event.values[1]);
            zText.setText("Z: " + event.values[2]);


            if (event.values[0] > 8.0f && event.values[1] < 8.0f && event.values[2] < 8.0f) {
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            }
            if (event.values[0] < 8.0f && event.values[1] > 8.0f) {
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            }
            if (event.values[0] < 8.0f && event.values[2] > 8.0f) {
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            }
        }


                try {
                    fos.write(event.values[0] + "," + event.values[1] + "," + event.values[2] + "\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
