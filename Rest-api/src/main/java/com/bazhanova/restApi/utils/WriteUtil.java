package com.bazhanova.restApi.utils;

import com.bazhanova.logic.interfaces.IFileReadingWriting;
import com.bazhanova.logic.readers.*;

public class WriteUtil {
public static void Writefile(String name, String text) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
    Pattern pattern=Pattern.compile("\\.\\w+");
    Matcher matcher= pattern.matcher(name);
    String format="";
    if(matcher.find())
    {
        format=matcher.group();
    }
    IFileReadingWriting writer = null;
    switch (format)
    {
        case ".txt":
            writer= new TxtFile();
            text= writer.writing(text, name);
            break;
        case ".xml":
            writer= new XmlFile();
            text= writer.writing(text, name);
            break;
        case ".json":
            writer= new JsonFile();
            text= writer.writing(text, name);
            break;
    }
    }
}