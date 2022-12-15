package Read_and_Write.Archives;
import Read_and_Write.FilesRead;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unarchivers {
    public String unarchivate_rar(String FileName, boolean a) throws RarException, IOException, ParserConfigurationException, SAXException, GeneralSecurityException {
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
        FilesRead reader=new FilesRead();
        switch (format)
        {
            case ".txt":
                text= reader.read_plain_text(name, a);
                break;
            case ".xml":
                text=reader.read_xml(name, a);
                break;
            case ".json":
                text=reader.read_json(name, a);
                break;
        }
        return text;
    }
    public String unarchivate_zip(String FileName, boolean a)
    {
        String text="";
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(FileName)))
        {
            ZipEntry entry;
            String name="";
            long size;
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName();
                size=entry.getSize();
                FileOutputStream fout = new FileOutputStream("D:\\Работа\\ПП\\Java_task\\" + name);
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
            FilesRead reader=new FilesRead();
            switch (format)
            {
                case ".txt":
                    text= reader.read_plain_text(name, a);
                    break;
                case ".xml":
                    text=reader.read_xml(name, a);
                    break;
                case ".json":
                    text=reader.read_json(name, a);
                    break;
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return text;
    }
}
