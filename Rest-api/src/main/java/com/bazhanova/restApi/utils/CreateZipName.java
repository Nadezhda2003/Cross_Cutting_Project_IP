package com.bazhanova.restApi.utils;

public class CreateZipName {
    public static String createZipName(String name)
    {
        Pattern pattern=Pattern.compile("\\.\\w+");
        Matcher matcher= pattern.matcher(name);
        String format="";
        if(matcher.find())
        {
            format=matcher.group();
        }
        name=name.replace(format, ".zip");
        return name;
    }
}