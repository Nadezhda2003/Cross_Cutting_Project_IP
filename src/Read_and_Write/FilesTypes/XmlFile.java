package Read_and_Write.FilesTypes;

import Read_and_Write.Interfaces.IFileReadingWriting;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.GeneralSecurityException;

public class XmlFile implements IFileReadingWriting {
    @Override
    public String reading(String FileName, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
        String text="";
        File inputFile = new File(FileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("String");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == nNode.ELEMENT_NODE) {
                Element string = (Element) nNode;
                text+=string.getTextContent()+" ";
            }
        }
        return text.strip();
    }

    @Override
    public void writing(String text, String way) throws IOException, XMLStreamException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        Element rootElement = doc.createElement("Strings");
        doc.appendChild(rootElement);
        Element strings = doc.createElement("String");
        rootElement.appendChild(strings);
        strings.appendChild(doc.createTextNode(text));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(mkdirFiles(way));
        transformer.transform(source, result);
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