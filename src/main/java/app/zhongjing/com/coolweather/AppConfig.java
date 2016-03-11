package app.zhongjing.com.coolweather;

import android.text.TextUtils;

/**
 * Created by chenjun on 16/3/3.
 */
public class AppConfig {

    public static final String ADDRESS = "http://www.weather.com.cn/data/list3/city";
    public static final String ADDRESS_SUFFIX = ".xml";
    public static final String WEATHER_INFO = "http://www.weather.com.cn/data/cityinfo/";
    public static final String WEATHER_INFO_SUFFIX = ".html";
    //public static final String WEATHER = "http://http://m.weather.com.cn/zs/101190404.html";

    public static String getAddress(final String code){
        String address;
        if (!TextUtils.isEmpty(code)){
            address = ADDRESS + code + ADDRESS_SUFFIX;
        }else{
            address = ADDRESS + ADDRESS_SUFFIX;
        }
        return address;
    }

    public static String getWeatherInfoAddress(final String code){
        String address = WEATHER_INFO+code+WEATHER_INFO_SUFFIX;
        return address;
    }
}
