package app.zhongjing.com.coolweather.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import app.zhongjing.com.coolweather.R;
import app.zhongjing.com.coolweather.adapter.ListAdapter;
import app.zhongjing.com.coolweather.db.CoolWeatherDB;
import app.zhongjing.com.coolweather.model.City;
import app.zhongjing.com.coolweather.ui.view.AutoSwipeRefreshLayout;

/**
 * Created by chenjun on 16/3/7.
 */
public class ChooseAreaCountyActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private AutoSwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private ListAdapter adapter;
    private CoolWeatherDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area_activity);

        findViewById();
        initView();
    }

    private void findViewById() {
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        refreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }

    @SuppressLint("NewApi")
    private void initView() {
        City city = (City) getIntent().getExtras().get(SELECTED_CITY);
        toolbar.setTitle(city.getCityName());
        toolbar.setNavigationIcon(getDrawable(R.mipmap.base_action_bar_back_normal));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });

        refreshLayout.setOnRefreshListener(refreshListener);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ListAdapter(this);
        recyclerView.setAdapter(adapter);
        db = CoolWeatherDB.getInstance(this);
        adapter.setCurrentLevel(ListAdapter.LEVEL_COUNTY);
        setSelectedCity(city);
        queryCounties(db, adapter);

    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

        }
    };


}
