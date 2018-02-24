package com.example.mahdi.udp_test_01;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import android.widget.EditText;
import android.widget.TextView;
import java.net.*;


public class MainActivity extends AppCompatActivity {

    Button btnClickMe;
    Button btnNext;
    EditText ett1;
    EditText ett2;
    EditText ett3;
    TextView TVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClickMe = (Button) findViewById(R.id.btn1);
        btnNext=(Button)findViewById(R.id.button2);
        ett1   = (EditText)findViewById(R.id.eT1);
        ett2   = (EditText)findViewById(R.id.eT2);
        ett3   = (EditText)findViewById(R.id.eT3);
        TVV    = (TextView)findViewById(R.id.textView4);
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(ett1.getText().toString(),ett2.getText().toString(),ett3.getText().toString());
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NXTT=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(NXTT);
            }
        });
    }

    class MyTask extends AsyncTask<String, String, Void> {

        protected Void doInBackground(String... arg0) {
            String SVRAD=arg0[0];
            String SVPRT=arg0[1];
            String CLDTA=arg0[2];
            try {
                DatagramSocket udpSocket = new DatagramSocket(9999);
                publishProgress("UDPSocket Created");
                InetAddress serverAddr = InetAddress.getByName(SVRAD);
                byte[] buf = (CLDTA).getBytes();
                publishProgress("Buffered");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, Integer.parseInt(SVPRT));
                publishProgress("UDP created");
                udpSocket.send(packet);
                publishProgress("Sent");
                udpSocket.close();
                publishProgress("Done");
            } catch (Exception e) {
                publishProgress("Error:\n" + e.toString());
            }
            return null;
        }

        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            TVV.setText((values[0]).toCharArray(),0,values[0].length());
        }

        protected void onPostExecute(Void result) {
            // TODO: do something with the feed
        }
    }
}


































/*


                try {
                    Random rand = new Random();
                    int pickedNumber = rand.nextInt(500) + 1000;
                    DatagramSocket udpSocket = new DatagramSocket(pickedNumber);
                    TVV.setText("UDPSocket Created".toCharArray(),0,17);
                    InetAddress serverAddr = InetAddress.getByName(ett1.getText().toString());
                    byte[] buf = (ett3.getText().toString()).getBytes();
                    TVV.setText("Buffered".toCharArray(),0,8);
                    //DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr,Integer.parseInt(ett2.getText().toString()));
                    DatagramPacket packet = new DatagramPacket(buf, buf.length,InetAddress.getByName("192.168.1.101"),9999);
                    TVV.setText("UDP created".toCharArray(),0,11);
                    udpSocket.send(packet);
                    TVV.setText("Sent".toCharArray(),0,4);
                    udpSocket.close();
                    TVV.setText("Done".toCharArray(),0,4);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


 */