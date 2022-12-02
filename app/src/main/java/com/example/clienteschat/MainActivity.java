package com.example.clienteschat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Socket sock;
    HandlerThread htConnNRead;
    HandlerThread htEscritura;
    Handler handlerConnNRead;
    Handler handlerEscritura;

    LinearLayout containerMensajes;
    EditText teclado;

    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        htConnNRead = new HandlerThread("Conexion y Lectura");
        htConnNRead.start();
        Looper looperConexion = htConnNRead.getLooper();
        handlerConnNRead = new Handler(looperConexion);

        mainActivity = this;

        htEscritura = new HandlerThread("Escritura");
        htEscritura.start();
        Looper looperEscritura = htEscritura.getLooper();
        handlerEscritura = new Handler(looperEscritura);

        containerMensajes = (LinearLayout) findViewById(R.id.lyContainerMensajes);

        handlerConnNRead.post(new Runnable() {
            @Override
            public void run() {

                if(sock == null){
                    try {
                        sock = new Socket("10.0.2.2", 6666);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                InputStream inputStream = null;
                BufferedReader reader = null;

                try {

                    if(inputStream == null){

                        inputStream = sock.getInputStream();

                        reader = new BufferedReader(new InputStreamReader(inputStream));
                    }

                    String str;

                    while ((str = reader.readLine()) != null){

                        TextView nuevoMsg = new TextView(MainActivity.this);
                        //nuevoMsg.setId((int)System.currentTimeMillis());
                        nuevoMsg.setText(str);

                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                containerMensajes.addView(nuevoMsg);
                                teclado.setText("");
                            }
                        });

                        //Log.i("Recepción", str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        teclado = (EditText) findViewById(R.id.etTeclado);

        Button bEnviar = (Button) findViewById(R.id.bEnviar);

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    public void sendMessage(){

        handlerEscritura.post(new Runnable() {
            @Override
            public void run() {

                String mensaje;

                OutputStream outputStream;

                BufferedWriter writer;

                boolean vacio;

                try {
                    mensaje = teclado.getText().toString();

                    vacio = true;

                    outputStream = sock.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(outputStream));

                    for(int i = 0; i < mensaje.length(); i++){
                        if(mensaje.charAt(i) != ' '){
                            vacio = false;
                        }
                    }

                    if (!vacio){
                        writer.write(mensaje);
                        writer.newLine();
                        writer.flush();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        });
    }

}