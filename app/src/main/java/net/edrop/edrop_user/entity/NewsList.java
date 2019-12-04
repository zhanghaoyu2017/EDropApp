package net.edrop.edrop_user.entity;

/**
 * Created by Android Studio.
 * User: zhanghaoyu
 * Date: 2019/12/3
 * Time: 19:38
 */
public class NewsList {
    private String name;
    private Integer trust;
    private Integer lajitype;
    private String lajitip;

    public NewsList(String name, Integer trust, Integer lajitype, String lajitip) {
        this.name = name;
        this.trust = trust;
        this.lajitype = lajitype;
        this.lajitip = lajitip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTrust() {
        return trust;
    }

    public void setTrust(Integer trust) {
        this.trust = trust;
    }

    public Integer getLajitype() {
        return lajitype;
    }

    public void setLajitype(Integer lajitype) {
        this.lajitype = lajitype;
    }

    public String getLajitip() {
        return lajitip;
    }

    public void setLajitip(String lajitip) {
        this.lajitip = lajitip;
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "name='" + name + '\'' +
                ", trust=" + trust +
                ", lajitype=" + lajitype +
                ", lajitip='" + lajitip + '\'' +
                '}';
    }
}
