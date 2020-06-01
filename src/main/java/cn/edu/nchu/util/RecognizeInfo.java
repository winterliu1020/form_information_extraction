package cn.edu.nchu.util;

import lombok.Data;

/**
 * Created by liuwentao on 2020-04-01 15:48
 */
@Data
public class RecognizeInfo {
    private String message = "上传成功!";
    private int modelID = 0;
    private int recognizeCount = 0; // 识别数
    public RecognizeInfo(String message, int modelID, int recognizeCount) {
        this.message = message;
        this.modelID = modelID;
        this.recognizeCount = recognizeCount;
    }

}
