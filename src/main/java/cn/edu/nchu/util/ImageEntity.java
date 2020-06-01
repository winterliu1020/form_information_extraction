package cn.edu.nchu.util;

/**
 * Created by liuwentao on 2020-03-21 11:44
 */
public class ImageEntity {
    private String image_url;
    private int num;

    public ImageEntity(String image_url, int num) {
        this.image_url = image_url;
        this.num = num;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
