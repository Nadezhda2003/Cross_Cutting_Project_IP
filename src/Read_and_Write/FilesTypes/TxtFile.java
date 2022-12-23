package Read_and_Write.FilesTypes;

import Read_and_Write.Decoder_and_Encoder.DecryptEncrypt;
import Read_and_Write.Interfaces.IFileReadingWriting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Scanner;

public class TxtFile implements IFileReadingWriting {
    @Override
    public String reading(String FileName, boolean a) throws GeneralSecurityException, IOException {
        if (a == true)
        {
            Key key = new DecryptEncrypt().getKey("12345");
            new DecryptEncrypt().encrypt(FileName, "D:\\Работа\\ПП\\Java_task\\"+FileName, key);
        }
        FileReader reader = new FileReader(FileName);
        Scanner in=new Scanner(reader);
        String text="";
        while(in.hasNextLine())
        {
            text+=in.nextLine();
        }
        reader.close();
        return text;
    }

    @Override
    public void writing(String text, String way) throws IOException {
        FileWriter out = new FileWriter(mkdirFiles(way));
        out.write(text);
        out.close();
    }
    private File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        File parent=file.getParentFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }
}