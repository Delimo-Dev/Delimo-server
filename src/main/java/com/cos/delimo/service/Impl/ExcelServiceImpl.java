package com.cos.delimo.service.Impl;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.Member;
import com.cos.delimo.repository.MemberRepository;
import com.cos.delimo.service.ExcelService;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    private final MemberRepository memberRepository;

    @Autowired
    public ExcelServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Member> members = memberRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        createHeaderRow(sheet);
        fillDataRows(sheet, members);


        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void fillDataRows(Sheet sheet, List<Member> members) {
        int rowNum = 1;
        for (Member member : members){
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            row.createCell(colNum++).setCellValue(member.getId());
            row.createCell(colNum++).setCellValue(member.getEmail());
            row.createCell(colNum++).setCellValue(member.getNickname());
            row.createCell(colNum++).setCellValue(member.getResolution());

            List<Diary> diaries = member.getDiaryList();
            for(Diary diary : diaries){
                Row nextRow = sheet.createRow(rowNum++);
                int diaryColNum = colNum;
                nextRow.createCell(diaryColNum++).setCellValue(diary.getContent());
                nextRow.createCell(diaryColNum++).setCellValue(diary.getDiarySentiment().getSentiment());
                nextRow.createCell(diaryColNum).setCellValue(diary.getCreatedDate().toString());
            }
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("EMAIL");
        headerRow.createCell(2).setCellValue("NICKNAME");
        headerRow.createCell(3).setCellValue("RESOLUTION");
        headerRow.createCell(4).setCellValue("DIARIES");

        for(int i=0;i<headerRow.getLastCellNum();i++){
            headerRow.getCell(i).setCellStyle(headerStyle);
        }
    }
}
