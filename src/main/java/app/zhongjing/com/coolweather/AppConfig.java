package app.zhongjing.com.coolweather;

import android.text.TextUtils;

/**
 * Created by chenjun on 16/3/3.
 */
public class AppConfig {

    public static final String ADDRESS = "http://www.weather.com.cn/data/list3/city";
    public static final String ADDRESS_SUFFIX = ".xml";

    public static String getAddress(final String code){
        String address;
        if (!TextUtils.isEmpty(code)){
            address = ADDRESS + code + ADDRESS_SUFFIX;
        }else{
            address = ADDRESS + ADDRESS_SUFFIX;
        }
        return address;
    }
}
