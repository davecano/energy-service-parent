package com.tiansu.energy.ltc.module.demo;

import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.tiansu.energy.ltc.module.demo.domain.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@RestController
@Slf4j
public class ExcelController {
    @GetMapping("download")
    public void Easydownload(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Student.class).sheet("模板").doWrite(data());
    }

    private List<Student> data() {
        List<Student> list = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            Student data = new Student();
            data.setName("姓名" + i);
            data.setIdCard("111111111");
            data.setAge(i+10);
            data.setAddress("南京");
            data.setGender("男");
            list.add(data);
        }
        return list;
    }

    @GetMapping("downloadTemplate")
    public void Templatedownload(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");


        // 从 /resources 目录下加载模板
        try (InputStream templateInputStream = getClass().getClassLoader().getResourceAsStream("excel-template/学生名单模板.xlsx");
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 设置输出目标和模板
            ExcelWriter excelWriter = EasyExcel.write(out).withTemplate(templateInputStream).build();
            // 创建 Sheet
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 注意：forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。这会把所有数据放到内存，数据量大时会很耗内存
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(data(), fillConfig, writeSheet);
            // 填充普通变量
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("className", "软件工程3班");
            map.put("date", new Date());
            excelWriter.fill(map, writeSheet);
            excelWriter.finish();
            // 将 outputStream 转换成 InputStream 并上传到 oss 中
            try (InputStream in = new ByteArrayInputStream(out.toByteArray())) {
                IoUtil.copy(in,response.getOutputStream());
                // todo 在这里处理这个 InputStream，可以上传到 OSS 并返回下载接，也可以做为响应返回给用户
            }
        } catch (IOException e) {
            log.error("export excel error", e);
        }

    }
}
