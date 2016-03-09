package app.zhongjing.com.coolweather.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.zhongjing.com.coolweather.AppConfig;
import app.zhongjing.com.coolweather.R;
import app.zhongjing.com.coolweather.http.HttpCallbackListener;
import app.zhongjing.com.coolweather.http.HttpEngine;
import app.zhongjing.com.coolweather.service.AutoUpdateService;
import app.zhongjing.com.coolweather.ui.view.AutoSwipeRefreshLayout;
import app.zhongjing.com.coolweather.util.OpenActivityUtil;
import app.zhongjing.com.coolweather.util.ParseUtil;

/**
 * Created by chenjun on 16/3/7.
 */
public class WeatherInfoActivity extends BaseActivity{

    private LinearLayout weatherInfo;
    private TextView publishText;
    private TextView weatherDesc;
    private TextView temp1;
    private TextView temp2;
    private TextView currentDate;
    private AutoSwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;
    private SharedPreferences prefs;
    private boolean tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getIntent().getBooleanExtra("from_selected", false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("city_selected",false) && !tag){
            Intent intent = new Intent(this,ChooseAreaActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        findViewById();
        initView();


    }

    private void findViewById(){
        publishText = (TextView)findViewById(R.id.publish_text);
        weatherDesc = (TextView)findViewById(R.id.weather_desc);
        temp1 = (TextView)findViewById(R.id.temp1);
        temp2 = (TextView)findViewById(R.id.temp2);
        currentDate = (TextView)findViewById(R.id.current_date);
        weatherInfo = (LinearLayout)findViewById(R.id.weather_info_layout);
        refreshLayout = (AutoSwipeRefreshLayout)findViewById(R.id.refresh_weather);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    private void initView(){
        refreshLayout.setOnRefreshListener(listener);
        String countyCode  = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)){
            publishText.setText("更新中...");
            weatherInfo.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
            showWeather();
        }
    }

    private SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            publishText.setText("更新中...");
            String weatherCode = prefs.getString("weather_code","");
            if (!TextUtils.isEmpty(weatherCode)){
                queryWeatherInfo(weatherCode);
            }
        }
    };

    private void queryWeatherCode(String countyCode){
        String address = AppConfig.getAddress(countyCode);
        queryWeatherFromServer(address, "countyCode");
    }

    private void queryWeatherInfo(String weatherCode){
        String address = AppConfig.getWeatherInfoAddress(weatherCode);
        queryWeatherFromServer(address, "weatherCode");
    }

    private void queryWeatherFromServer(final String address,final String type){
        HttpEngine.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        //解析出天气代号
                        String[] arr = response.split("\\|");
                        if (arr != null && arr.length == 2) {
                            String weatherCode = arr[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    ParseUtil.handleWeatherResponse(WeatherInfoActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onFail(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("更新失败");
                    }
                });
            }
        });
    }

    @SuppressLint("NewApi")
    private void showWeather(){
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if (prefs == null){
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
        }
        toolbar.setTitle(prefs.getString("city_name", ""));
        toolbar.setNavigationIcon(getDrawable(R.mipmap.select));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityUtil.goToProvinceActivity(WeatherInfoActivity.this);
                finish();
            }
        });
        temp1.setText(prefs.getString("temp1", ""));
        temp2.setText(prefs.getString("temp2", ""));
        weatherDesc.setText(prefs.getString("weather_desc", ""));
        publishText.setText("今天" + prefs.getString("publish_time", "") + "发布");
        currentDate.setText(prefs.getString("current_date", ""));
        weatherInfo.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }
}
