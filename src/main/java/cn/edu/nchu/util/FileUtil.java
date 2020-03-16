package cn.edu.nchu.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by liuwentao on 2020-03-16 11:40
 */
public class FileUtil {

    /**
     * @name: upload
     * @description: 文件上传
     * @param path
     * @param file
     * @param request
     * @return: java.lang.String
     * @date: 2020-03-16 11:41
     * @auther: liuwentao
     *
    */
    public static String upload(String path, MultipartFile file, HttpServletRequest request) {

        // 文件夹不存在就新建一个
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 判断附件是否为空，为空就附件字段为null
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1); // 文件后缀
            String newFileName = new Date().getTime() + "." + suffix;
            // 构建文件对象
            File file1 = new File(path, newFileName);
            // 执行上传操作
            try {
                file.transferTo(file1);
                return file1.getName();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * @name: download
     * @description: 文件下载
     * @param filePath
     * @param fileName
     * @param request
     * @param response
     * @return: int
     * @date: 2020-03-16 11:51
     * @auther: liuwentao
     *
    */
    public static int download(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 得到要下载的文件
            File file = new File(filePath);
            if (!file.exists()) {
                return 0;
            }
            // 设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.setCharacterEncoding("UTF-8");
            // 读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(file);
            // 创建输出流
            OutputStream out = response.getOutputStream();
            // 创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区中
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            // 关闭文件输入输出流
            in.close();
            out.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
