package com.example.david.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Calorie extends AppCompatActivity {
    GestureDetectorCompat gestureObject;
    public int dailyCalories;
    TextView enteredCals;
    public int calories;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);
        setTitle("Calorie Counter");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonCaloriesReset, buttonCaloriesSubmit;
        final EditText edit;
        final TextView text;
        edit = (EditText)findViewById(R.id.etCalories);
        text = (TextView)findViewById(R.id.dailyCalories);
        pref = getSharedPreferences("calories", Context.MODE_PRIVATE);
        editor = pref.edit();
        enteredCals = (TextView)findViewById(R.id.etCalories);
        buttonCaloriesReset = (Button)findViewById(R.id.buttonCaloriesReset);
        buttonCaloriesSubmit = (Button)findViewById(R.id.buttonCaloriesSubmit);

        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int lastRecordedDay = pref.getInt("lastRecordedDay", 0);
        if (today != lastRecordedDay){
            dailyCalories = 0;
            editor.putInt("cals", 0);
        }


        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        editor.putInt("lastRecordedDay", currentDay).commit();

        dailyCalories = pref.getInt("cals", dailyCalories);
        text.setText(Integer.toString(dailyCalories));


        int steps = getIntent().getIntExtra("steps", 0);
        editor.putInt("steps", steps);
        editor.commit();



        TextView dailyCaloriesBurned = (TextView)findViewById(R.id.dailyCaloriesBurned);
        TextView netCalories = (TextView)findViewById(R.id.netCalories);
        int caloriesBurnedFromSteps = pref.getInt("steps", 0) / 20;
        dailyCalories = pref.getInt("cals", 0);



        updateInfo(dailyCaloriesBurned, netCalories, caloriesBurnedFromSteps, dailyCalories);



        buttonCaloriesSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEmpty(edit)) {

                        TextView dailyCaloriesBurned = (TextView)findViewById(R.id.dailyCaloriesBurned);
                        TextView netCalories = (TextView)findViewById(R.id.netCalories);


                        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
                        int lastRecordedDay = pref.getInt("lastRecordedDay", 0);
                        if (today != lastRecordedDay){
                            dailyCalories = 0;
                            editor.putInt("cals", dailyCalories).commit();
                        }

                        calories = Integer.valueOf(edit.getText().toString());
                        dailyCalories += calories;
                        text.setText(" " + dailyCalories);
                        editor.putInt("cals", dailyCalories).commit();

                        int caloriesBurnedFromSteps = pref.getInt("steps", 0) / 20;
                        updateInfo(dailyCaloriesBurned, netCalories, caloriesBurnedFromSteps, dailyCalories);
                    }
                    else{
                        Toast.makeText(Calorie.this, "Please enter a number for your caloric intake", Toast.LENGTH_LONG).show();
                    }


                }
            });

        buttonCaloriesReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TextView dailyCaloriesBurned = (TextView)findViewById(R.id.dailyCaloriesBurned);
                    TextView netCalories = (TextView)findViewById(R.id.netCalories);

                    dailyCalories = 0;
                    text.setText(" " + dailyCalories);
                    editor.putInt("cals", dailyCalories).commit();

                    int caloriesBurnedFromSteps = pref.getInt("steps", 0) / 20;
                    updateInfo(dailyCaloriesBurned, netCalories, caloriesBurnedFromSteps, dailyCalories);



            }
        });



        gestureObject = new GestureDetectorCompat(this, new Calorie.LearnGesture());

    }

    public void updateInfo(TextView dailyCaloriesBurned, TextView netCalories, int caloriesBurnedFromSteps, int dailyCalories){
        int netCaloriesOutput = dailyCalories - caloriesBurnedFromSteps;
        netCalories.setText(netCaloriesOutput + " ");
        dailyCaloriesBurned.setText("" + caloriesBurnedFromSteps);
    }


    public void getCalories(View v){

    }

    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float VelocityX, float VelocityY){
            if (event2.getX() > event1.getX()){

            }
            else
            if (event2.getX() < event1.getX()){

                Intent intent = new Intent(Calorie.this, StepCounter.class);
                intent.putExtra("stepDecrease", -1);
                startActivity(intent);
            }
            return true;
        }
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureObject.onTouchEvent(ev);
    }


}
