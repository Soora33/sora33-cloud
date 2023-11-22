package com.sora.utils.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.io.IoUtil;
import com.sora.utils.ServletUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * @Classname ExcelUtils
 * @Description Excel常用方法
 * @Date 2023/07/01 14:04
 * @Author by Sora33
 */
public class ExcelUtils {

    public static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * MIME类型的TYPE 针对下载文件方法 根据文件的后缀名获取对应的MIME类型
     */
    private static final HashMap<String, String> MIME_MAP = new HashMap<>(){{
        // 文本文件
        put("txt", "text/plain");
        put("csv", "text/csv");
        put("html", "text/html");
        put("css", "text/css");
        put("xml", "text/xml");
        put("json", "application/json");

        // 图像文件
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("gif", "image/gif");
        put("bmp", "image/bmp");
        put("svg", "image/svg+xml");

        // 音频文件
        put("mp3", "audio/mpeg");
        put("wav", "audio/wav");
        put("ogg", "audio/ogg");
        put("flac", "audio/flac");

        // 视频文件
        put("mp4", "video/mp4");
        put("mov", "video/quicktime");
        put("avi", "video/x-msvideo");
        put("mkv", "video/x-matroska");

        // PDF 文件
        put("pdf", "application/pdf");

        // Excel 文件
        put("xls", "application/vnd.ms-excel");
        put("xlsx", "application/vnd.ms-excel");

        // Word 文件
        put("doc", "application/msword");
        put("docx", "application/msword");
    }};

    /**
     * List导出Excel文件
     * @param list list类型的数据
     * @param fileName 文件名
     * @param cls 类
     * @param <T>
     */
    public static <T> void exportToExcel(List<T> list, String fileName, Class<?> cls) {

        // 创建导出参数
        ExportParams exportParams = new ExportParams();
        // 装载Style风格
        exportParams.setStyle(ExcelStyle.class);

        // 获取Excel工作簿对象
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, cls, list);

        // 获取响应并设置响应头
        HttpServletResponse response = ServletUtils.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String encodedFileName = null;
        encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + encodedFileName + ".xlsx");

        try {
            // 写入到响应对象中
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            logger.error("IO写入错误！", e);
        }
    }


    /**
     * 导入Excel文件，读取为集合并返回
     * @param multipartFile
     * @param cls
     * @return
     */
    public static <T> List<T> importExcelToList(MultipartFile multipartFile, Class<T> cls) {
        // 将MultipartFile转换为File对象
        File file = null;
        try {
            file = File.createTempFile("temp", ".xls");
            // 这里相当于把上传的文件传输到了Java内的File文件内
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error("[Excel导入失败，IO读写异常！操作类：[{}]", cls);
        }

        // 使用EasyPoi库读取Excel文件并转换为实体类集合
        return ExcelImportUtil.importExcel(file, cls, new ImportParams());
    }


    /**
     * 下载文件
     * @param filePath
     */
    public static void downloadFile(String filePath) {
        File file = new File(filePath);

        // 获取文件名字 默认为最后一个/后的部分
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        // 获取文件后缀，用来设置ContentType
        String fileSuffix = null;
        try {
            fileSuffix = filePath.substring(filePath.lastIndexOf("."));
        } catch (Exception e) {
            logger.error("文件没有后缀名！使用二进制进行下载");
        }
        String contentType = MIME_MAP.get(fileSuffix) == null ? "application/octet-stream" : MIME_MAP.get(fileSuffix);

        HttpServletResponse response = ServletUtils.getResponse();
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        try (InputStream in = new FileInputStream(file); ServletOutputStream out = response.getOutputStream()) {
            IoUtil.copy(in, out);
        } catch (IOException e) {
            logger.error("下载文件失败！文件路径：[{}]", filePath);
        }
    }

}
