package net.edrop.edrop_user.entity;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/29
 * Time: 21:21
 */
public class HotItem {
    private String id;
    private String content;

    public HotItem(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public HotItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
