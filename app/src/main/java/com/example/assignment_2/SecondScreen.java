package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class SecondScreen extends AppCompatActivity {
    private Button replay;
    private Button practice;
    private VideoView player;
    private String gestureSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        setupScreen();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(SecondScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });

        gestureSelected = getIntent().getStringExtra("Gesture");

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay.setText("Replay");
                switch (gestureSelected) {
                    case "Turn on Lights":
                        Uri uri1 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hlighton);
                        player.setVideoURI(uri1);
                        player.start();
                        break;

                    case "Turn off Lights":
                        Uri uri2 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hlightoff);
                        player.setVideoURI(uri2);
                        player.start();
                        break;
                    case "Turn on Fan":
                        Uri uri3 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hfanon);
                        player.setVideoURI(uri3);
                        player.start();
                        break;
                    case "Turn off Fan":
                        Uri uri4 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hfanoff);
                        player.setVideoURI(uri4);
                        player.start();
                        break;
                    case "Increase Fan Speed":
                        Uri uri5 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hincreasefanspeed);
                        player.setVideoURI(uri5);
                        player.start();
                        break;
                    case "Decrease Fan Speed":
                        Uri uri6 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hdecreasefanspeed);
                        player.setVideoURI(uri6);
                        player.start();
                        break;
                    case "Set Thermostat to specified temperature":
                        Uri uri7 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hsetthermo);
                        player.setVideoURI(uri7);
                        player.start();
                        break;
                    case "Digit 0":
                        Uri uri8 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h0);
                        player.setVideoURI(uri8);
                        player.start();
                        break;
                    case "Digit 1":
                        Uri uri9 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h1);
                        player.setVideoURI(uri9);
                        player.start();
                        break;
                    case "Digit 2":
                        Uri uri10 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h2);
                        player.setVideoURI(uri10);
                        player.start();
                        break;
                    case "Digit 3":
                        Uri uri11 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h3);
                        player.setVideoURI(uri11);
                        player.start();
                        break;
                    case "Digit 4":
                        Uri uri12 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h4);
                        player.setVideoURI(uri12);
                        player.start();
                        break;
                    case "Digit 5":
                        Uri uri13 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h5);
                        player.setVideoURI(uri13);
                        player.start();
                        break;
                    case "Digit 6":
                        Uri uri14 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h6);
                        player.setVideoURI(uri14);
                        player.start();
                        break;
                    case "Digit 7":
                        Uri uri15 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h7);
                        player.setVideoURI(uri15);
                        player.start();
                        break;
                    case "Digit 8":
                        Uri uri16 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h8);
                        player.setVideoURI(uri16);
                        player.start();
                        break;
                    case "Digit 9":
                        Uri uri17 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.h9);
                        player.setVideoURI(uri17);
                        player.start();
                        break;
                }
            }
        });

        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondScreen.this, UserUploadActivity.class);
                intent.putExtra("Gesture",gestureSelected);
                startActivity(intent);
            }
        });

    }
    private void setupScreen(){
        replay = (Button) findViewById(R.id.replaybtn);
        practice = (Button) findViewById(R.id.practicebtn);
        player = (VideoView) findViewById(R.id.videoPlayVV);
    }
}