package demo.security.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Set; // Maintainability issue: Unused import
import java.util.HashSet; // Maintainability issue: Unused import

public class ValidationHelper {
    
    // Maintainability issue: Constant that is never used
    private static final String UNUSED_PATTERN = "[0-9]+";
    private static final int MAX_RETRIES = 5;
    
    // Reliability issue: Mutable static field
    
    // Maintainability issue: God method with too many responsibilities
    public static boolean validateUserInput(String username, String email, String password, 
                                           String confirmPassword, String phone, String address) {
        // Maintainability issue: Multiple responsibilities in one method
        boolean valid = true;
        
        // Validate username
        if (username == null || username.isEmpty()) {
            valid = false;
        } else {
            if (username.length() < 3) {
                valid = false;
            } else {
                if (username.length() > 20) {
                    valid = false;
                } else {
                    if (!username.matches("[a-zA-Z0-9]+")) {
                        valid = false;
                    }
                }
            }
        }
        
        // Validate email
        if (email == null || email.isEmpty()) {
            valid = false;
        } else {
            if (!email.contains("@")) {
                valid = false;
            } else {
                if (!email.contains(".")) {
                    valid = false;
                }
            }
        }
        
        // Validate password
        if (password == null || password.isEmpty()) {
            valid = false;
        } else {
            if (password.length() < 8) {
                valid = false;
            } else {
                if (!password.equals(confirmPassword)) {
                    valid = false;
                }
            }
        }
        
        // Validate phone
        if (phone != null) {
            if (!phone.isEmpty()) {
                if (phone.length() < 10) {
                    valid = false;
                }
            }
        }
        
        return valid;
    }
    
    // Maintainability issue: Switch statement with fall-through
    public static String getErrorMessage(int errorCode) {
        String message = "";
        switch (errorCode) {
            case 1:
                message = "Invalid username";
                // Maintainability issue: Missing break - will fall through
            case 2:
                message = "Invalid email";
                break;
            case 3:
                message = "Invalid password";
                break;
            default:
                message = "Unknown error";
        }
        return message;
    }
    
    // Reliability issue: Comparing strings with ==
    public static boolean checkStatus(String status) {
        // Reliability issue: Using == for string comparison
        if (status == "active") {
            return true;
        } else if (status == "pending") {
            return true;
        }
        return false;
    }
    
    // Maintainability issue: Complex ternary operator
    public static String formatMessage(String input, boolean uppercase, boolean trim, boolean addPrefix) {
        return addPrefix ? "MSG: " + (trim ? (uppercase ? input.trim().toUpperCase() : input.trim().toLowerCase()) 
                                           : (uppercase ? input.toUpperCase() : input.toLowerCase()))
                        : (trim ? (uppercase ? input.trim().toUpperCase() : input.trim().toLowerCase()) 
                                : (uppercase ? input.toUpperCase() : input.toLowerCase()));
    }
    
    // Reliability issue: Ignoring return value
    public static void processString(String input) {
        // Reliability issue: trim() returns a new string but result is ignored
        input.trim();
        input.toLowerCase();
        // The original input is unchanged
        System.out.println(input);
    }
    
    // Maintainability issue: Multiple return statements in short method
    public static int calculateScore(int value) {
        if (value < 0) {
            return 0;
        }
        if (value < 10) {
            return 1;
        }
        if (value < 20) {
            return 2;
        }
        if (value < 30) {
            return 3;
        }
        return 4;
    }
    
    // Reliability issue: Possible NPE when chaining method calls
    public static String getUserInfo(String userId) {
        // Reliability issue: No null checks - getUserById could return null
        return getUserById(userId).toUpperCase().trim();
    }
    
    private static String getUserById(String id) {
        // Could return null
        return null;
    }
}

