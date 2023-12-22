package com.a00n.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String UPLOAD_DIRECTORY = "content" + File.separator + "images" + File.separator + "uploads";

    private Constants() {}

    private static final List<String> VALID_EXTENSIONS = Arrays.asList("png", "jpeg", "jpg");

    public static boolean isValidImageExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return false;
        }

        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        return VALID_EXTENSIONS.contains(extension);
    }
}
