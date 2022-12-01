package Read;

import org.apache.logging.log4j.core.util.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FilesRead {
    public void read_xml(String FileName)
    {

    }
    public String read_plain_text(String FileName) throws IOException {
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
    public void read_json(String FileName)
    {

    }
}
