import helpers.FileHandler;
import org.apache.commons.io.FileUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;

@MultipartConfig
public class Sss extends HttpServlet implements Servlet {
    private final StringBuffer stringBuffer = new StringBuffer();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("Servlet:SSS");
        String dir = getServletContext().getInitParameter("doc-dir");
        System.out.println(dir);

        String fileName = request.getParameter("file");
        if (fileName != null && !fileName.isEmpty()) {
            FileHandler fileHandler = new FileHandler(dir);
            File foundFile = fileHandler.GetSelectedFile(fileName);

            if (foundFile != null) {
                response.setContentType("application/msword");
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                response.setContentLength((int)foundFile.length());

                FileInputStream inputStream = new FileInputStream(foundFile);
                BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
                int readBytes = 0;
                while ((readBytes = bufferedStream.read()) != -1) {
                    response.getWriter().write(readBytes);
                }

            } else {
                response.getWriter().write("Unexpected error happened...");
            }
        }
    }
    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Part partFile = request.getPart("file");
        //String fileName = Paths.get(partFile.getSubmittedFileName()).getFileName().toString();
        String fileName = getSubmittedFileName(partFile);
        InputStream fileContent = partFile.getInputStream();

        File file = new File(getServletContext().getInitParameter("doc-dir") + "/" + fileName);
        FileUtils.copyInputStreamToFile(fileContent, file);

        response.getWriter().write("File has been saved");
    }
}
