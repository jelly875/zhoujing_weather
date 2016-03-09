package app.zhongjing.com.coolweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import app.zhongjing.com.coolweather.service.AutoUpdateService;

/**
 * Created by chenjun on 16/3/8.
 */
public class AutoUpdateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, AutoUpdateService.class);
        context.startService(intent1);
    }
}
