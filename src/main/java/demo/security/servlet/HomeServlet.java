package demo.security.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/helloWorld")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Maintainability issue: Unused fields
    private int requestCount = 0;
    private String serverName = "ProductionServer";

    public HomeServlet() {
        super();
        // Maintainability issue: Auto-generated comment left in code
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // Reliability issue: No null check before calling trim() - NullPointerException risk
        String name = request.getParameter("name").trim();
        
        // Maintainability issue: Unused variable
        long timestamp = System.currentTimeMillis();
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<h2>Hello "+name+ "</h2>");
        out.close();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // Maintainability issue: Auto-generated comment
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    
    // Maintainability issue: Method with complex nested conditions
    private boolean validateRequest(HttpServletRequest request) {
        if (request != null) {
            if (request.getParameter("name") != null) {
                if (!request.getParameter("name").isEmpty()) {
                    if (request.getParameter("name").length() > 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
