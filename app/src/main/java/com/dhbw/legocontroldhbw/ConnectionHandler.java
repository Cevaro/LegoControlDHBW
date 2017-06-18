package com.dhbw.legocontroldhbw;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by D062299 on 02.02.2017.
 */


public class ConnectionHandler{

    public final String SERVER_ADDR = "192.168.43.39";
    public final int SERVER_PORT = 4711;

    public InetAddress ia;

    public void open() throws UnknownHostException {
        ia = InetAddress.getByName( SERVER_ADDR );
    }

    public void sendPacket(String s) throws IOException {
        byte[] raw = s.getBytes();

        DatagramPacket packet = new DatagramPacket( raw, raw.length, ia, SERVER_PORT );
        DatagramSocket dSocket = new DatagramSocket();

        dSocket.send( packet );

    }
}
/*
public class ConnectionHandler extends AsyncTask<Void, Void, Void> {

   /* public URL url;
    public HttpURLConnection connection;
    public OutputStreamWriter outputWriter;

    public ConnectionHandler() throws IOException {
        url = new URL("http://10.0.1.1");
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        outputWriter = new OutputStreamWriter(
                connection.getOutputStream());
    }*//*

    private Socket sock;
    private OutputStream ostream;
    private PrintStream pstream;
    private String message = null;

    public ConnectionHandler() throws IOException {
        /*sock = new Socket("192.168.43.39", 3141);
        ostream = sock.getOutputStream();
         pstream = new PrintStream(ostream);*//*

    }

    public void setMessage(String m) {
        message = m;
    }

    public void closeConnection() throws IOException {
      /*pstream.close();
       ostream.close();
       sock.close();*//*
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //InetAddress serverAddress = null;

        //    serverAddress = InetAddress.getByName("192.168.43.39");
        //  String message = "Forward";
        try {
            sock = new Socket("192.168.43.39", 3141);
            ostream = sock.getOutputStream();
            pstream = new PrintStream(ostream);


                pstream.println(message);

            pstream.close();
            ostream.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
*/