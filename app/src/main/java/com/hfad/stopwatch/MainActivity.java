package com.hfad.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    private Chronometer stopwatch;
    private boolean running = false;
    private long offset = 0;

    //KEYS for use with the bundle
    public static final String OFFSET_KEY = "offset";
    public static final String RUNNING_KEY = "running";
    public static final String BASE_KEY = "base";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatch = findViewById(R.id.chrStopwatch);
        Button btnStart = findViewById(R.id.btnStart);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnReset = findViewById(R.id.btnReset);

        if (savedInstanceState != null)
        {
            offset = savedInstanceState.getLong(OFFSET_KEY);
            running = savedInstanceState.getBoolean(RUNNING_KEY);

            if (running)
            {
                stopwatch.setBase(savedInstanceState.getLong(BASE_KEY));
                stopwatch.start();
            }
            else
            {
                setBaseTime();
            }
        }
//The start button starts the stopwatch if it is not already running
        btnStart.setOnClickListener(view -> {
            if (!running)
                {
                    setBaseTime();
                    stopwatch.start();
                    running = true;


                    System.out.println("START:");

                    System.out.println("\trunning: " + running);
                    System.out.println("\toffset: " + offset);
                    System.out.println("\tbase: " + stopwatch.getBase());
                }
        });

        btnPause.setOnClickListener(view -> {
            if (running)
            {
                saveOffset();
                stopwatch.stop();
                running = false;
                System.out.println("PAUSE:");

                System.out.println("\trunning: " + running);
                System.out.println("\toffset: " + offset);
                System.out.println("\tbase: " + stopwatch.getBase());
            }
        });

        btnReset.setOnClickListener(view -> {
            offset = 0;
            setBaseTime();
            System.out.println("RESET:");
            System.out.println("\trunning: " + running);
            System.out.println("\toffset: " + offset);
            System.out.println("\tbase: " + stopwatch.getBase());
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong(OFFSET_KEY, offset);
        savedInstanceState.putBoolean(RUNNING_KEY, running);
        savedInstanceState.putLong(BASE_KEY, stopwatch.getBase());
    }
    public void setBaseTime()
    {
        stopwatch.setBase(SystemClock.elapsedRealtime() - offset);
    }

    public void saveOffset(){
        offset = SystemClock.elapsedRealtime() - stopwatch.getBase();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (running) {
            saveOffset();
            stopwatch.stop();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (running) {
            setBaseTime();
            stopwatch.start();
            offset = 0;
        }
    }

}