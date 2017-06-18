package com.dhbw.legocontroldhbw;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.UnknownHostException;

public class GyroModeActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private MyListener mListener;
    public ConnectionHandler gyroConnectionHandler;
    public TextView textViewSent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewSent = (TextView) findViewById(R.id.textViewSentG);

        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        try {
            mListener = new MyListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gyroConnectionHandler = new ConnectionHandler();
        try {
            gyroConnectionHandler.open();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void switchToMainMode(View view) throws IOException {
        mListener.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class MyListener implements SensorEventListener {

        private Sensor mRotationVectorSensor;
        private final float[] mRotationMatrix = new float[16];
        private int updateCounter = 0;

        public MyListener() throws IOException {
            // find the rotation-vector sensor
            mRotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            // initialize the rotation matrix
            mRotationMatrix[0] = 1;
            mRotationMatrix[4] = 1;
            mRotationMatrix[8] = 1;
            mRotationMatrix[12] = 1;



        }

        public void start() {
            // enable our sensor when the activity is resumed, ask for
            // 100 ms updates.
            mSensorManager.registerListener(this, mRotationVectorSensor, 100000);
        }


        public void stop() {
            // make sure to turn our sensor off when the activity is paused
            mSensorManager.unregisterListener(this);
        }

            @Override
        public void onSensorChanged(SensorEvent event) {
            // we received a sensor event. it is a good practice to check
            // that we received the proper event
            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                // convert the rotation-vector to a 4x4 matrix.
                SensorManager.getRotationMatrixFromVector(
                        mRotationMatrix , event.values);
                updateCounter++;
                updateDebugPanel(mRotationMatrix);
            }

            //convert rotation data and send it off
                int ix = (int) Math.floor((mRotationMatrix[8])*200);
                ix = -ix;
                if (ix > 100) ix = 100;
                if (ix < -100) ix = -100;
                String intensity = null;
                if (ix >= 0 && ix < 10) {
                    intensity = "F"+"00"+ix;
                } else if (ix >=0 && ix < 100){
                    intensity = "F"+"0"+ix;
                } else if (ix >=0 && ix == 100){
                    intensity = "F"+ix;
                }

                if (ix < 0 && ix > -10) {
                    intensity = "B"+"00"+(ix*(-1));
                } else if (ix <0 && ix > -100){
                    intensity = "B"+"0"+(ix*(-1));
                } else if (ix <0 && ix == -100){
                    intensity = "B"+(ix*(-1));
                }

                int iy = (int) Math.floor((mRotationMatrix[9])*200);
                if (iy > 100) iy = 100;
                if (iy < -100) iy = -100;
                String direction = null;
                if (iy >= 0 && iy < 10) {
                    direction = "R"+"00"+iy;
                } else if (iy >=0 && iy < 100){
                    direction = "R"+"0"+iy;
                } else if (iy >=0 && iy == 100){
                    direction = "R"+iy;
                }

                if (iy < 0 && iy > -10) {
                    direction = "L"+"00"+(iy*(-1));
                } else if (iy <0 && iy > -100){
                    direction = "L"+"0"+(iy*(-1));
                } else if (iy <0 && iy == -100){
                    direction = "L"+(iy*(-1));
                }
                try {
                    String s = ("G"+intensity+direction);
                    gyroConnectionHandler.sendPacket(s);
                    s = "Sent: " + s;
                    textViewSent.setText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        public void updateDebugPanel(float[] matrix) {
            TextView textViewUpdateCounter = (TextView) findViewById(R.id.textViewUpdateCount);
            /*TextView textViewArray0 = (TextView) findViewById(R.id.textViewArray0);
            TextView textViewArray1 = (TextView) findViewById(R.id.textViewArray1);
            TextView textViewArray2 = (TextView) findViewById(R.id.textViewArray2);
            TextView textViewArray3 = (TextView) findViewById(R.id.textViewArray3);
            TextView textViewArray4 = (TextView) findViewById(R.id.textViewArray4);
            TextView textViewArray5 = (TextView) findViewById(R.id.textViewArray5);
            TextView textViewArray6 = (TextView) findViewById(R.id.textViewArray6);
            TextView textViewArray7 = (TextView) findViewById(R.id.textViewArray7);*/
            TextView textViewArray8 = (TextView) findViewById(R.id.textViewArray8);
            TextView textViewArray9 = (TextView) findViewById(R.id.textViewArray9);
            TextView textViewArray10 = (TextView) findViewById(R.id.textViewArray10);
            /*TextView textViewArray11 = (TextView) findViewById(R.id.textViewArray11);
            TextView textViewArray12 = (TextView) findViewById(R.id.textViewArray12);
            TextView textViewArray13 = (TextView) findViewById(R.id.textViewArray13);
            TextView textViewArray14 = (TextView) findViewById(R.id.textViewArray14);
            TextView textViewArray15 = (TextView) findViewById(R.id.textViewArray15);*/
            textViewUpdateCounter.setText(Integer.toString(updateCounter));
            /*textViewArray0.setText(Float.toString(matrix[0]));
            textViewArray1.setText(Float.toString(matrix[1]));
            textViewArray2.setText(Float.toString(matrix[2]));
            textViewArray3.setText(Float.toString(matrix[3]));
            textViewArray4.setText(Float.toString(matrix[4]));
            textViewArray5.setText(Float.toString(matrix[5]));
            textViewArray6.setText(Float.toString(matrix[6]));
            textViewArray7.setText(Float.toString(matrix[7]));*/
            textViewArray8.setText(Float.toString(matrix[8]));
            textViewArray9.setText(Float.toString(matrix[9]));
            textViewArray10.setText(Float.toString(matrix[10]));
            /*textViewArray11.setText(Float.toString(matrix[11]));
            textViewArray12.setText(Float.toString(matrix[12]));
            textViewArray13.setText(Float.toString(matrix[13]));
            textViewArray14.setText(Float.toString(matrix[14]));
            textViewArray15.setText(Float.toString(matrix[15]));*/
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }
}