package demo.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WebUtils {

    public void addCookie(HttpServletResponse response, String name, String value) {
        Cookie c = new Cookie(name, value);
        response.addCookie(c);
    }

    public static void getSessionId(HttpServletRequest request){
        String sessionId = request.getRequestedSessionId();
        if (sessionId != null){
            String ip = "10.40.1.1";
            Socket socket = null;
            try {
                socket = new Socket(ip, 6667);
                socket.getOutputStream().write(sessionId.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    // Reliability issue: potential NPE if socket creation failed
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    // Reliability issue: Empty catch block - swallows exception
                    // TODO - Handle this
                }
            }
        }
    }
    
    // Maintainability issue: Magic numbers throughout method
    public static int calculateDiscount(int price, int userType) {
        // Maintainability issue: Magic numbers 1, 2, 3 instead of named constants
        if (userType == 1) {
            return price * 90 / 100; // 10% discount
        } else if (userType == 2) {
            return price * 80 / 100; // 20% discount
        } else if (userType == 3) {
            return price * 70 / 100; // 30% discount
        }
        return price;
    }
    
    // Reliability issue: Potential division by zero
    public static double calculateAverage(int total, int count) {
        // Reliability issue: No check for count being zero
        return total / count;
    }
    
    // Reliability issue: Array access without bounds checking
    public static String getFirstElement(String[] array) {
        // Reliability issue: No null or length check
        return array[0];
    }
}
