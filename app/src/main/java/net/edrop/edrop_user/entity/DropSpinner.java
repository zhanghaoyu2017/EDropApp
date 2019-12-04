package net.edrop.edrop_user.entity;

/**
 * 由于在下拉框列表中需要显示一张图片，和一个标题。
 * 因此这个类只需要两个字段，一个ImgId字段用来存放图片，一个title字段用来存放标题。
 *
 * Created by mysterious
 * User: mysterious
 * Date: 2019/12/4
 * Time: 8:41
 */
public class DropSpinner {
    private String title;

    public DropSpinner(String title) {
        this.title = title;
    }

    public DropSpinner() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
