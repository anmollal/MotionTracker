package com.example.anmol.motiontracker;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LeftThumbActivity extends AppCompatActivity {

    List<LeftThumbActivity.ScreenLoc> swipe;
    //String[] states = {"Right Thumb", "Left Thumb", "Any Finger"};
    //String currentState;
    //Random rand;
    Display display;
    Point size;
    float height;
    long startTime;
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_thumb);
        Button button = (Button) findViewById(R.id.lthumb_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(LeftThumbActivity.this, LeftIndexActivity.class));
            }
        });

        // rand = new Random();
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        height = (float) size.y;
        genState();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            swipe = new ArrayList<>();
            startTime = System.currentTimeMillis();
        }
        Log.i("X, Y", Float.toString(ev.getX(0)) + " , " + Float.toString(ev.getY(0)));
        swipe.add(new LeftThumbActivity.ScreenLoc(ev.getX(0), height - ev.getY(0)));
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            endTime = System.currentTimeMillis();
            File file = new File(this.getExternalFilesDir("SwipeData"), "LeftThumbSwipeData.csv");
            try {
                file.createNewFile();
                FileOutputStream stream = new FileOutputStream(file, true);
                stream.write(swipeString(swipe).getBytes());
                stream.flush();
                stream.close();
            } catch (IOException e) {
                Log.d("error", e.getMessage());
            }
            genState();
        }
        return true;
    }

    private class ScreenLoc {
        float x;
        float y;

        ScreenLoc(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private String swipeString(List<LeftThumbActivity.ScreenLoc> list) {
        //figure out fragments and uncomment this after
        //String str = String.format("%s, %s", currentState, Long.toString(endTime - startTime));
        String str = String.format("%s %s","Left Thumb", Long.toString(endTime - startTime));
        for (int i = 0; i < list.size(); i++) {
            str = str + String.format(Locale.US, ",(%.3f:%.3f)", list.get(i).x, list.get(i).y);
        }
        Log.i("String", str);
        return str + "\n";
    }

    /**
     * This will generate a state
     * @return the generated state
     */
    private void genState() {
        //currentState = states[rand.nextInt(3)];
        // Put your UI display code here
    }

}
