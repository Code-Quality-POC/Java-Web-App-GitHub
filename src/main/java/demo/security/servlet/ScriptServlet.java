package demo.security.servlet;

import demo.security.util.Utils;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/scripts")
public class ScriptServlet extends HttpServlet {
    
    // Maintainability issue: Instance variable that should be local or injected
    private int requestCount = 0;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        
        // Reliability issue: Not thread-safe increment
        requestCount++;
        
        try {
            Utils.executeJs(data);
            response.setContentType("text/html");
            response.getWriter().println("<h1>Script executed</h1>");
            // Reliability issue: Writer not closed
        } catch (ScriptException e) {
            // Reliability issue: Generic RuntimeException thrown
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Maintainability issue: Duplicate code from doPost
        String data = request.getParameter("data");
        requestCount++; // Reliability issue: Not thread-safe
        
        try {
            Utils.executeJs(data);
            response.setContentType("text/html");
            response.getWriter().println("<h1>Script executed</h1>");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Reliability issue: Method accessing non-thread-safe counter
    public int getRequestCount() {
        return requestCount;
    }
}