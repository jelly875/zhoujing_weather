package app.zhongjing.com.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

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
}
