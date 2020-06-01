package cn.edu.nchu.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwentao on 2020-03-18 17:23
 */
public class PicUtil {
    public static void jointPic(List<File> files, String path) {
        try {
            Integer allWidth = 0;    // 图片总宽度
            Integer allHeight = 0;    // 图片总高度
            List<BufferedImage> imgs = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                imgs.add(ImageIO.read(files.get(i)));
                //竖向
                if (i == 0) {
                    allWidth = imgs.get(0).getWidth();
                }
                allHeight += imgs.get(i).getHeight();
            }
            BufferedImage combined = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_RGB);
            // paint both images, preserving the alpha channels
            Graphics g = combined.getGraphics();
            // 竖向合成
            Integer height = 0;
            for (int i = 0; i < imgs.size(); i++) {
                g.drawImage(imgs.get(i), 0, height, null);
                height += imgs.get(i).getHeight();
            }
            ImageIO.write(combined, "jpg", new File(path));
            System.out.println("===合成成功====");
        } catch (Exception e) {
            System.out.println("===合成失败====");
            e.printStackTrace();
        }
    }
}
