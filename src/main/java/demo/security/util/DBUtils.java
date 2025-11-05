package demo.security.util;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    Connection connection;
    public DBUtils() throws SQLException {
        connection = DriverManager.getConnection(
                "mYJDBCUrl", "myJDBCUser", "myJDBCPass");
    }

    public List<String> findUsers(String user) throws Exception {
        String query = "SELECT userid FROM users WHERE username = '" + user  + "'";
        // Reliability issue: Statement and ResultSet not closed - resource leak
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<String> users = new ArrayList<String>();
        while (resultSet.next()){
            users.add(resultSet.getString(0));
        }
        return users;
    }

    public List<String> findItem(String itemId) throws Exception {
        String query = "SELECT item_id FROM items WHERE item_id = '" + itemId  + "'";
        // Reliability issue: Statement and ResultSet not closed - resource leak
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<String> items = new ArrayList<String>();
        while (resultSet.next()){
            items.add(resultSet.getString(0));
        }
        return items;
    }
    
    // Reliability issue: Method returns null instead of empty list or throwing exception
    public List<String> findOrders(String orderId) {
        try {
            String query = "SELECT order_id FROM orders WHERE order_id = '" + orderId  + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<String> orders = new ArrayList<String>();
            while (resultSet.next()){
                orders.add(resultSet.getString(0));
            }
            return orders;
        } catch (Exception e) {
            // Reliability issue: Returning null on error instead of proper error handling
            return null;
        }
    }
}
