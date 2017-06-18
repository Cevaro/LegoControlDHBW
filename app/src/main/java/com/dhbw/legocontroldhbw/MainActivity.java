package com.dhbw.legocontroldhbw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.dhbw.legocontroldhbw.R.id.textViewSent;

public class MainActivity extends AppCompatActivity {

    //public ClientThread client;
    public ConnectionHandler standardConnectionHandler;
    public static final String FORWARD_MODE = "F";
    public static final String BACKWARD_MODE = "B";
    public static final String LEFT_MODE = "L";
    public static final String RIGHT_MODE = "R";
    public static final String STILL_MODE = "S";

    private static String mode = STILL_MODE;
    private static TextView textViewSent;
    private static Sender sender;
    //private Socket socket;
    //private OutputStream ostream;
    //private PrintStream pstream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btForward = (Button) findViewById(R.id.buttonForward);
        Button btBackward = (Button) findViewById(R.id.buttonBackward);
        Button btLeft = (Button) findViewById(R.id.buttonLeft);
        Button btRight = (Button) findViewById(R.id.buttonRight);
        textViewSent = (TextView) findViewById(R.id.textViewSent);

        standardConnectionHandler = new ConnectionHandler();
        try {
            standardConnectionHandler.open();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        sender = new Sender();
        sender.start();


        btForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String sent;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //standardConnectionHandler.sendPacket(FORWARD_MODE);
                        //String s = "Sent: " + FORWARD_MODE;
                        //textViewSent.setText(s);
                        mode = FORWARD_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        //standardConnectionHandler.sendPacket(STILL_MODE);
                        //String s = "Sent: " + STILL_MODE;
                        //textViewSent.setText(s);
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        //standardConnectionHandler.sendPacket(STILL_MODE);
                        //String s = "Sent: " + STILL_MODE;
                        //textViewSent.setText(s);
                        mode = STILL_MODE;
                        String s = "Sent: " + mode;
                        textViewSent.setText(s);
                        return true;
                }
                return false;
            }
        });

        btBackward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String sent;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mode = BACKWARD_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                }
                return false;
            }
        });

        btLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String sent;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mode = LEFT_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                }
                return false;
            }
        });

        btRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String sent;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mode = RIGHT_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mode = STILL_MODE;
                        sent = "Sent: " + mode;
                        textViewSent.setText(sent);
                        return true;
                }
                return false;
            }
        });
    }

    /*public void startConnection() throws IOException, InterruptedException {
        /*standardConnectionHandler = new ConnectionHandler();
        standardConnectionHandler.setMessage(FORWARD_MODE);
        standardConnectionHandler.execute();*/
        //client = new ClientThread();
        //Thread thread = new Thread();
        //thread.start();
    //}

    /*@Override
    public void onStop() {
        super.onStop();
        try {
            pstream.close();
            ostream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void switchToGyroMode(View view) throws IOException {
        sender.interrupt();
        Intent intent = new Intent(this, GyroModeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                socket = new Socket(SERVER_IP, SERVERPORT);
                ostream = socket.getOutputStream();
                pstream = new PrintStream(ostream);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }*/

    private class Sender extends Thread{
        public void run(){
            while (true) {
                try {
                    standardConnectionHandler.sendPacket(mode);
                    Thread.sleep(200);
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (InterruptedException e) {
return;
                }
            }
        }
    }
}
