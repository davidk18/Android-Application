package com.example.david.myapplication;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Exercises extends AppCompatActivity {
    TextView tvInternet;
    GestureDetectorCompat gestureObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        setTitle("Exercise Information");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gestureObject = new GestureDetectorCompat(this, new Exercises.LearnGesture());
            Button btnSquat = (Button) findViewById(R.id.btnSquat);
            Button btnBench = (Button) findViewById(R.id.btnBench);
            Button btnDeadlift = (Button) findViewById(R.id.btnDeadlift);
            Button btnPushup = (Button) findViewById(R.id.btnPushup);
            Button btnPullup = (Button) findViewById(R.id.btnPullup);
            Button btnPlank = (Button) findViewById(R.id.btnPlank);
            tvInternet = (TextView) findViewById(R.id.tvInternet);
            tvInternet.setMovementMethod(new ScrollingMovementMethod());

            btnSquat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new squatInfo().execute();

                }
            });

            btnBench.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new benchInfo().execute();
                }
            });

            btnDeadlift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new deadliftInfo().execute();
                }
            });

            btnPushup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new pushupInfo().execute();
                }
            });

            btnPullup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new pullupInfo().execute();
                }
            });

            btnPlank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new plankInfo().execute();
                }
            });
    }

    public class squatInfo extends AsyncTask<Void, Void, Void> {
        String words;

        @Override
        protected Void doInBackground(Void... params) {

                try {
                    Document doc = Jsoup.connect("https://www.bodybuilding.com/exercises/detail/view/name/barbell-full-squat").get();
                    words = doc.text();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String indexStart = "This exercise";
            String indexEnd = "bar position.";
            int x = words.indexOf(indexStart);
        int y = words.indexOf(indexEnd);
        y += indexEnd.length();
        tvInternet.setText(words.substring(x, y));

    }
}

public class benchInfo extends AsyncTask<Void, Void, Void> {
    String words;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect("https://www.bodybuilding.com/exercises/detail/view/name/barbell-bench-press-medium-grip").get();
            words = doc.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String indexStart = "Lie back";
        String indexEnd = "barbell at all times.";
        int x = words.indexOf(indexStart);
        int y = words.indexOf(indexEnd);
        y += indexEnd.length();
        tvInternet.setText(words.substring(x, y));
    }
}

public class deadliftInfo extends AsyncTask<Void, Void, Void> {
    String words;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect("https://www.bodybuilding.com/exercises/detail/view/name/barbell-deadlift").get();
            words = doc.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String indexStart = "Approach the bar";
        String indexEnd = "guiding it to the floor.";
        int x = words.indexOf(indexStart);
        int y = words.indexOf(indexEnd);
        y += indexEnd.length();
        tvInternet.setText(words.substring(x, y));

    }
}

public class pushupInfo extends AsyncTask<Void, Void, Void> {
    String words;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect("https://www.bodybuilding.com/exercises/detail/view/name/pushups").get();
            words = doc.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String indexStart = "Lie on the floor face";
        String indexEnd = "the upper chest more.";
        int x = words.indexOf(indexStart);
        int y = words.indexOf(indexEnd);
        y += indexEnd.length();
        tvInternet.setText(words.substring(x, y));

    }
}

public class pullupInfo extends AsyncTask<Void, Void, Void> {
    String words;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect("https://www.bodybuilding.com/exercises/detail/view/name/pullups").get();
            words = doc.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String indexStart = "Grab the pull";
        String indexEnd = "the bar behind the neck.";
        int x = words.indexOf(indexStart);
        int y = words.indexOf(indexEnd);
        y += indexEnd.length();
        tvInternet.setText(words.substring(x, y));

    }
}

public class plankInfo extends AsyncTask<Void, Void, Void> {
    String words;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect("https://www.bodybuilding.com/exercises/detail/view/name/plank").get();
            words = doc.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String indexStart = "Get into a prone position";
        String indexEnd = "an arm or leg can be raised.";
        int x = words.indexOf(indexStart);
        int y = words.indexOf(indexEnd);
        y += indexEnd.length();
        tvInternet.setText(words.substring(x, y));

    }
}

    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

class LearnGesture extends GestureDetector.SimpleOnGestureListener{
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float VelocityX, float VelocityY){
        if (event2.getX() > event1.getX()){
            Intent intent = new Intent(Exercises.this, LaunchActivity.class);
            startActivity(intent);
        }
        else
        if (event2.getX() < event1.getX()){

        }
        return true;
    }
}




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureObject.onTouchEvent(ev);
    }

}