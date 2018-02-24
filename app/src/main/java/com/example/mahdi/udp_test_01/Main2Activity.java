package com.example.mahdi.udp_test_01;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;


public class Main2Activity extends AppCompatActivity {

    Button NxtBtn;
    Button SrtBtn;
    boolean PTC = true;
    boolean PPTY = true;
    EditText ETT1;
    TextAdapter TXTV = new TextAdapter();
    RecyclerView recycler;
    boolean BtnCon = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        NxtBtn = (Button) findViewById(R.id.button);
        SrtBtn = (Button) findViewById(R.id.btnStart);
        ETT1 = (EditText) findViewById(R.id.ET1);

        recycler = (RecyclerView) findViewById(R.id.txtMain);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(TXTV);

        NxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Luc = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(Luc);
            }
        });

        SrtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BtnCon) {
                    PPTY = false;
                    PTC = false;
                    PPTY = true;
                    PTC = true;
                    new MyTask().execute(Integer.parseInt(ETT1.getText().toString()));
                    ETT1.setFocusable(false);
                    Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();
                    SrtBtn.setText("STOP");
                    BtnCon = false;
                } else {
                    if (!BtnCon) {
                        PTC = false;
                        PPTY = false;
                        SrtBtn.setText("START");
                        ETT1.setFocusableInTouchMode(true);
                        BtnCon = true;
                        Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    class MyTask extends AsyncTask<Integer, String, String> {
        protected String doInBackground(Integer... arg0) {
            if (PPTY) {
                Integer SVPRT = arg0[0];
                publishProgress("////AsyncTask:" + String.valueOf(SVPRT));
                //Toast.makeText(getApplicationContext(), String.valueOf(SVPRT), Toast.LENGTH_LONG).show();
                String RecData = "";
                while (PPTY) {
                    try {
                        //publishProgress("\n////Port:\n////" + String.valueOf(SVPRT));
                        DatagramSocket udpSocket = new DatagramSocket(SVPRT);
                        byte[] buf = new byte[1024];
                        DatagramPacket RecPacket = new DatagramPacket(buf, buf.length);
                        //Toast.makeText(getApplicationContext(), String.valueOf(RecPacket.getPort()), Toast.LENGTH_LONG).show();
                        publishProgress("////LocalPort:" + String.valueOf(udpSocket.getLocalPort()));
                        //publishProgress("\n////NONLIPort:" + String.valueOf(udpSocket.getPort()));
                        while (PTC) {
                            //publishProgress("\n////Listening");
                            udpSocket.receive(RecPacket);
                            //publishProgress("\n////Recieved");
                            String Dataa = new String(RecPacket.getData(), 0, RecPacket.getLength());
                            publishProgress(RecPacket.getAddress().toString() + ":" + String.valueOf(RecPacket.getPort()) + ">>" + Dataa);
                            RecPacket.setLength(buf.length);
                        }
                        udpSocket.close();
                    } catch (Exception e) {
                        publishProgress("\n////Error:\n////" + e.toString());
                    }
                }
            }
            return null;
        }

        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            TXTV.addText(values[0]);
            recycler.scrollToPosition(TXTV.getItemCount() - 1);
        }
    }
}
