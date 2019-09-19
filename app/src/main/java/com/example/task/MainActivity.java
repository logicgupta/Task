package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    Button start,stop,send;
    EditText editView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        final Intent intent=new Intent(MainActivity.this,BGService.class);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isServiceRunning(BGService.class)){
                    startService(intent);
                    Toast.makeText(MainActivity.this, "SuccessFully Started Service !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Service is already started ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isServiceRunning(BGService.class)){
                    Toast.makeText(MainActivity.this, "Service isn't started yet", Toast.LENGTH_SHORT).show();
                }
                else{
                    stopService(intent);
                    Toast.makeText(MainActivity.this, "Service Stopped !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isServiceRunning(BGService.class)){
                    Toast.makeText(MainActivity.this, "Service isn't started yet", Toast.LENGTH_SHORT).show();
                }
                else{
                    BGService service=new BGService();
                    editView.getText().toString();
                    service.getData( editView.getText().toString());
                }

            }
        });

    }
    void init(){
        start=findViewById(R.id.startButton);
        stop=findViewById(R.id.stopButton);
        send=findViewById(R.id.sendButton);
        editView =findViewById(R.id.textView);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventFromService(EventMessage eventMessage){
        editView.setText(""+eventMessage.getNotification());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
