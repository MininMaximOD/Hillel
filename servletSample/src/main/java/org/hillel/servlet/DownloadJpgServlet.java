package org.hillel.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.server.ExportException;

public class DownloadJpgServlet extends HttpServlet {

    private String fileName = "1.jpg";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = new File(fileName);
        try(ServletOutputStream outputStream = resp.getOutputStream();
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))){
            resp.setContentType("1jpg.jpg");
            int readBytes = 0;
            while ((readBytes = inputStream.read()) != -1)
                outputStream.write(readBytes);
        }catch (ExportException e){
            e.printStackTrace();
        }
        super.doGet(req, resp);
    }
}
