package com.example.david.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;



public class LaunchActivity extends AppCompatActivity {

    GestureDetectorCompat gestureObject;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gestureObject = new GestureDetectorCompat(this, new LaunchActivity.LearnGesture());

    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float VelocityX, float VelocityY){
            if (event2.getX() > event1.getX()){

                Intent intent = new Intent(LaunchActivity.this, StepCounter.class);
                intent.putExtra("stepDecrease", -1);
                startActivity(intent);
            }
            else
            if (event2.getX() < event1.getX()){
                if (isConnectingToInternet(LaunchActivity.this)) {
                    Intent intent = new Intent(LaunchActivity.this, Exercises.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LaunchActivity.this, "No internet connection detected - The exercises information section requires an internet connection to function correctly.", Toast.LENGTH_LONG).show();
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
