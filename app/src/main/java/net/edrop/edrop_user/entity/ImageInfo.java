package net.edrop.edrop_user.entity;

public class ImageInfo {
    private int id;
    private String title;
    private String date;
    private String image;
    private String url;

    public ImageInfo() {
    }

    public ImageInfo(int id, String title, String date, String image, String url) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
