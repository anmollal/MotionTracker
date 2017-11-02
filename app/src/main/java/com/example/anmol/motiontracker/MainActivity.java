package com.example.anmol.motiontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<ScreenLoc> swipe;
    String[] states = {"Right Thumb", "Left Thumb", "Any Finger"};
    String currentState;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        genState();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            swipe = new ArrayList<>();
        }
        Log.i("X, Y", Float.toString(ev.getX(0)) + " , " + Float.toString(ev.getY(0)));
        swipe.add(new ScreenLoc(ev.getX(0), ev.getY(0)));
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            File file = new File(this.getExternalFilesDir("SwipeData"), "SwipeData.csv");
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

    private String swipeString(List<ScreenLoc> list) {
        String str = currentState;
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
        currentState = states[rand.nextInt(3)];
        // Put your UI display code here
    }
}
