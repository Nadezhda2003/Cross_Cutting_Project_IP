package com.bazhanova.logic.utils.archives;

import com.bazhanova.logic.readers.JsonFile;
import com.bazhanova.logic.readers.TxtFile;
import com.bazhanova.logic.readers.XmlFile;
import com.bazhanova.logic.builders.IArchive;
import com.bazhanova.logic.builders.IFileReadingWriting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ArchiveZip implements IArchive {
    @Override
    public void archivate(String FileName, String way) {
        Pattern pattern = Pattern.compile("\\.\\w+");
        Matcher matcher = pattern.matcher(FileName);
        String type="";
        if(matcher.find())
        {
            type=matcher.group();
        }
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(way));
            FileInputStream fis= new FileInputStream(FileName);)
        {
            ZipEntry entry1=new ZipEntry("file"+type);
            zout.putNextEntry(entry1);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String unarchivate(String FileName, boolean a) {
        String text="";
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(FileName)))
        {
            ZipEntry entry;
            String name="";
            long size;
            while((entry=zin.getNextEntry())!=null){
                name = entry.getName();
                size=entry.getSize();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
            Pattern pattern=Pattern.compile("\\.\\w+");
            Matcher matcher= pattern.matcher(name);
            String format="";
            if(matcher.find())
            {
                format=matcher.group();
            }
            IFileReadingWriting reader = null;
            switch (format)
            {
                case ".txt":
                    reader= new TxtFile();
                    text= reader.reading(name, a);
                    break;
                case ".xml":
                    reader= new XmlFile();
                    text= reader.reading(name, a);
                    break;
                case ".json":
                    reader= new JsonFile();
                    text= reader.reading(name, a);
                    break;
            }
            new File(name).delete();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return text;
    }
}