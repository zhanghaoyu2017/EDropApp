package net.edrop.edrop_user.entity;

import java.sql.Timestamp;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/25
 * Time: 18:24
 */
public class User {
    private Integer id;
    private String phone;
    private String qq;
    private String username;
    private String password;
    private String imgname;
    private String imgpath;
    private String address;
    private String gender;
    private	Timestamp registerTime;
    private String detailAddress;

    public String getDetailAddress() {
        return detailAddress;
    }
    public User(Integer id, String phone, String qq, String username, String password, String imgname, String imgpath,
                String address, String gender, Timestamp registerTime, String detailAddress) {
        super();
        this.id = id;
        this.phone = phone;
        this.qq = qq;
        this.username = username;
        this.password = password;
        this.imgname = imgname;
        this.imgpath = imgpath;
        this.address = address;
        this.gender = gender;
        this.registerTime = registerTime;
        this.detailAddress = detailAddress;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Timestamp getRegisterTime() {
        return registerTime;
    }
    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", phone=" + phone + ", qq=" + qq + ", username=" + username + ", password="
                + password + ", imgname=" + imgname + ", imgpath=" + imgpath + ", address=" + address + ", gender="
                + gender + ", registerTime=" + registerTime + ", detailAddress=" + detailAddress + "]";
    }
}
