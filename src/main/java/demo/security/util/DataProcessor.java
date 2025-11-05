package demo.security.util;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

// Maintainability issue: God class - too many responsibilities
public class DataProcessor {
    
    // Maintainability issue: Poor naming - what does 'x' represent?
    private static int x = 0;
    
    // Maintainability issue: Magic number
    private static final int MAX_SIZE = 1000;
    
    // Reliability issue: Non-thread-safe SimpleDateFormat as static field
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // Maintainability issue: Mutable static collection - can cause issues
    private static List<String> cache = new ArrayList<>();
    
    // Reliability issue: Method with too many nested try-catch blocks
    public String processData(String input) {
        try {
            if (input == null) {
                return null;
            }
            try {
                String trimmed = input.trim();
                try {
                    String processed = trimmed.toUpperCase();
                    try {
                        // Reliability issue: Deep nesting of try-catch
                        return processed.substring(0, 10);
                    } catch (IndexOutOfBoundsException e) {
                        // Reliability issue: Catching specific exception but doing nothing
                        return processed;
                    }
                } catch (Exception e) {
                    return trimmed;
                }
            } catch (Exception e) {
                return input;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    // Reliability issue: Method throws generic Exception
    public void readFile(String path) throws Exception {
        FileInputStream fis = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(path);
            br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                // Process line
                System.out.println(line);
            }
        } catch (IOException e) {
            // Reliability issue: Rethrowing as generic Exception loses information
            throw new Exception("Error reading file");
        }
        // Reliability issue: Streams not closed in finally block
    }
    
    // Maintainability issue: Complex method with multiple responsibilities
    public Map<String, Object> processUserRequest(String userId, String action, 
                                                   Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        
        // Maintainability issue: Long method with complex logic
        if (userId == null || userId.isEmpty()) {
            result.put("error", "Invalid user ID");
            return result;
        }
        
        if (action == null) {
            result.put("error", "No action specified");
            return result;
        }
        
        // Maintainability issue: Complex nested conditionals
        if (action.equals("create")) {
            if (params != null) {
                if (params.containsKey("name")) {
                    if (params.get("name").length() > 3) {
                        if (params.containsKey("email")) {
                            if (params.get("email").contains("@")) {
                                // Process create
                                result.put("status", "created");
                                result.put("userId", userId);
                            } else {
                                result.put("error", "Invalid email");
                            }
                        } else {
                            result.put("error", "Missing email");
                        }
                    } else {
                        result.put("error", "Name too short");
                    }
                } else {
                    result.put("error", "Missing name");
                }
            } else {
                result.put("error", "No parameters");
            }
        } else if (action.equals("update")) {
            // Maintainability issue: Duplicate validation logic
            if (params != null) {
                if (params.containsKey("name")) {
                    if (params.get("name").length() > 3) {
                        result.put("status", "updated");
                    } else {
                        result.put("error", "Name too short");
                    }
                }
            }
        } else if (action.equals("delete")) {
            result.put("status", "deleted");
        }
        
        return result;
    }
    
    // Reliability issue: Method modifies input parameter
    public void modifyList(List<String> items) {
        // Reliability issue: Modifying input list can cause unexpected behavior for caller
        items.clear();
        items.add("modified");
    }
    
    // Reliability issue: Returning reference to mutable internal state
    public List<String> getCache() {
        // Reliability issue: Direct access to mutable collection
        return cache;
    }
    
    // Maintainability issue: Duplicate code
    public String formatUserName(String firstName, String lastName) {
        if (firstName == null || firstName.isEmpty()) {
            return "Unknown";
        }
        if (lastName == null || lastName.isEmpty()) {
            return firstName;
        }
        return firstName + " " + lastName;
    }
    
    public String formatFullName(String first, String last) {
        // Maintainability issue: Exact duplicate of formatUserName with different param names
        if (first == null || first.isEmpty()) {
            return "Unknown";
        }
        if (last == null || last.isEmpty()) {
            return first;
        }
        return first + " " + last;
    }
    
    // Reliability issue: Infinite loop potential
    public int findValue(int target) {
        int i = 0;
        // Reliability issue: If target is negative, infinite loop
        while (i < target) {
            if (i == target) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    // Reliability issue: Array index without bounds checking
    public String getElement(String[] array, int index) {
        // Reliability issue: No validation of array or index
        return array[index];
    }
    
    // Maintainability issue: Switch statement that should be polymorphism
    public double calculatePrice(String productType, int quantity) {
        double basePrice = 0;
        
        // Maintainability issue: Long switch statement with magic numbers
        switch (productType) {
            case "electronics":
                basePrice = 299.99;
                if (quantity > 10) {
                    basePrice = basePrice * 0.9;
                }
                break;
            case "clothing":
                basePrice = 49.99;
                if (quantity > 10) {
                    basePrice = basePrice * 0.9;
                }
                break;
            case "books":
                basePrice = 19.99;
                if (quantity > 10) {
                    basePrice = basePrice * 0.9;
                }
                break;
            case "furniture":
                basePrice = 499.99;
                if (quantity > 10) {
                    basePrice = basePrice * 0.9;
                }
                break;
            default:
                basePrice = 9.99;
        }
        
        return basePrice * quantity;
    }
    
    // Reliability issue: Comparison using == for strings
    public boolean isValidStatus(String status) {
        // Reliability issue: Using == instead of .equals() for String comparison
        if (status == "active" || status == "pending" || status == "completed") {
            return true;
        }
        return false;
    }
    
    // Maintainability issue: Method that violates single responsibility
    public void doEverything(String data) {
        // Validate
        if (data == null) return;
        
        // Transform
        String processed = data.toUpperCase();
        
        // Store in cache
        cache.add(processed);
        
        // Log
        System.out.println("Processed: " + processed);
        
        // Write to file
        try {
            FileWriter fw = new FileWriter("output.txt", true);
            fw.write(processed + "\n");
            // Reliability issue: FileWriter not closed
        } catch (IOException e) {
            // Reliability issue: Empty catch block
        }
        
        // Update counter
        x++;
        
        // Send notification (simulated)
        System.out.println("Notification sent");
    }
    
    // Reliability issue: Potential memory leak with large collections
    public void addToCache(String item) {
        // Reliability issue: No size limit check - can cause OutOfMemoryError
        cache.add(item);
    }
    
    // Maintainability issue: Unclear variable names
    public int calc(int a, int b, int c) {
        int d = a + b;
        int e = d * c;
        int f = e - a;
        int g = f / b;
        return g;
    }
}

