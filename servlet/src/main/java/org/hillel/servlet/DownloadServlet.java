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

//@WebServlet(urlPatterns = "/download/*", displayName = "downloadServlet", loadOnStartup = 1)
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String[] arrayUrl = url.split("/");
        String fileName = arrayUrl[arrayUrl.length-1];
        File file = new File(req.getServletContext().getRealPath("/WEB-INF/" + fileName));
//        System.out.println(file.length());
        resp.setContentType("image/jpg");
        resp.setHeader("Content-Disposition", "inline");
        resp.setContentLength((int) file.length());
        try (ServletOutputStream outputStream = resp.getOutputStream();
             BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            int readBytes = 0;
            while ((readBytes = inputStream.read()) != -1) {
                outputStream.write(readBytes);
            }
        } catch (ExportException e) {
            e.printStackTrace();
        }
    }
}
