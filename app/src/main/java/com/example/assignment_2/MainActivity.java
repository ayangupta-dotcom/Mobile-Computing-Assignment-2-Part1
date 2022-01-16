package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //FFF4DE06
    public String ipAddr = "192.168.0.98";
    public String portNo= "5000";
    private Button connect;

    private Button next;
    private Spinner gestureList;
    private ArrayList<String> inputList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupScreen();

        makeList();
        gestureList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(gestureList.getSelectedItem().toString().equals("Select a Gesture")){
                    System.out.println(gestureList.getSelectedItem().toString());
                }
                else{
                    try{
                        Intent intent = new Intent(MainActivity.this,SecondScreen.class);
                        intent.putExtra("Gesture",gestureList.getSelectedItem().toString());
                        startActivity(intent);
                    } catch (NullPointerException e){
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Waiting for Selection");
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectServer(ipAddr,portNo);
//                Intent intent = new Intent(MainActivity.this,FirstScreen.class);
//                startActivity(intent);
            }
        });
    }

    private void makeList() {
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,inputList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gestureList.setAdapter(adp1);
        gestureList.setSelection(0);
    }

    private void setupScreen() {
//        next = (Button) findViewById(R.id.nextbtn);
        gestureList = (Spinner) findViewById(R.id.listSpinner);
        inputList.add("Select a Gesture");
        inputList.add("Turn on Lights");
        inputList.add("Turn off Lights");
        inputList.add("Turn on Fan");
        inputList.add("Turn off Fan");
        inputList.add("Increase Fan Speed");
        inputList.add("Decrease Fan Speed");
        inputList.add("Set Thermostat to specified temperature");
        inputList.add("Digit 0");
        inputList.add("Digit 1");
        inputList.add("Digit 2");
        inputList.add("Digit 3");
        inputList.add("Digit 4");
        inputList.add("Digit 5");
        inputList.add("Digit 6");
        inputList.add("Digit 7");
        inputList.add("Digit 8");
        inputList.add("Digit 9");

        connect = (Button) findViewById(R.id.startbtn);
    }

    void connectServer(String ipaddr, String portno){
        String postUrl= "http://"+ipaddr+":"+portno+"/";
        System.out.println(postUrl);

        String postBodyText="Hello";
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(mediaType, postBodyText);

        postRequest(postUrl, postBody);
    }

    void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                System.out.println(e);
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.Welcometv);
                        responseText.setText("Failed to Connect to Server!");
                        String outfail = "Failed to Connect to Server!";
                        Toast.makeText(MainActivity.this,outfail,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.Welcometv);
                        try {
                            responseText.setText(response.body().string());
                            String out = "Connected to Server: "+postUrl;
                            Toast.makeText(MainActivity.this,out,Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}

