package app.zhongjing.com.coolweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import app.zhongjing.com.coolweather.AppConfig;
import app.zhongjing.com.coolweather.adapter.ListAdapter;
import app.zhongjing.com.coolweather.db.CoolWeatherDB;
import app.zhongjing.com.coolweather.http.HttpCallbackListener;
import app.zhongjing.com.coolweather.http.HttpEngine;
import app.zhongjing.com.coolweather.model.City;
import app.zhongjing.com.coolweather.model.County;
import app.zhongjing.com.coolweather.model.Province;
import app.zhongjing.com.coolweather.util.ParseUtil;

/**
 * Created by chenjun on 16/3/3.
 */
public class BaseActivity extends AppCompatActivity{

    public static final String SELECTED_PROVINCE = "province";
    public static final String SELECTED_CITY = "city";

    private Province selectedProvince;
    private City selectedCity;
    private ListAdapter provinceAdapter;
    private CoolWeatherDB db;

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    public void setSelectedProvince(Province province){
        selectedProvince = province;
    }

    public void setSelectedCity(City city){
        selectedCity = city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void queryFromServer(final String code,final String type){
        String address = AppConfig.getAddress(code);
        HttpEngine.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                Log.i("chan", "onSuccess");
                boolean result = false;
                if ("province".equals(type)) {
                    Log.i("chan","response body : "+response);
                    result = ParseUtil.handleProvincesResponse(db, response);
                } else if ("city".equals(type)) {
                    Log.i("chan","response body : "+response);
                    result = ParseUtil.handleCitiesResponse(db, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    Log.i("chan","response body : "+response);
                    result = ParseUtil.handleCountiesResponse(db, response, selectedCity.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("province".equals(type)) {
                                queryProvince(db, provinceAdapter);
                            } else if ("city".equals(type)) {
                                queryCities(db, provinceAdapter);
                            } else if ("county".equals(type)){
                                queryCounties(db,provinceAdapter);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFail(Exception e) {
                Log.i("chan", "onFail");
                //通过runOnUiThread()方法回到主线程处理逻辑
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    protected void queryProvince(CoolWeatherDB db,ListAdapter adapter){

        if(this.provinceAdapter == null || this.db == null){
            this.provinceAdapter = adapter;
            this.db = db;
        }

        provinceList = db.loadProvinces();
        if (provinceList != null && provinceList.size() > 0){
            adapter.setProvinceList(provinceList);
        } else {
            queryFromServer(null, "province");
        }
    }

    protected void queryCities(CoolWeatherDB db , ListAdapter adapter){
        if (this.db == null || adapter == null){
            this.db = db;
            this.provinceAdapter = adapter;
        }
        //Log.i("chan","selectedProvinceId = "+selectedProvince.getId());
        cityList = db.loadCities(selectedProvince.getId());
        if(cityList != null && cityList.size() > 0){
            //Log.i("chan","queryDB");
            //Log.i("chan","cityListSize = "+cityList.size());
            adapter.setCityList(cityList);
        }else{
            //Log.i("chan","queryServer");
            queryFromServer(selectedProvince.getProvinveCode(),"city");
        }
    }

    protected void queryCounties(CoolWeatherDB db , ListAdapter adapter){
        if (this.db == null || adapter == null){
            this.db = db;
            this.provinceAdapter = adapter;
        }

        countyList = db.loadCounties(selectedCity.getId());
        if (countyList != null && countyList.size() > 0){
            adapter.setCountyList(countyList);

        }else{
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }
}
