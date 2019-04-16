package com.example.ee497_2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square   mSquare;

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private float accx, accy, accz, magx, magy, magz;

    @Override
    public void onSurfaceCreated(GL10 unused, javax.microedition.khronos.egl.EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();

        Log.d("mylog", "Start Renderer");


    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        //SENSOR_DELAY_NORMAL();

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Create a rotation transformation for the triangle

        //Rotate X
        Matrix.rotateM(vPMatrix, 0, accz*7 ,  1f, 0f, 0f);

        //Rotate Y
        Matrix.rotateM(vPMatrix, 0, magx*2 - 0f,  0f, -1f, 0f);


//        Log.d("mylog","-----acceleration values------");
//        Log.d("mylog",String.valueOf(accx));
//        Log.d("mylog",String.valueOf(accy));
//        Log.d("mylog",String.valueOf(accz));
//
//        Log.d("mylog","-----magnetic values------");
//        Log.d("mylog",String.valueOf(magx));
//        Log.d("mylog",String.valueOf(magy));
//        Log.d("mylog",String.valueOf(magz));

        // Draw shape
        mTriangle.draw(vPMatrix);
    }



    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void setAcc(float accx, float accy, float accz){
        this.accx=accx;
        this.accy=accy;
        this.accz=accz;
    }

    public void setMag(float magx, float magy, float magz){
        this.magx=magx;
        this.magy=magy;
        this.magz=magz;
    }
}