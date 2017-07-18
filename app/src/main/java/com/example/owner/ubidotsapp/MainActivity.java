package com.example.owner.ubidotsapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ubidots.ApiClient;
import com.ubidots.Variable;

public class MainActivity extends Activity {

    private static final String BATTERY_LEVEL = "level";
    private TextView mBatteryLevel;
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("ywlee", "BB here3");
            int level = intent.getIntExtra(BATTERY_LEVEL, 0);
            Log.i("ywlee", "BB here4 ="+level);
            mBatteryLevel.setText(Integer.toString(level) + "%");
            Log.i("ywlee", "BB here4");
            new ApiUbidots().execute(level);
        }
    };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mBatteryLevel=findViewById(R.id.batteryLevel);
        }

    @Override
    protected void onStart() {super.onStart();
        Log.i("ywlee", "here");
        registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Log.i("ywlee", "here2");
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBatteryReceiver);
        super.onStop();

    }

    protected class ApiUbidots extends AsyncTask<Integer, Void, Void> {

        private final String API_KEY = "27ce0fbfb88598f0b6ce90e0eac7d73fcee776cf";//"our_api_key";
        private final String VARIABLE_ID ="596dc8767625420909c7cfda";// "our_variable_id";


        @Override
            protected Void doInBackground(Integer... params) {
                ApiClient apiClient = new ApiClient(API_KEY);
                Variable batteryLevel = apiClient.getVariable(VARIABLE_ID);

                batteryLevel.saveValue(params[0]);
                return null;
            }
        }
    }