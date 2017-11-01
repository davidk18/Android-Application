package com.example.david.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StepCounter extends AppCompatActivity implements SensorEventListener {

    GestureDetectorCompat gestureObject;
    private PendingIntent pendingIntent;
    SensorManager sensorManager;
    Sensor sensor;
    TextView display;
    int steps;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    DatabaseHelper helper;
    LocationManager manager;
    Button resetSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        display = (TextView)findViewById(R.id.stepsCount);
        pref = getSharedPreferences("pedometer", Context.MODE_PRIVATE);
        editor = pref.edit();
        resetSteps = (Button)findViewById(R.id.buttonStepReset);


        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        gestureObject = new GestureDetectorCompat(this, new StepCounter.LearnGesture());

        registerReceiver(
                new ConnectivityChangeReceiver(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));



        resetSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                steps = 0;
                editor.putInt("steps", steps).commit();

                display.setText("" + steps);
            }
        });




    }


    @Override
    protected void onResume(){
        super.onResume();

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            Toast.makeText(this, "No sensor detected", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps = pref.getInt("steps", 0);
        steps++;
        steps = steps + getIntent().getIntExtra("stepDecrease", 0);
        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int lastRecordedDay = pref.getInt("lastRecordedDay", 0);
        if (today != lastRecordedDay){
            steps = 0;
        }
        display.setText("" + steps);

        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        editor.putInt("lastRecordedDay", currentDay).commit();
        editor.putInt("steps", steps).commit();


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float VelocityX, float VelocityY){
            if (event2.getX() > event1.getX()){

                Intent intent = new Intent(StepCounter.this, Calorie.class);
                intent.putExtra("steps", steps);
                startActivity(intent);
            }
            else
            if (event2.getX() < event1.getX()){
                if (isConnectingToInternet(StepCounter.this)) {
                    Intent intent = new Intent(StepCounter.this, LaunchActivity.class);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(StepCounter.this, "No internet connection detected - The exercises information section requires an internet connection to function correctly.", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }
    }


    public boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureObject.onTouchEvent(ev);
    }
}







