package com.audibene.covidalert.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ResourceFileUtil {

    public static String readFileContentAsString(String filename) throws IOException {
        final var path = "src/test/resources/" + filename;
        final var file = new File(path);
        return FileUtils.readFileToString(file, "UTF-8");
    }
}
