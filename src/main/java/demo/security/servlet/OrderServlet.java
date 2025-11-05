package demo.security.servlet;

import demo.security.util.DBUtils;
import demo.security.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    
    // Maintainability issue: Hard-coded credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shop";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "password123";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        String customerId = request.getParameter("customerId");
        
        // Reliability issue: No null check for required parameters
        int orderIdInt = Integer.parseInt(orderId); // Can throw NumberFormatException
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Maintainability issue: Complex conditional logic without extraction
        if (orderId != null && !orderId.isEmpty() && orderId.length() > 0 && orderId.matches("\\d+") && Integer.parseInt(orderId) > 0) {
            try {
                DBUtils db = new DBUtils();
                List<String> orders = db.findOrders(orderId);
                
                // Reliability issue: Potential null pointer - findOrders can return null
                if (orders.size() > 0) {
                    out.print("<h1>Orders</h1>");
                    orders.forEach((order) -> {
                        out.print("<div>" + order + "</div>");
                    });
                }
            } catch (Exception e) {
                // Reliability issue: Generic exception catch
                e.printStackTrace();
            }
        }
        
        // Reliability issue: Resource (out) not closed in finally block
        out.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Maintainability issue: Giant if-else chain instead of strategy pattern
        if (action == null) {
            out.print("No action");
        } else if (action.equals("create")) {
            handleCreateOrder(request, out);
        } else if (action.equals("update")) {
            handleUpdateOrder(request, out);
        } else if (action.equals("delete")) {
            handleDeleteOrder(request, out);
        } else if (action.equals("list")) {
            handleListOrders(request, out);
        } else if (action.equals("search")) {
            handleSearchOrders(request, out);
        } else if (action.equals("export")) {
            handleExportOrders(request, out);
        } else {
            out.print("Unknown action");
        }
        
        out.close();
    }
    
    // Maintainability issue: Code duplication across multiple methods below
    private void handleCreateOrder(HttpServletRequest request, PrintWriter out) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
            String orderId = request.getParameter("orderId");
            String customerId = request.getParameter("customerId");
            // SQL Injection vulnerability + No resource cleanup
            String sql = "INSERT INTO orders VALUES ('" + orderId + "', '" + customerId + "')";
            stmt.executeUpdate(sql);
            out.print("<h1>Order Created</h1>");
        } catch (Exception e) {
            // Reliability issue: Exception printed to response
            out.print("Error: " + e.getMessage());
        }
        // Reliability issue: Resources not closed - connection and statement leak
    }
    
    private void handleUpdateOrder(HttpServletRequest request, PrintWriter out) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
            String orderId = request.getParameter("orderId");
            String status = request.getParameter("status");
            // Maintainability issue: Duplicate code from handleCreateOrder
            String sql = "UPDATE orders SET status = '" + status + "' WHERE order_id = '" + orderId + "'";
            stmt.executeUpdate(sql);
            out.print("<h1>Order Updated</h1>");
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
        // Reliability issue: Resources not closed
    }
    
    private void handleDeleteOrder(HttpServletRequest request, PrintWriter out) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
            String orderId = request.getParameter("orderId");
            // Maintainability issue: More duplicate code
            String sql = "DELETE FROM orders WHERE order_id = '" + orderId + "'";
            stmt.executeUpdate(sql);
            out.print("<h1>Order Deleted</h1>");
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
        // Reliability issue: Resources not closed
    }
    
    private void handleListOrders(HttpServletRequest request, PrintWriter out) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
            out.print("<h1>All Orders</h1>");
            while (rs.next()) {
                // Reliability issue: Potential SQL exception on invalid column index
                out.print("<div>" + rs.getString(1) + " - " + rs.getString(2) + "</div>");
            }
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
        // Reliability issue: Multiple resources not closed (conn, stmt, rs)
    }
    
    private void handleSearchOrders(HttpServletRequest request, PrintWriter out) {
        // Maintainability issue: Duplicate connection code (4th time)
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            String searchTerm = request.getParameter("search");
            // SQL Injection + resource leak
            String sql = "SELECT * FROM orders WHERE order_id LIKE '%" + searchTerm + "%'";
            ResultSet rs = stmt.executeQuery(sql);
            out.print("<h1>Search Results</h1>");
            while (rs.next()) {
                out.print("<div>" + rs.getString(1) + "</div>");
            }
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
    }
    
    private void handleExportOrders(HttpServletRequest request, PrintWriter out) {
        // Maintainability issue: Duplicate connection code (5th time)
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
            out.print("order_id,customer_id,status\n");
            while (rs.next()) {
                // Reliability issue: No escaping for CSV export
                out.print(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "\n");
            }
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
    }
    
    // Maintainability issue: Method with too many parameters
    private void processOrder(String orderId, String customerId, String productId, 
                            String quantity, String price, String discount, 
                            String shippingAddress, String billingAddress, 
                            String paymentMethod, String status) {
        // Maintainability issue: Empty method body
        // TODO: Implement this method
    }
    
    // Reliability issue: Method that can return different types in array
    private Object[] getMixedData() {
        Object[] data = new Object[3];
        data[0] = "String";
        data[1] = 123;
        data[2] = new ArrayList<String>();
        return data;
    }
}

