package cn.edu.nchu.util;

import lombok.Data;

/**
 * Created by liuwentao on 2020-03-16 12:13
 */
@Data
public class FileInfo {
    private int success = 1;
    private String message = "上传成功!";
    private String url;

    public FileInfo(int success,String message,String url) {
        this.success = success;
        this.message = message;
        this.url = url;
    }
}
