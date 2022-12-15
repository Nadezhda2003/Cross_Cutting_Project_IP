package Read_and_Write.Decoder_and_Encoder;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;


public class DecryptEncrypt {

    private static String type = "AES";


    public void encrypt(String srcFile, String destFile, Key key) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(destFile));

            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public void decrypt(String srcFile, String destFile, Key key) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(destFile));

            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
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


    public Key getKey(String secret) throws GeneralSecurityException {
        KeyGenerator kgen = KeyGenerator.getInstance(type);
        kgen.init(128, new SecureRandom(secret.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        return secretKey;
    }


    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }
}
