package com.example.jayanthl.file_im;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MyService extends Service {
    File file;
    File[] listfiles;
    String filename,filepath;
    Socket socket;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void onCreate() {
        super.onCreate();
        Log.i("Started ","Intent");
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.i("SDCARD","Not Mounted");
        }
        else {
            Log.i("Exectuting ","Else part");
            String fileBase64 = getBase64();
            if(fileBase64.equals("0")) {
                Log.i("In real :","file not found");
            }
        }
    }

    public String getBase64() {
        Log.i("Executing","function");
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "samp_image");
        file.mkdirs();
        listfiles = file.listFiles();
        filename = listfiles[0].getName();
        filepath = listfiles[0].getAbsolutePath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        //create socket and bind it
                        Log.i("trying ","to create socket");
                        socket = new Socket("192.168.43.232",3000);
                        Log.i("Socket"," Created");
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                        FileInputStream fis = new FileInputStream(filepath);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        try {
                            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                                bos.write(buf, 0, readNum);
                                Log.i("Read", readNum + " bytes");
                            }
                        } catch (IOException e) {
                            Log.i("Error :", "IOexcption");
                            //return "0";
                        }
                        byte[] bytes = bos.toByteArray();
                        String encodedString = Base64.encodeToString(bytes,Base64.NO_WRAP);
                        oos.writeObject(encodedString.toString());
                        oos.flush();

                        String mess = (String) ois.readObject();
                        Log.i("The ","message from server " + mess);
                        //return encodedString;
                    } catch (Exception e) {
                        Log.i("Error is : ","Exception");
                        //return "0";
                    }

                } catch (Exception e) {
                    Log.i("File ","Not Found");
                    //return "0";

                }
            }
        }).start();
    return  "0";
    }
}
