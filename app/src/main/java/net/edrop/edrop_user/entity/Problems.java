package net.edrop.edrop_user.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Problems implements Serializable {
    private int id;
    private String question;
    private int type_id;
    public Problems() {
    }
    public Problems(int id,int type_id,String question) {
        this.id=id;
        this.type_id=type_id;
        this.question=question;
    }


    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", type_id=" + type_id +
                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }


}
