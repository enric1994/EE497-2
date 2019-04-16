package com.example.ee497_2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

class MyGLSurfaceView extends GLSurfaceView implements SensorEventListener {

    private final MyGLRenderer renderer;
    private SensorManager sensorManager;
    private Sensor mAcc, mMag;
    public static float accx, accy, accz, magx, magy, magz;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);

//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
        mMag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, mMag, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("mylog", "start GLSurfaceView");

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


    public final void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == 1) {
            float accx = event.values[0];
            float accy = event.values[1];
            float accz = event.values[2];
            setAcc(accx,accy,accz);
//            Log.d("mylog",String.valueOf(sensor.getType()));
        }
        if (sensor.getType() == 2) {
            float magx = event.values[0];
            float magy = event.values[1];
            float magz = event.values[2];
            setMag(magx, magy, magz);

//            Log.d("mylog",String.valueOf(sensor.getType() ));

        }



//        Log.d("mylog", String.valueOf(event.values[3]));
//        Log.d("mylog2", String.valueOf(accx));
        // Do something with this sensor value.

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mMag, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void setAcc(float accx, float accy, float accz){
        renderer.setAcc(accx,accy,accz);
    }
    public void setMag(float magx, float magy, float magz){
        renderer.setMag(magx,magy,magz);
    }




}