package com.tiansu.energy.ltc.module.demo;


import cn.hutool.core.util.StrUtil;
import com.tiansu.energy.ltc.common.domain.ResponseDTO;
import com.tiansu.energy.ltc.config.MinioConfig;
import com.tiansu.energy.ltc.third.MinioService;
import io.minio.errors.ErrorResponseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RequestMapping("/minio")
@RestController
@Api(tags = "文件上传接口")
@Slf4j
public class MinioController {


    @Resource
    private MinioService minioService;

    @Resource
    private MinioConfig minioConfig;


    @ApiOperation(value = "使用minio文件上传")
    @PostMapping("/uploadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "MultipartFile", name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(dataType = "String", name = "bucketName", value = "对象存储桶名称")
    })
    public ResponseDTO<String> uploadFile(MultipartFile file, String bucketName) {
        try {
            bucketName = StrUtil.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
            if (!minioService.bucketExists(bucketName)) {
                minioService.makeBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();
            String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));
            log.info("objectName:"+objectName);
            InputStream inputStream = file.getInputStream();
            minioService.putObject(bucketName, objectName, inputStream);
            inputStream.close();
            return ResponseDTO.success(minioService.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.failure(400,"上传失败");
        }
    }
    @ApiOperation(value = "使用minio文件下载")
    @GetMapping("/downloadFile")
    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) {
        try {
            //中文乱码问题没有解决1
            InputStream file = minioService.getObject(bucketName, fileName);
            String downloadFileName=new String(fileName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            if (StrUtil.isNotEmpty(originalName)) {
                downloadFileName = new String(originalName.getBytes("GBK"), StandardCharsets.ISO_8859_1);
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            } servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
