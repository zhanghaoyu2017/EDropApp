package net.edrop.edrop_user.entity;

import java.sql.Timestamp;

/**
 * Created by Android Studio.
 * User: zhanghaoyu
 * Date: 2019/12/9
 * Time: 15:59
 */
public class Order {
    private Integer id;
    private String number;
    private Integer userId;
    private Integer employeeId;
    private Timestamp reserveTime;
    private Timestamp createTime;
    private Timestamp finishTime;
    private int state;
    private User user;
    private Employee employee;
    private int userDeleteState;
    private int employeeDeleteState;
    private String orderAddress;
    public Timestamp getReserveTime() {
        return reserveTime;
    }
    public void setReserveTime(Timestamp reserveTime) {
        this.reserveTime = reserveTime;
    }
    public String getOrderAddress() {
        return orderAddress;
    }
    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Order(Integer id, String number, Integer userId, Integer employeeId, Timestamp reserveTime,
                 Timestamp createTime, Timestamp finishTime, int state, User user, Employee employee, int userDeleteState,
                 int employeeDeleteState, String orderAddress) {
        super();
        this.id = id;
        this.number = number;
        this.userId = userId;
        this.employeeId = employeeId;
        this.reserveTime = reserveTime;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.state = state;
        this.user = user;
        this.employee = employee;
        this.userDeleteState = userDeleteState;
        this.employeeDeleteState = employeeDeleteState;
        this.orderAddress = orderAddress;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public int getUserDeleteState() {
        return userDeleteState;
    }
    public void setUserDeleteState(int userDeleteState) {
        this.userDeleteState = userDeleteState;
    }
    public int getEmployeeDeleteState() {
        return employeeDeleteState;
    }
    public void setEmployeeDeleteState(int employeeDeleteState) {
        this.employeeDeleteState = employeeDeleteState;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public Timestamp getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }
    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    @Override
    public String toString() {
        return "Order [id=" + id + ", number=" + number + ", userId=" + userId + ", employeeId=" + employeeId
                + ", reserveTime=" + reserveTime + ", createTime=" + createTime + ", finishTime=" + finishTime
                + ", state=" + state + ", user=" + user + ", employee=" + employee + ", userDeleteState="
                + userDeleteState + ", employeeDeleteState=" + employeeDeleteState + ", orderAddress=" + orderAddress
                + "]";
    }
    public Order() {
        super();
        // TODO Auto-generated constructor stub
    }
}
