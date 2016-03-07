package app.zhongjing.com.coolweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.zhongjing.com.coolweather.R;
import app.zhongjing.com.coolweather.model.City;
import app.zhongjing.com.coolweather.model.County;
import app.zhongjing.com.coolweather.model.Province;
import app.zhongjing.com.coolweather.util.OpenActivityUtil;

/**
 * Created by chenjun on 16/3/3.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.AreaVH>{

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    public int currentLevel;

    private LayoutInflater inflater;
    private Context context;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    public ListAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setCurrentLevel(int currentLevel){
        this.currentLevel = currentLevel;
    }

    public void setProvinceList(List<Province> listData){
        provinceList = listData;
        notifyDataSetChanged();
    }

    public void setCityList(List<City> cityList){
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    public void setCountyList(List<County> countyList){
        this.countyList = countyList;
        notifyDataSetChanged();
    }

    @Override
    public AreaVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view,parent,false);

        return new AreaVH(view);
    }

    @Override
    public void onBindViewHolder(AreaVH holder, int position) {
        if(currentLevel == LEVEL_PROVINCE){
            holder.textView.setText(provinceList.get(position).getProvinceName());
            holder.textView.setTag(provinceList.get(position).getProvinveCode());
        }else if(currentLevel == LEVEL_CITY){
            holder.textView.setText(cityList.get(position).getCityName());
        }else if(currentLevel == LEVEL_COUNTY){
            holder.textView.setText(countyList.get(position).getCountyName());
        }
    }

    @Override
    public int getItemCount() {
        if(currentLevel == LEVEL_PROVINCE){
            if (provinceList != null){
                return provinceList.size();
            }
        }else if(currentLevel == LEVEL_CITY){
            if (cityList != null){
                return cityList.size();
            }
        }else if(currentLevel == LEVEL_COUNTY){
            if (countyList != null){
                return countyList.size();
            }
        }
        return 0;
    }

    class AreaVH extends RecyclerView.ViewHolder{
        private TextView textView;
        public AreaVH(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentLevel == LEVEL_PROVINCE){
                        OpenActivityUtil.goToCityActivity(context,provinceList.get(getPosition()));
                    } else if (currentLevel == LEVEL_CITY){
                        OpenActivityUtil.goToCountyActivity(context,cityList.get(getPosition()));
                    } else if(currentLevel == LEVEL_COUNTY){

                    }

                }
            });
        }
    }
}
