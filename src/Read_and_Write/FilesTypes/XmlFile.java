package Read_and_Write.FilesTypes;

import Read_and_Write.Decoder_and_Encoder.DecryptEncrypt;
import Read_and_Write.Interfaces.IFileReadingWriting;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;

public class XmlFile implements IFileReadingWriting {
    @Override
    public String reading(String FileName, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
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

    @Override
    public void writing(String text, String way) throws IOException {
        var outputFile = new FileOutputStream(way);
        outputFile.write(text.getBytes());
        outputFile.close();
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
