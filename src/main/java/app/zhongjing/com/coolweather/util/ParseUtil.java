package app.zhongjing.com.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.zhongjing.com.coolweather.db.CoolWeatherDB;
import app.zhongjing.com.coolweather.model.City;
import app.zhongjing.com.coolweather.model.County;
import app.zhongjing.com.coolweather.model.Province;

/**
 * Created by chenjun on 16/3/3.
 */
public class ParseUtil {

    /**
     * 解析服务器返回的省级数据
     * @param db
     * @param response
     * @return
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB db,String response){

        if (!TextUtils.isEmpty(response)){
            String[] allProvince = response.split(",");
            if (allProvince != null && allProvince.length > 0){
                for (String p : allProvince){
                    String[] arr = p.split("\\|");
                    Province province = new Province();
                    province.setProvinveCode(arr[0]);
                    province.setProvinceName(arr[1]);
                    //将解析出来的数据存储到province表中
                    db.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     * @param db
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCitiesResponse(CoolWeatherDB db,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0){
                for (String c : allCities){
                    String[] arr = c.split("\\|");
                    City city = new City();
                    city.setCityCode(arr[0]);
                    city.setCityName(arr[1]);
                    city.setProvinceId(provinceId);
                    db.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     * @param db
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountiesResponse(CoolWeatherDB db,String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0){
                for (String c : allCounties){
                    String[] arr = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(arr[0]);
                    county.setCountyName(arr[1]);
                    county.setCityId(cityId);
                    db.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的json数据，并将解析出来的数据存储到本地
     * @param context
     * @param response
     */
    public static void handleWeatherResponse(Context context,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesc = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesc,publishTime);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 将服务器返回的天气信息存储到SharedPreference文件中
     * @param context
     * @param cityName
     * @param weatherCode
     * @param temp1
     * @param temp2
     * @param weatherDesc
     * @param publishTime
     */
    public static void saveWeatherInfo(Context context,String cityName,String weatherCode,String temp1,String temp2,String weatherDesc,String publishTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desc", weatherDesc);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}
