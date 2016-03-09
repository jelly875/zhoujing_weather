package app.zhongjing.com.coolweather.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import app.zhongjing.com.coolweather.model.City;
import app.zhongjing.com.coolweather.model.County;
import app.zhongjing.com.coolweather.model.Province;
import app.zhongjing.com.coolweather.ui.BaseActivity;
import app.zhongjing.com.coolweather.ui.ChooseAreaActivity;
import app.zhongjing.com.coolweather.ui.ChooseAreaCityActivity;
import app.zhongjing.com.coolweather.ui.ChooseAreaCountyActivity;
import app.zhongjing.com.coolweather.ui.WeatherInfoActivity;

/**
 * Created by chenjun on 16/3/4.
 */
public class OpenActivityUtil {

    public static void goToCityActivity(Context context,Province province){
        Intent intent = new Intent(context,ChooseAreaCityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseActivity.SELECTED_PROVINCE,province);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void goToCountyActivity(Context context,City city){
        Intent intent = new Intent(context, ChooseAreaCountyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseActivity.SELECTED_CITY,city);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public static void goToWeatherInfoActivity(Context context,County county){
        Intent intent = new Intent(context, WeatherInfoActivity.class);
        intent.putExtra("county_code", county.getCountyCode());
        intent.putExtra("from_selected",true);
        context.startActivity(intent);
    }

    public static void goToProvinceActivity(Context context){
        Intent intent = new Intent(context, ChooseAreaActivity.class);
        context.startActivity(intent);
    }
}
