package app.zhongjing.com.coolweather.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import app.zhongjing.com.coolweather.model.City;
import app.zhongjing.com.coolweather.model.Province;
import app.zhongjing.com.coolweather.ui.BaseActivity;
import app.zhongjing.com.coolweather.ui.ChooseAreaCityActivity;
import app.zhongjing.com.coolweather.ui.ChooseAreaCountyActivity;

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
}
