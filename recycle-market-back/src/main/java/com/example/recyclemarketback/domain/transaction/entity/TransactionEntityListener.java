package com.example.recyclemarketback.domain.transaction.entity;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.PreUpdate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class TransactionEntityListener {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/report.xlsx";

    private Workbook workbook;
    private Sheet sheet;
    private int rowIndex = 0;

    @PostConstruct
    public void init() throws IOException {
        // 엑셀 파일 생성
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();
            createHeaderRow();
        } else {
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
            rowIndex = sheet.getLastRowNum() + 1;
        }
    }

    private void createHeaderRow() {
        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Product ID");
        headerRow.createCell(2).setCellValue("Product Name");
        headerRow.createCell(3).setCellValue("Transaction State");
        headerRow.createCell(4).setCellValue("Seller ID");
        headerRow.createCell(5).setCellValue("Seller Number");
        headerRow.createCell(6).setCellValue("Buyer Number");
        headerRow.createCell(7).setCellValue("Tracking Number");
        headerRow.createCell(8).setCellValue("Payment Method");
        headerRow.createCell(9).setCellValue("Date");

        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreUpdate
    public void preUpdate(TransactionEntity entity) {
        // 상태 변경 로그 작성
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(entity.getId());
        row.createCell(1).setCellValue(entity.getProduct().getId());
        row.createCell(2).setCellValue(entity.getProduct().getName());
        row.createCell(3).setCellValue(entity.getTransactionState().name());
        row.createCell(4).setCellValue(entity.getSeller().getId());
        row.createCell(5).setCellValue(entity.getSeller().getPhoneNumber());
        row.createCell(6).setCellValue(entity.getBuyer().getId());
        row.createCell(7).setCellValue(entity.getBuyer().getPhoneNumber());
        row.createCell(8).setCellValue(entity.getPaymentMethod() != null ? entity.getPaymentMethod().name() : null);
        row.createCell(9).setCellValue(new Date().toString());


        // 변경된 로그를 엑셀 파일에 저장
        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

