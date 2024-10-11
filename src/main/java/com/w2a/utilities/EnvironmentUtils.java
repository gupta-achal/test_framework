package com.w2a.utilities;



public class EnvironmentUtils {

    // Example method to check if the current environment is Production
    public static boolean isProduction() {
        // You can use environment variables, system properties, or configuration files
        String env = System.getProperty("env"); // Example: set -Denv=production in VM arguments

        // Check if the environment is set to production
        return "production".equalsIgnoreCase(env);
    }
}

