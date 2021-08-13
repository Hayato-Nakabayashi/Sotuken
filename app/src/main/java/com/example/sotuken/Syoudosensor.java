package com.example.sotuken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Syoudosensor extends Activity implements Runnable, SensorEventListener{
    SensorManager sm;
    TextView tv;
    Handler h;
    String str = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        setContentView(ll);
        tv = new TextView(this);
        ll.addView(tv);
        h = new Handler();
        h.postDelayed(this, 500);
    }

    @SuppressLint("SetTextI18n")
    public void run() {
        tv.setText("照度" + str);
        h.postDelayed(this, 500);
    }

    protected void onResume() {
        super.onResume();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors =
                sm.getSensorList(Sensor.TYPE_LIGHT);
        if (0 < sensors.size()) {
            sm.registerListener(this,sensors.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(this);
    }

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            str = "照度:" + event.values[0];
            tv.setText(str);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
