package demo.security.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowserSearchUtility {  
    
    // Maintainability issue: Unused constant
    private static final String DEFAULT_SEARCH_ENGINE = "https://www.google.com";
    private static int searchCount = 0;

    public static void main(String[] args) {
        String query = "sonarsource";
        // Maintainability issue: Unused variable
        boolean success = true;
        searchWeb(query);
    }

    public static void searchWeb(String query) {
        String url = "https://www.google.com/search?q=" + query;
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                // Reliability issue: Just printing stack trace, not properly handling error
                e.printStackTrace();
            }
        } else {
            System.err.println("Desktop is not supported. Cannot open the browser.");
        }
    }
    
    // Maintainability issue: Duplicate code - similar to searchWeb
    public static void openUrl(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Desktop is not supported. Cannot open the browser.");
        }
    }
    
    // Maintainability issue: Empty method
    private static void logSearch(String query) {
        // TODO: Implement logging
    }
}