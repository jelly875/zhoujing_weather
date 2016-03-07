package app.zhongjing.com.coolweather.http;

/**
 * Created by chenjun on 16/3/3.
 */
public interface HttpCallbackListener {

    void onSuccess(String response);
    void onFail(Exception e);
}
