package demo.security.servlet;

import demo.security.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;

@WebServlet("/files")
public class FileServlet extends HttpServlet {
    
    // Maintainability issue: Unused variable
    private static final String UPLOAD_DIR = "/tmp/uploads";
    private int unusedCounter = 0;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        // Maintainability issue: Unused local variable
        String debugInfo = "Processing file: " + data;
        
        Utils.deleteFile(data);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getParameter("file");
        String action = request.getParameter("action");
        
        // Maintainability issue: Boolean parameter anti-pattern
        processFile(filename, true, false, action);
    }
    
    // Maintainability issue: Too many boolean parameters - unclear what they mean
    private void processFile(String name, boolean flag1, boolean flag2, String action) {
        if (flag1 && !flag2) {
            // Do something
        } else if (!flag1 && flag2) {
            // Do something else
        }
    }
    
    // Maintainability issue: Empty method
    private void validateFile(String filename) {
        // TODO: Add validation
    }
    
    // Maintainability issue: Commented out code
    /*
    private void oldDeleteMethod(String file) {
        File f = new File(file);
        f.delete();
    }
    */
}
