import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.CalculationByFunctionException;
import Exceptions.RarArchivationException;
import Parsing.CalculationByLibrary;
import Parsing.CalculationRun;
import Read_and_Write.Archives.*;
import Read_and_Write.Decoder_and_Encoder.*;
import Read_and_Write.FilesTypes.*;
import Read_and_Write.Interfaces.*;
import com.github.junrar.exception.RarException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MainConcole {
    public static String createZipName(String name)
    {
        Pattern pattern=Pattern.compile("\\.\\w+");
        Matcher matcher= pattern.matcher(name);
        String format="";
        if(matcher.find())
        {
            format=matcher.group();
        }
        name=name.replace(format, ".zip");
        return name;
    }
    public static void menuArchive()
    {
        System.out.println("Choose type of input archive. Enter:\n1 if zip;\n2 if rar.");
    }

    public String SecondSwitch(int num, Key key, Scanner in, String way, String text) throws GeneralSecurityException, RarException, IOException, ParserConfigurationException, SAXException, TransformerException, RarArchivationException, CalculationByFunctionException {
        System.out.println("Choose how to solve the problem. Enter:\n1 to slove by functions.\n2 to solve by labrary.");
        int n=in.nextInt();
        if (n==1)
            text = new CalculationRun().CalculationRunByFunction(text);
        else if (n==2)
            text = new CalculationRun().CalculationRunByLibrary(text);
        IArchive archive=null;
        String FileName=way;
        IFileReadingWriting file = null;
        menuTypeFile();
        int type=in.nextInt();
        switch (type) {
            case 1 -> file = new XmlFile();
            case 2 -> file = new TxtFile();
            case 3 -> file = new JsonFile();
        }
        int typearchive;
        switch (num) {
            case 1 -> {
                menuArchive();
                typearchive = in.nextInt();
                switch (typearchive) {
                    case 1 -> archive = new ArchiveZip();
                    case 2 -> archive = new ArchiveRar();
                }
                file.writing(text, way);
                FileName = createZipName(way);
                archive.archivate(way, FileName);
            }
            case 2 -> new DecryptEncrypt().encrypt(way, way, key);
            case 3 -> {
                menuArchive();
                typearchive = in.nextInt();
                switch (typearchive) {
                    case 1:
                        archive = new ArchiveZip();
                        break;
                    case 2:
                        archive = new ArchiveRar();
                        break;
                }
                FileName = createZipName(way);
                archive.archivate(way, FileName);
                new DecryptEncrypt().encrypt(FileName, FileName, key);
            }
            case 4 -> {
                menuArchive();
                typearchive = in.nextInt();
                switch (typearchive) {
                    case 1:
                        archive = new ArchiveZip();
                        break;
                    case 2:
                        archive = new ArchiveRar();
                        break;
                }
                new DecryptEncrypt().encrypt(way, way, key);
                FileName = createZipName(way);
                archive.archivate(way, FileName);
            }
            case 5 -> text = readfile(way, false);
        }
        return FileName;
    }
    public static void menuTypeFile()
    {
        System.out.println("Choose type of output file. Enter:\n1 if xml;\n2 if txt;\n3 if json.");
    }
    public static String readfile(String name, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
        String text="";
        Pattern pattern=Pattern.compile("\\.\\w+");
        Matcher matcher= pattern.matcher(name);
        String format="";
        if(matcher.find())
        {
            format=matcher.group();
        }
        IFileReadingWriting reader = null;
        switch (format)
        {
            case ".txt":
                reader= new TxtFile();
                text= reader.reading(name, a);
                break;
            case ".xml":
                reader= new XmlFile();
                text= reader.reading(name, a);
                break;
            case ".json":
                reader= new JsonFile();
                text= reader.reading(name, a);
                break;
        }
        return text;
    }
    public static void main(String[] args) throws Exception {
        String way="";
        System.out.println("Enter input file path");
        Scanner in = new Scanner(System.in);
        way=in.nextLine();
        System.out.println("Choose the right feature for your input file. Enter:\n" +
                "1 if the file is archived;\n" +
                "2 if the file is encrypted;\n" +
                "3 if the file is archived and then encrypted;\n" +
                "4 if your file is encrypted and then archived;\n" +
                "5 none of the above.");
        int num=in.nextInt();
        System.out.println("Enter output file path");
        String way2=in.nextLine();
        way2=in.nextLine();
        System.out.println("Choose the right feature for your output file. Enter:\n" +
                "1 if the file is to be archived;\n" +
                "2 if the file needs to be encrypted;\n" +
                "3 if the file is to be archived and then encrypted;\n" +
                "4 if your file needs to be encrypted and then archived;\n" +
                "5 none of the above.");
        int num1=in.nextInt();
        int typearchive;
        Key key = new DecryptEncrypt().getKey("12345");
        String text="";
        IArchive archive=null;
        switch (num)
        {
            case 1:
                menuArchive();
                typearchive=in.nextInt();
                switch (typearchive)
                {
                    case 1: archive=new ArchiveZip();
                        break;
                    case 2: archive=new ArchiveRar();
                        break;
                }
                text = archive.unarchivate(way, false);
                break;
            case 2:
                new DecryptEncrypt().decrypt(way, way, key);
                text = readfile(way, false);
                break;
            case 3: menuArchive();
                typearchive=in.nextInt();
                switch (typearchive)
                {
                    case 1: archive=new ArchiveZip();
                        break;
                    case 2: archive=new ArchiveRar();
                        break;
                }
                text = archive.unarchivate(way, true);
                break;
            case 4:
                menuArchive();
                typearchive=in.nextInt();
                switch (typearchive)
                {
                    case 1: archive=new ArchiveZip();
                        break;
                    case 2: archive=new ArchiveRar();
                        break;
                }
                new DecryptEncrypt().decrypt(way, way, key);
                text = archive.unarchivate(way, false);
                break;
            case 5:
                text=readfile(way, false);
                break;
        }
        System.out.println("Choose how to solve the problem. Enter:\n1 to slove by functions.\n2 to solve by labrary.");
        int n=in.nextInt();
        if (n==1)
            text = new CalculationRun().CalculationRunByFunction(text);
        else if (n==2)
            text = new CalculationRun().CalculationRunByLibrary(text);
        String FileName=way2;
        IFileReadingWriting file = null;
        menuTypeFile();
        int type=in.nextInt();
        switch (type)
        {
            case 1: file=new XmlFile();
                break;
            case 2: file = new TxtFile();
                break;
            case 3: file=new JsonFile();
                break;
        }
        switch (num1)
        {
            case 1: archive=new ArchiveZip();
                file.writing(text, way2);
                FileName = createZipName(way2);
                archive.archivate(way2, FileName);
                break;
            case 2:
                new DecryptEncrypt().encrypt(way2, way2, key);
                break;
            case 3: archive=new ArchiveZip();
                FileName=createZipName(way2);
                archive.archivate(way2, FileName);
                new DecryptEncrypt().encrypt(FileName, FileName, key);
                break;
            case 4: archive=new ArchiveZip();
                new DecryptEncrypt().encrypt(way2, way2, key);
                FileName=createZipName(way2);
                archive.archivate(way2, FileName);
                break;
            case 5:
                file.writing(text, way2);
                break;
        }
        System.out.println("Your file is create with name:"+ FileName);
    }
}
