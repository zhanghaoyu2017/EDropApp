package net.edrop.edrop_user.entity;

/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/2
 * Time: 15:39
 * TODO：拍照识别结果的实体类
 */
public class Recognition {
    private int imgId;
    private String text;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
