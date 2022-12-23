package Read_and_Write.Interfaces;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IFileReadingWriting {
    String reading(String FileName, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException;
    void writing(String text, String way) throws IOException;
}