package com.example.zhangbowen.carandroidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private Button forwardButton;
    private Button backButton;
    private Button leftButton;
    private Button rightButton;
    private Button fireButton;
    private Button exitButton;

    private static int white = Color.parseColor("#FFFFFF");
    private static int black = Color.parseColor("#000000");

    private String IP_ADDR;
    AlertDialog.Builder builder;
    private char lastClicked;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void unclickLast() {
        switch (lastClicked) {
            case 'w': {
                forwardButton.setTextColor(white);
                forwardButton.setBackgroundColor(black);
                //forwardButton.setBackground(exitButton.getBackground());
                break;
            }
            case 'a': {
                leftButton.setTextColor(white);
                leftButton.setBackgroundColor(black);
                //leftButton.setBackground(exitButton.getBackground());
                break;
            }
            case 's': {
                backButton.setTextColor(white);
                backButton.setBackgroundColor(black);
                //backButton.setBackground(exitButton.getBackground());
                break;
            }
            case 'd': {
                rightButton.setTextColor(white);
                rightButton.setBackgroundColor(black);
                //rightButton.setBackground(exitButton.getBackground());
                break;
            }
            case 'f': {
                fireButton.setTextColor(white);
                fireButton.setBackgroundColor(black);
                //fireButton.setBackground(exitButton.getBackground());
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        readInIp();
    }

    private void readInIp() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("IP Address of Server");
        builder.setCancelable(false);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IP_ADDR = input.getText().toString();
                connect();
            }
        });
        builder.show();
    }

    private void connect() {
        if (Client.startClient(IP_ADDR,this) == -1) {
            AlertDialog.Builder clientFail = new AlertDialog.Builder(this);
            clientFail.setTitle("WARNING");
            clientFail.setMessage("Start of client failed, try re-enter the IP address, and make sure the server is on.");
            clientFail.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    IP_ADDR = null;
                    readInIp();
                }
            });
            clientFail.show();
        } else {
            AlertDialog.Builder clientSuccess = new AlertDialog.Builder(this);
            clientSuccess.setTitle("SUCCESS");
            clientSuccess.setMessage("Establishment of client succeeded");
            clientSuccess.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    init();
                }
            });
            clientSuccess.show();
        }
    }

    private void init() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int buttonSize = height / 5;
        lastClicked = '#';

        forwardButton = findViewById(R.id.FORWARD_BUTTON);
        backButton = findViewById(R.id.BACK_BUTTON);
        leftButton = findViewById(R.id.LEFT_BUTTON);
        rightButton = findViewById(R.id.RIGHT_BUTTON);
        fireButton = findViewById(R.id.FIRE_BUTTON);
        exitButton = findViewById(R.id.EXIT_BUTTON);

        leftButton.setOnClickListener(leftListener);
        rightButton.setOnClickListener(rightListener);
        forwardButton.setOnClickListener(forwardListener);
        backButton.setOnClickListener(backListener);
        fireButton.setOnClickListener(fireListener);
        exitButton.setOnClickListener(exitListener);

        RelativeLayout.LayoutParams lpW = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        RelativeLayout.LayoutParams lpA = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        RelativeLayout.LayoutParams lpS = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        RelativeLayout.LayoutParams lpD = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        RelativeLayout.LayoutParams lpFire = new RelativeLayout.LayoutParams(buttonSize * 2, buttonSize * 2);
        // Set up forward
        lpW.leftMargin = width * 3 / 16;
        lpW.topMargin = buttonSize;
        forwardButton.setLayoutParams(lpW);
        forwardButton.setTextColor(white);
        forwardButton.setBackgroundColor(black);
        // Set up Back button
        lpS.leftMargin = width * 3 / 16;
        lpS.topMargin = buttonSize * 3;
        backButton.setLayoutParams(lpS);
        backButton.setTextColor(white);
        backButton.setBackgroundColor(black);
        // Set up Left Button
        lpA.leftMargin = width * 3 / 16 - buttonSize;
        lpA.topMargin = buttonSize * 2;
        leftButton.setLayoutParams(lpA);
        leftButton.setTextColor(white);
        leftButton.setBackgroundColor(black);
        // Set up Right button
        lpD.leftMargin = width * 3 / 16 + buttonSize;
        lpD.topMargin = buttonSize * 2;
        rightButton.setLayoutParams(lpD);
        rightButton.setTextColor(white);
        rightButton.setBackgroundColor(black);

        // Set up Fire button
        lpFire.leftMargin = width / 4 * 3;
        lpFire.topMargin = (int) (buttonSize * 1.5);
        fireButton.setLayoutParams(lpFire);
        fireButton.setTextColor(white);
        fireButton.setBackgroundColor(black);

        forwardButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        leftButton.setVisibility(View.VISIBLE);
        rightButton.setVisibility(View.VISIBLE);
        fireButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            unclickLast();
            if (lastClicked != 'a') {
                leftButton.setTextColor(black);
                leftButton.setBackgroundColor(white);
                lastClicked = 'a';
                Client.sendCommand("a");
            } else {
                lastClicked = '#';
            }
        }
    };
    private View.OnClickListener rightListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            unclickLast();
            if (lastClicked != 'd') {
                rightButton.setTextColor(black);
                rightButton.setBackgroundColor(white);
                lastClicked = 'd';
                Client.sendCommand("d");
            } else {
                lastClicked = '#';
            }
        }
    };
    private View.OnClickListener forwardListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            unclickLast();
            if (lastClicked != 'w') {
                forwardButton.setTextColor(black);
                forwardButton.setBackgroundColor(white);
                lastClicked = 'w';
                Client.sendCommand("w");
            } else {
                lastClicked = '#';
            }
        }
    };
    private View.OnClickListener backListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            unclickLast();
            if (lastClicked != 's') {
                backButton.setTextColor(black);
                backButton.setBackgroundColor(white);
                lastClicked = 's';
                Client.sendCommand("s");
            } else {
                lastClicked = '#';
            }
        }
    };
    private View.OnClickListener fireListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            unclickLast();
            if (lastClicked != 'f') {
                fireButton.setTextColor(black);
                fireButton.setBackgroundColor(white);
                lastClicked = 'f';
                Client.sendCommand("f");
            } else {
                lastClicked = '#';
            }
        }
    };
    private View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Client.sendCommand(String.valueOf((char) 27));
            AlertDialog.Builder clientClosed = new AlertDialog.Builder(MainActivity.this);
            clientClosed.setTitle("Exit clicked");
            clientClosed.setMessage("Client closed, Click Ok and type in IP addr if want to continue");
            clientClosed.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setContentView(R.layout.activity_main);
                    readInIp();
                }
            });
            clientClosed.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            clientClosed.show();
        }
    };
}
