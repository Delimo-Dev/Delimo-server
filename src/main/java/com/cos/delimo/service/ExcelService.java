package com.cos.delimo.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ExcelService {
    void exportToExcel(HttpServletResponse response) throws IOException;
}
