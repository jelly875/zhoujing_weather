package app.zhongjing.com.coolweather.model;

/**
 * Created by chenjun on 16/3/2.
 */
public class Province {

    private int id;
    private String provinceName;
    private String provinveCode;

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getProvinveCode() {
        return provinveCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setProvinveCode(String provinveCode) {
        this.provinveCode = provinveCode;
    }
}
