package cn.edu.nchu.controller;

import cn.edu.nchu.util.FileInfo;
import cn.edu.nchu.util.FileUtil;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuwentao on 2020-03-16 09:23
 */
@Slf4j
@Controller
public class FileController {


    private String folder = "/Users/liuwentao/Code/upload/";

    /**
     * @name: upload
     * @description: 上传文件
     * @param request
     * @param file
     * @return: org.apache.tomcat.jni.FileInfo
     * @date: 2020-03-16 11:37
     * @auther: liuwentao
     *
    */
    @ResponseBody
    @RequestMapping("/file")
    public FileInfo upload(HttpServletRequest request,
                           @RequestParam(value = "modelWord", required = false)MultipartFile file) throws Exception {
        log.info("【FileController】filename={}, fileOriginName={}, fileSize={}", file.getName(), file.getOriginalFilename(), file.getSize());
        log.info(request.getContextPath());
        String newFileName = FileUtil.upload(folder, file, request);
        if (newFileName != null) {
            String fileURL = request.getRequestURI().substring(0, request.getRequestURL().lastIndexOf("/")) + "/upload/" + newFileName;
            return new FileInfo(1, "上传成功",fileURL);
        } else {
            return new FileInfo(0, "上传失败",null);
        }
    }

    /**
     * @name: download
     * @description: 下载文件
     * @param fileName
     * @param request
     * @param response
     * @return: void
     * @date: 2020-03-16 12:16
     * @auther: liuwentao
     *
    */
    @RequestMapping("file/{fileName}")
    public void download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
        if (FileUtil.download(folder+fileName,fileName, request, response) == 0) {
            request.setAttribute("message", "下载资源失败！！");
        }
    }
}
