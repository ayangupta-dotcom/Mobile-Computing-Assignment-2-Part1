package com.example.assignment_2;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserUploadActivity extends AppCompatActivity implements View.OnClickListener {

    public static Map<String, Integer> counterMap = new HashMap<>();

    private static int VIDEO_REQUEST=201;
    String ipv4AddressServer="192.168.0.98";
    String portNumber="5000";
    Button recordUserGesture;
    Button postFileToServer;
    String gestureSelected;
    String realPath;

    private Button replay;
    private VideoView player;
//    private String gestureSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upload);

        gestureSelected=getIntent().getStringExtra("Gesture");
        gestureSelected = gestureSelected.replace(" ","_");

        gestureSelected=getIntent().getStringExtra("Gesture");
        switch (gestureSelected) {
            case "Turn on Lights":
                gestureSelected = "LightOn_PRACTICE_";
                break;
            case "Turn off Lights":
                gestureSelected = "LightOff_PRACTICE_";
                break;
            case "Turn on Fan":
                gestureSelected = "FanOn_PRACTICE_";
                break;
            case "Turn off Fan":
                gestureSelected = "FanOff_PRACTICE_";
                break;
            case "Increase Fan Speed":
                gestureSelected = "FanUp_PRACTICE_";
                break;
            case "Decrease Fan Speed":
                gestureSelected = "FanDown_PRACTICE_";
                break;
            case "Set Thermostat to specified temperature":
                gestureSelected = "SetThermo_PRACTICE_";
                break;
            case "Digit 0":
                gestureSelected = "Num0";
                break;
            case "Digit 1":
                gestureSelected = "Num1";
                break;
            case "Digit 2":
                gestureSelected = "Num2";
                break;
            case "Digit 3":
                gestureSelected = "Num3";
                break;
            case "Digit 4":
                gestureSelected = "Num4";
                break;
            case "Digit 5":
                gestureSelected = "Num5";
                break;
            case "Digit 6":
                gestureSelected = "Num6";
                break;
            case "Digit 7":
                gestureSelected = "Num7";
                break;
            case "Digit 8":
                gestureSelected = "Num8";
                break;
            case "Digit 9":
                gestureSelected = "Num9";
                break;
        }

        recordUserGesture=findViewById(R.id.record_user_gesture);
        postFileToServer=findViewById(R.id.upload_to_server);

        recordUserGesture.setOnClickListener(this);
        postFileToServer.setOnClickListener(this);

    }

    public void postRequest(String postUrl,RequestBody httpRequestBody){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(httpRequestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserUploadActivity.this, "Connection to server not established", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserUploadActivity.this, "Video uploaded to server", Toast.LENGTH_SHORT).show();
                        Intent backToSquareOne = new Intent(UserUploadActivity.this, MainActivity.class);
                        finish();
                        startActivity(backToSquareOne);
                    }
                });
            }
        });

    }

    public void httpMultiFromRequestBody(String videoUri){

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions,1);

        if (counterMap.get(gestureSelected) == null) {
            counterMap.put(gestureSelected, 1);
        } else {
            Integer num=counterMap.get(gestureSelected);
            counterMap.put(gestureSelected, num+1);
        }

        String postUrl = "http://" + ipv4AddressServer + ":" + portNumber + "/";

        File stream=null;
        RequestBody postBodyImage=null;
        try
        {
            stream=new File(videoUri);

            postBodyImage = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image",
                            gestureSelected+"_PRACTICE_"+counterMap.get(gestureSelected)+
                                    "_vemireddy"+".mp4", RequestBody.create(MediaType.parse("video/*"), stream))
                    .build();

        }
        catch (Exception ioexp)
        {
            ioexp.printStackTrace();
        }

        postRequest(postUrl, postBodyImage);
    }

    public String getPathFromURI(Context context, Uri contentUri) {

        if ( contentUri.toString().indexOf("file:///") > -1 ){
            return contentUri.getPath();
        }

        Cursor thisCursor = null;
        try {
            String[] temp = { MediaStore.Images.Media.DATA };
            thisCursor = context.getContentResolver().query(contentUri,  temp, null, null, null);
            int column_index = thisCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            thisCursor.moveToFirst();
            return thisCursor.getString(column_index);
        }finally {
            if (thisCursor != null) {
                thisCursor.close();
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.record_user_gesture:
                recordVideoUsingMediastore(view);
                break;

            case R.id.upload_to_server:
                httpMultiFromRequestBody(realPath);
                break;

        }
    }

    public void recordVideoUsingMediastore(View view){

        Intent recordIntent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        recordIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
        recordIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        recordIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(recordIntent, VIDEO_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK && data!=null ) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                realPath=getPathFromURI(getApplicationContext(),uri);
                Toast.makeText(this, "Video has been saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == VIDEO_REQUEST && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Video recording cancelled.",
                    Toast.LENGTH_LONG).show();
        } else if(requestCode == VIDEO_REQUEST) {
            Toast.makeText(this, "Failed to record video",
                    Toast.LENGTH_LONG).show();
        }
    }

}