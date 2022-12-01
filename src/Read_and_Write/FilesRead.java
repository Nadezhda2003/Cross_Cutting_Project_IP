package Read_and_Write;

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
import java.util.Scanner;

public class FilesRead {
    public String read_xml(String FileName) throws ParserConfigurationException, IOException, SAXException {
        String text="";
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
