package demo.security.servlet;

import demo.security.util.DBUtils;
import demo.security.util.SessionHeader;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        try {
            DBUtils db = new DBUtils();
            List<String> users = db.findUsers(user);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            // Reliability issue: Potential null pointer exception - users could be null
            users.forEach((result) -> {
                        out.print("<h2>User "+result+ "</h2>");
            });
            // Reliability issue: Resource not closed properly in catch block
            out.close();
        } catch (Exception e) {
            // Reliability issue: Generic exception catch - swallows specific exceptions
            throw new RuntimeException(e);
        }

    }

    private SessionHeader getSessionHeader(HttpServletRequest request) {
        String sessionAuth = request.getHeader("Session-Auth");
        if (sessionAuth != null) {
            try {
                byte[] decoded = Base64.decodeBase64(sessionAuth);
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(decoded));
                return (SessionHeader) in.readObject();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionHeader sessionHeader = getSessionHeader(request);
        if (sessionHeader == null) return;
        String user = sessionHeader.getUsername();
        try {
            DBUtils db = new DBUtils();
            List<String> users = db.findUsers(user);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            users.forEach((result) -> {
                out.print("<h2>User "+result+ "</h2>");
            });
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // Maintainability issue: Duplicate string literals
    private String buildResponse(String type) {
        String html = "<html><body>";
        if (type.equals("success")) {
            html = html + "<h1>Success</h1></body></html>";
        } else if (type.equals("error")) {
            html = html + "<h1>Error</h1></body></html>";
        } else {
            html = html + "<h1>Unknown</h1></body></html>";
        }
        return html;
    }
    
    // Reliability issue: Collection.toArray() without proper type
    private Object[] convertList(List<String> items) {
        // Reliability issue: Returning Object[] instead of String[]
        return items.toArray();
    }
}
