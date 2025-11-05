package demo.security.util;

import java.io.*;
import java.util.*;

// Maintainability issue: Singleton pattern with public static mutable state
public class ConfigManager {
    
    // Reliability issue: Singleton instance not thread-safe
    private static ConfigManager instance;
    
    // Maintainability issue: Public mutable fields - breaks encapsulation
    public static String API_KEY = "sk_test_12345abcdefg";
    public static String DATABASE_URL = "jdbc:mysql://prod-db.example.com:3306/maindb";
    public static String ADMIN_PASSWORD = "Admin123!";
    
    // Reliability issue: Non-thread-safe lazy initialization
    public static ConfigManager getInstance() {
        if (instance == null) {
            // Reliability issue: Not synchronized - can create multiple instances
            instance = new ConfigManager();
        }
        return instance;
    }
    
    // Maintainability issue: Hard-coded configuration values
    private Map<String, String> config = new HashMap<>();
    
    private ConfigManager() {
        // Maintainability issue: Magic strings and values
        config.put("timeout", "30000");
        config.put("maxConnections", "100");
        config.put("retryAttempts", "3");
        config.put("apiEndpoint", "https://api.example.com/v1");
        config.put("secretKey", "s3cr3tk3y123");
    }
    
    // Reliability issue: Returns null on error
    public String getConfig(String key) {
        try {
            return config.get(key);
        } catch (Exception e) {
            // Reliability issue: Generic catch with null return
            return null;
        }
    }
    
    // Reliability issue: No input validation
    public void setConfig(String key, String value) {
        // Reliability issue: No null checks
        config.put(key, value);
    }
    
    // Reliability issue: Exposing mutable internal state
    public Map<String, String> getAllConfig() {
        // Reliability issue: Returning direct reference to internal map
        return config;
    }
    
    // Maintainability issue: Method with complex logic and no error handling
    public void loadConfigFromFile(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                // Reliability issue: No validation of line format
                String[] parts = line.split("=");
                // Reliability issue: Array access without bounds checking
                config.put(parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            // Reliability issue: Different exception handling
            System.out.println("File not found");
        } catch (IOException e) {
            // Reliability issue: Just printing stack trace
            e.printStackTrace();
        }
        // Reliability issue: Reader not closed in finally block
    }
    
    // Maintainability issue: Method with duplicate validation logic
    public boolean validateApiKey(String key) {
        if (key == null) {
            return false;
        }
        if (key.isEmpty()) {
            return false;
        }
        if (key.length() < 10) {
            return false;
        }
        if (!key.startsWith("sk_")) {
            return false;
        }
        return true;
    }
    
    public boolean validateSecretKey(String key) {
        // Maintainability issue: Duplicate validation logic
        if (key == null) {
            return false;
        }
        if (key.isEmpty()) {
            return false;
        }
        if (key.length() < 10) {
            return false;
        }
        return true;
    }
    
    // Reliability issue: Integer parsing without error handling
    public int getIntConfig(String key) {
        String value = config.get(key);
        // Reliability issue: No null check before parsing
        return Integer.parseInt(value);
    }
    
    // Reliability issue: Potential resource leak
    public void saveConfigToFile(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Map.Entry<String, String> entry : config.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            writer.flush();
            // Reliability issue: Writer not closed - resource leak
        } catch (IOException e) {
            // Reliability issue: Exception swallowed
        }
    }
    
    // Maintainability issue: Complex boolean expression
    public boolean isValidConfiguration() {
        return config.containsKey("timeout") && 
               config.containsKey("maxConnections") && 
               config.containsKey("retryAttempts") &&
               config.get("timeout") != null && 
               !config.get("timeout").isEmpty() &&
               config.get("maxConnections") != null && 
               !config.get("maxConnections").isEmpty() &&
               Integer.parseInt(config.get("timeout")) > 0 &&
               Integer.parseInt(config.get("maxConnections")) > 0 &&
               Integer.parseInt(config.get("retryAttempts")) > 0;
    }
    
    // Reliability issue: Method with side effects not clear from name
    public String getDatabaseUrl() {
        // Reliability issue: Getter that modifies state
        if (DATABASE_URL.contains("localhost")) {
            DATABASE_URL = DATABASE_URL.replace("localhost", "127.0.0.1");
        }
        return DATABASE_URL;
    }
}

