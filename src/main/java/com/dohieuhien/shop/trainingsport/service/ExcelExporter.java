package com.dohieuhien.shop.trainingsport.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Component
public class ExcelExporter {
    public byte[] toXlsx(String sheetName, List<String> headers, List<List<Object>> rows) {
        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet(sheetName);

            CellStyle bold = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            bold.setFont(font);

            Row head = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell c = head.createCell(i);
                c.setCellValue(headers.get(i));
                c.setCellStyle(bold);
                sheet.setColumnWidth(i, 22 * 256);
            }
            int r = 1;
            for (List<Object> row : rows) {
                Row x = sheet.createRow(r++);
                for (int c = 0; c < row.size(); c++) {
                    Object v = row.get(c);
                    Cell cell = x.createCell(c);
                    if (v instanceof Number n) cell.setCellValue(n.doubleValue());
                    else if (v != null) cell.setCellValue(v.toString());
                }
            }
            wb.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
