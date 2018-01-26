package com.example.jayanthl.file_im;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.example.jayanthl.file_im.MyService;

public class MainActivity extends AppCompatActivity {
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            public void run() {
                try {
                    /*
                    Log.i("Socket ","creation started");
                    socket = new Socket("192.168.43.232", 3000);
                    Log.i("Socket"," Created");
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    String message =(String) is.readObject();
                    Log.i("The ","message from server is " + message );
                    os.writeObject("hello World!");
                    */

                    Intent intent = new Intent(getApplicationContext(),MyService.class);
                    getApplicationContext().startService(intent);
                } catch (Exception e) {
                    Log.i("IOexception : ","Could not create socket");
                }


            }
        }).start();
    }
}