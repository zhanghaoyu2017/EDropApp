package net.edrop.edrop_user.entity;

/**
 * Created by Android Studio.
 * User: zhanghaoyu
 * Date: 2019/12/11
 * Time: 17:24
 */
public class Wallet {
    private Integer id;
    private Integer pid;
    private Double money;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public Wallet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Wallet(Integer id, Integer pid, Double money) {
        super();
        this.id = id;
        this.pid = pid;
        this.money = money;
    }
    @Override
    public String toString() {
        return "Wallet [id=" + id + ", pid=" + pid + ", money=" + money + "]";
    }
}
