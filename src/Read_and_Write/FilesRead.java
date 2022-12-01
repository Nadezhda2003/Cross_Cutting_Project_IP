package Read_and_Write;

import com.google.gson.Gson;

import java.io.BufferedReader;
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
    public String read_json(String FileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FileName));
        String text = new Gson().fromJson(reader, String.class);
        reader.close();
        return text;
    }
}
