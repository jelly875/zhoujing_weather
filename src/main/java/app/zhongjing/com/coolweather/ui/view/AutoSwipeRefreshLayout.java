package app.zhongjing.com.coolweather.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import app.zhongjing.com.coolweather.R;

/**
 * Created by chenjun on 16/3/3.
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout{

    public AutoSwipeRefreshLayout(Context context) {
        super(context);
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void autoRefresh(){
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View view = (View)mCircleView.get(this);
            view.setVisibility(VISIBLE);
            Method refreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class,boolean.class);
            refreshing.setAccessible(true);
            refreshing.invoke(this,true,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setColorSchemeResources(R.color.main_color, R.color.kyblue, R.color.menu_bac, R.color.pur);
    }
}
