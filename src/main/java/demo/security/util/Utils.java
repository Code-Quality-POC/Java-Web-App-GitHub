package demo.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class Utils {

    public static KeyPair generateKey() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(512);
            return keyPairGen.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void deleteFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileUtils.forceDelete(file);
    }

    public static void executeJs(String input) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(input);
    }

    public static void encrypt(byte[] key, byte[] ptxt) throws Exception {
        byte[] nonce = "7cVgr5cbdCZV".getBytes("UTF-8");

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec); // Noncompliant
    }
    
    // Maintainability issue: Long method with deep nesting and code duplication
    public static String processUserData(String data, int type, boolean validate, String format) {
        // Maintainability issue: Magic numbers
        if (type == 1) {
            if (validate) {
                if (format != null) {
                    if (format.equals("json")) {
                        // Maintainability issue: Deep nesting (4 levels)
                        if (data != null && data.length() > 0) {
                            if (data.startsWith("{")) {
                                // Process JSON
                                return data.toUpperCase();
                            } else {
                                return null;
                            }
                        }
                    } else if (format.equals("xml")) {
                        if (data != null && data.length() > 0) {
                            if (data.startsWith("<")) {
                                return data.toUpperCase();
                            } else {
                                return null;
                            }
                        }
                    }
                }
            }
        } else if (type == 2) {
            if (validate) {
                if (format != null) {
                    if (format.equals("json")) {
                        if (data != null && data.length() > 0) {
                            if (data.startsWith("{")) {
                                // Maintainability issue: Code duplication
                                return data.toLowerCase();
                            } else {
                                return null;
                            }
                        }
                    } else if (format.equals("xml")) {
                        if (data != null && data.length() > 0) {
                            if (data.startsWith("<")) {
                                return data.toLowerCase();
                            } else {
                                return null;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    // Reliability issue: Unchecked cast
    @SuppressWarnings("unchecked")
    public static <T> T castObject(Object obj) {
        // Reliability issue: Unchecked cast can cause ClassCastException at runtime
        return (T) obj;
    }
    
    // Reliability issue: Returns null on error
    public static File readFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {
            // Reliability issue: Empty catch block
        }
        return null;
    }
    
    // Maintainability issue: Dead code / unreachable code
    public static int calculate(int x) {
        if (x > 0) {
            return x * 2;
        } else {
            return x * 3;
        }
        // Maintainability issue: Unreachable code
        // System.out.println("This will never execute");
        // return 0;
    }
}
