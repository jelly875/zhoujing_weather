package app.zhongjing.com.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import app.zhongjing.com.coolweather.AppConfig;
import app.zhongjing.com.coolweather.AutoUpdateReceiver;
import app.zhongjing.com.coolweather.http.HttpCallbackListener;
import app.zhongjing.com.coolweather.http.HttpEngine;
import app.zhongjing.com.coolweather.util.ParseUtil;

/**
 * Created by chenjun on 16/3/8.
 */
public class AutoUpdateService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int time = 6*60*60*1000;
        //int time = 5*1000;
        long triggerAtTime = SystemClock.elapsedRealtime()+time;
        Intent i = new Intent(this,AutoUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(){
        //Log.i("chan","updateWeather");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code", "");
        String address = AppConfig.getWeatherInfoAddress(weatherCode);
        HttpEngine.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                ParseUtil.handleWeatherResponse(AutoUpdateService.this,response);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
