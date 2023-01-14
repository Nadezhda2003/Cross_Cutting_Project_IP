package com.bazhanova.restApi.utils;

import com.bazhanova.logic.interfaces.IFileReadingWriting;
import com.bazhanova.logic.readers.*;

public class ReadUtil {
    public static String readfile(String name, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
        String text = "";
        Pattern pattern = Pattern.compile("\\.\\w+");
        Matcher matcher = pattern.matcher(name);
        String format = "";
        if (matcher.find()) {
            format = matcher.group();
        }
        IFileReadingWriting reader = null;
        switch (format) {
            case ".txt":
                reader = new TxtFile();
                text = reader.reading(name, a);
                break;
            case ".xml":
                reader = new XmlFile();
                text = reader.reading(name, a);
                break;
            case ".json":
                reader = new JsonFile();
                text = reader.reading(name, a);
                break;
        }
        return text;
    }
}