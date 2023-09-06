package com.sora.utils.excel;

import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

/**
 * @Classname ExcelStyle
 * @Description Excel自定义风格
 * @Date 2023/07/01 18:30
 * @Author by Sora33
 */
public class ExcelStyle extends AbstractExcelExportStyler implements IExcelExportStyler {

    public ExcelStyle(Workbook workbook) {
        super.createStyles(workbook);
    }

    @Override
    public CellStyle getHeaderStyle(short headerColor) {
        CellStyle headerStyle = workbook.createCellStyle();
        // 首行字体加粗
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        // 水平 居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置表格头颜色 灰色
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 填充样式 纯色填充
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 左边框 实线
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 右边框 实线
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 自动换行
        headerStyle.setWrapText(true);
        return headerStyle;
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        // 首行字体加粗
        Font font = workbook.createFont();
        font.setBold(true);
        titleStyle.setFont(font);
        // 水平 居中
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置表格头颜色 灰色
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 填充样式 纯色填充
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 左边框 实线
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 右边框 实线
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 自动换行
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle contentStyle = workbook.createCellStyle();
        // 水平 居中
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左边框 实线
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 右边框 实线
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 上边框 实线
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 下边框 实线
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 自动换行
        contentStyle.setWrapText(true);
        return contentStyle;
    }

    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle contentStyle = workbook.createCellStyle();
        // 水平 居中
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左边框 实线
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 右边框 实线
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 上边框 实线
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 下边框 实线
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 自动换行
        contentStyle.setWrapText(true);
        return contentStyle;
    }
}
