package net.edrop.edrop_user.entity;

/**
 * Created by Android Studio.
 * User: zhanghaoyu
 * Date: 2019/12/2
 * Time: 14:39
 */

/**
 * @author 13071
 * @ClassName: Type
 * @Description:
 * @date 2019年11月28日
 */
public class Type {
    private Integer id;
    private String name;
    private String descr;
    private String demand;
    private String formula;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Type(Integer id, String name, String descr, String demand, String formula) {
        super();
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.demand = demand;
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "Type [id=" + id + ", name=" + name + ", descr=" + descr + ", demand=" + demand + ", formula=" + formula
                + "]";
    }

    public Type() {
        super();
        // TODO Auto-generated constructor stub
    }
}
