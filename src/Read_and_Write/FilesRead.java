package Read_and_Write;

import Read_and_Write.Decoder_and_Encoder.DecryptEncrypt;
import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Scanner;

public class FilesRead {
    public String read_xml(String FileName, boolean a) throws ParserConfigurationException, IOException, SAXException, GeneralSecurityException {
        String text="";
        if (a == true)
        {
            Key key = new DecryptEncrypt().getKey("12345");
            new DecryptEncrypt().encrypt(FileName, "D:\\Работа\\ПП\\Java_task\\"+FileName, key);
        }
        File xmlFile = new File(FileName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("String");
        for (int i = 0; i < nodeList.getLength(); i++) {
            text = nodeList.item(i).getChildNodes().item(0).getTextContent();
        }
        String[] mas=text.split("\n");
        text="";
        for (int j=0; j<mas.length; j++)
        {
            text+=mas[j].strip()+"\n";
        }
        return text;
    }
    public String read_plain_text(String FileName, boolean a) throws IOException, GeneralSecurityException {
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
    public String read_json(String FileName, boolean a) throws IOException, GeneralSecurityException {
        if (a == true)
        {
            Key key = new DecryptEncrypt().getKey("12345");
            new DecryptEncrypt().encrypt(FileName, "D:\\Работа\\ПП\\Java_task\\"+FileName, key);
        }
        BufferedReader reader = new BufferedReader(new FileReader(FileName));
        String text = new Gson().fromJson(reader, String.class);
        reader.close();
        return text;
    }
}
