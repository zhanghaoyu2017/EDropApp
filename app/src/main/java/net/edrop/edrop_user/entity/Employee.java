package net.edrop.edrop_user.entity;

import java.sql.Timestamp;

/**
 * Created by Android Studio.
 * User: zhanghaoyu
 * Date: 2019/12/9
 * Time: 16:01
 */
public class Employee {
    private Integer id;
    private String phone;
    private String qq;
    private String username;
    private String password;
    private String imgname;
    private String imgpath;
    private Timestamp registerTime;
    private String gender;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Employee(Integer id, String phone, String qq, String username, String password, String imgname,
                    String imgpath, Timestamp registerTime, String gender) {
        super();
        this.id = id;
        this.phone = phone;
        this.qq = qq;
        this.username = username;
        this.password = password;
        this.imgname = imgname;
        this.imgpath = imgpath;
        this.registerTime = registerTime;
        this.gender = gender;
    }
    public String getImgname() {
        return imgname;
    }
    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
    public String getImgpath() {
        return imgpath;
    }
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
    public Timestamp getRegisterTime() {
        return registerTime;
    }
    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Employee() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "Employee [id=" + id + ", phone=" + phone + ", qq=" + qq + ", username=" + username + ", password="
                + password + ", imgname=" + imgname + ", imgpath=" + imgpath + ", registerTime=" + registerTime
                + ", gender=" + gender + "]";
    }
}
