package app.zhongjing.com.coolweather.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import app.zhongjing.com.coolweather.R;
import app.zhongjing.com.coolweather.adapter.ListAdapter;
import app.zhongjing.com.coolweather.db.CoolWeatherDB;
import app.zhongjing.com.coolweather.ui.view.AutoSwipeRefreshLayout;

/**
 * Created by chenjun on 16/3/3.
 */
public class ChooseAreaActivity extends BaseActivity {

    private AutoSwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private LinearLayoutManager mLinearLayoutManager;
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
        mSwipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar_layout);
    }

    private void initView() {
        toolbar.setTitle(R.string.province_title);
        setSupportActionBar(toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new ListAdapter(this);
        recyclerView.setAdapter(adapter);
        db = CoolWeatherDB.getInstance(this);
        appBarLayout.addOnOffsetChangedListener(
                new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (verticalOffset == 0){
                            mSwipeRefreshLayout.setEnabled(true);
                        }else{
                            mSwipeRefreshLayout.setEnabled(false);
                        }
                    }
                }
        );
        queryProvince(db, adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.setCurrentLevel(ListAdapter.LEVEL_PROVINCE);
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

        }
    };


}
