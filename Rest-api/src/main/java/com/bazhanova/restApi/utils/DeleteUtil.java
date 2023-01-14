package com.bazhanova.restApi.utils;

import java.io.File;

public class DeleteUtil {
    public static void deleteFile(String path, String fileName) {
        File file = new File(path + "\\" + fileName);
        file.delete();
    }
}