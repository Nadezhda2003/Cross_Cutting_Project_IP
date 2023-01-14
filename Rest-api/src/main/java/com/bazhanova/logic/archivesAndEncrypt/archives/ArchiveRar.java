package com.bazhanova.logic.utils.archives;

import com.bazhanova.logic.Exceptions.RarArchivationException;
import com.bazhanova.logic.readers.JsonFile;
import com.bazhanova.logic.readers.TxtFile;
import com.bazhanova.logic.readers.XmlFile;
import com.bazhanova.logic.builders.IArchive;
import com.bazhanova.logic.builders.IFileReadingWriting;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArchiveRar implements IArchive {
    @Override
    public void archivate(String FileName, String way) throws RarArchivationException {
        throw new RarArchivationException("Sorry, but you can't archivate file into rar.");
    }

    @Override
    public String unarchivate(String FileName, boolean a) throws RarException, IOException, GeneralSecurityException, ParserConfigurationException, SAXException {
        String text="";
        String name="";
        File f = new File(FileName);
        Archive archive = new Archive(f);
        archive.getMainHeader().print();
        FileHeader fh = archive.nextFileHeader();
        while(fh!=null){
            File fileEntry = new File(fh.getFileNameString().trim());
            name=fileEntry.toString();
            FileOutputStream os = new FileOutputStream(fileEntry);
            archive.extractFile(fh, os);
            os.close();
            fh=archive.nextFileHeader();
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
        return text;
    }
}