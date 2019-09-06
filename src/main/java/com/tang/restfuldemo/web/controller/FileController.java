package com.tang.restfuldemo.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Description file 文件上传下载
 * @Author tang
 * @Date 2019-08-21 22:42
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping(value = "upload")
    public String upload(MultipartFile file) {
        System.out.println("file.getName() = " + file.getName());
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        System.out.println("file.getSize() = " + file.getSize());

        // 存储
        File saveFile = new File("/Users/tang/Desktop/software/ideaWorkspace/restful-demo/src/main/resources/a.txt");
        /*try {
            // 第一种
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 第二种
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(saveFile);) {
            byte[] temp = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(temp)) != -1) {
                outputStream.write(temp, 0, len);
            }

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saveFile.getAbsolutePath();
    }

    @GetMapping("download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) {
        System.out.println("id = " + id);

        File downFile = new File("/Users/tang/Desktop/software/ideaWorkspace/restful-demo/src/main/resources/a.txt");
        try (InputStream inputStream = new FileInputStream(downFile);
             OutputStream outputStream = response.getOutputStream();) {

            // 设置下载类型
            response.setContentType("application/x-download");
            // 设置下载之后的文件名 filename后面为文件名
            response.setHeader("Conent-Disposition", "attachment; filename=test.txt");

            // 第一种 利用common-io工具类
            //IOUtils.copy(inputStream, outputStream);

            // 第二种
            byte[] temp = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(temp)) != -1) {
                outputStream.write(temp, 0, len);
            }

            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
