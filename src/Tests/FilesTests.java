package Tests;

import Read_and_Write.Archives.ArchiveZip;
import Read_and_Write.Decoder_and_Encoder.DecryptEncrypt;
import Read_and_Write.FilesTypes.TxtFile;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FilesTests {

    private static boolean isEqual(Path firstFile, Path secondFile)
    {
        try {
            long size = Files.size(firstFile);
            if (size != Files.size(secondFile)) {
                return false;
            }

            if (size < 2048)
            {
                return Arrays.equals(Files.readAllBytes(firstFile),
                        Files.readAllBytes(secondFile));
            }
            try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
                 BufferedReader bf2 = Files.newBufferedReader(secondFile)) {

                String line;
                while ((line = bf1.readLine()) != null)
                {
                    if (line != bf2.readLine()) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void whenEncryptingIntoFile_andDecryptingFileAgain_thenOriginalStringIsReturned() throws GeneralSecurityException, IOException {
        Key key = new DecryptEncrypt().getKey("12345");
        new DecryptEncrypt().encrypt("input.txt", "D:\\Работа\\ПП\\Java_task\\result.txt", key);
        new DecryptEncrypt().decrypt("result.txt", "D:\\Работа\\ПП\\Java_task\\res.txt", key);
        new File("result.txt").delete();
        File firstFile=new File("input.txt");
        File secondFile=new File("res.txt");
        assertEquals(true, isEqual(firstFile.toPath(), secondFile.toPath()));
        new File("res.txt").delete();
    }

    @Test
    public void whenFileIsArhivateIntoZip_andUnarchivateFile_thenOriginalTextIsReturned() throws IOException {
        String originText="Hello";
        new TxtFile().writing(originText, "D:\\Работа\\ПП\\Java_task\\result.txt");
        new ArchiveZip().archivate("result.txt", "D:\\Работа\\ПП\\Java_task\\res.zip");
        String text= new ArchiveZip().unarchivate("D:\\Работа\\ПП\\Java_task\\res.zip", false);
        new File("D:\\Работа\\ПП\\Java_task\\result.txt").delete();
        new File("D:\\Работа\\ПП\\Java_task\\res.zip").delete();
        assertEquals(originText, text);
    }
}
