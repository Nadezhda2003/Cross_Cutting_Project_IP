package Read_and_Write.Archives;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archivers {
    public void arhiver_zip(String FileName)
    {
        String way="D:\\Работа\\ПП\\Java_task\\result.zip";
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(way));
            FileInputStream fis= new FileInputStream(FileName);)
        {
            ZipEntry entry1=new ZipEntry(FileName);
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
}
