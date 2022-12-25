import Exceptions.CalculationByFunctionException;
import NewSwingClass.JFilePicker;
import Parsing.CalculationRun;
import Read_and_Write.Archives.ArchiveRar;
import Read_and_Write.Archives.ArchiveZip;
import Read_and_Write.Decoder_and_Encoder.DecryptEncrypt;
import Read_and_Write.FilesTypes.JsonFile;
import Read_and_Write.FilesTypes.TxtFile;
import Read_and_Write.FilesTypes.XmlFile;
import Read_and_Write.Interfaces.IArchive;
import Read_and_Write.Interfaces.IFileReadingWriting;
import com.github.junrar.exception.RarException;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainUI extends JDialog {
    private JPanel contentPane; //done
    private JButton ChooseInButton; //done
    private JButton ChooseOutButton; //done
    private JComboBox TypeOfOutFile;
    private JComboBox InFile;
    private JComboBox OutFile;
    private JButton LibButton; //done
    private JButton FunctionButton; //done
    private JLabel CharecterInFile; //done
    private JLabel CharecterOutFile; //done
    private JLabel TypeOfOUtFileLabel; //done
    private JLabel WhatToDoWithOutFile; //done
    private JLabel HowRun; //done
    private JTextField textField1; //done
    private JTextField textField2; //done
    private JComboBox comboBox1;
    private JLabel TypeArchive;
    String typeArchive = "";
    private JButton in1Button;
    private JButton out2Button;
    private JButton outButton;
    private JButton in2Button;
    private JLabel Label;
    final Key key = new DecryptEncrypt().getKey("12345");
    String pathIn = "";
    String pathOut = "";
    String text = "";
    String inType = "";
    String outType = "";
    String outDo = "";

    public static String readfile(String name, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
        String text = "";
        Pattern pattern = Pattern.compile("\\.\\w+");
        Matcher matcher = pattern.matcher(name);
        String format = "";
        if (matcher.find()) {
            format = matcher.group();
        }
        IFileReadingWriting reader = null;
        switch (format) {
            case ".txt":
                reader = new TxtFile();
                text = reader.reading(name, a);
                break;
            case ".xml":
                reader = new XmlFile();
                text = reader.reading(name, a);
                break;
            case ".json":
                reader = new JsonFile();
                text = reader.reading(name, a);
                break;
        }
        return text;
    }

    public void fileWrite(String path) throws IOException, ParserConfigurationException, TransformerException, GeneralSecurityException {
        ArchiveZip archive = new ArchiveZip();
        Pattern pattern = Pattern.compile("\\.\\w+");
        Matcher matcher = pattern.matcher(path);
        String format = "";
        if (matcher.find()) {
            format = matcher.group();
        }
        if (format.substring(1) != outType) {
            path = path.replaceAll(format.substring(1), outType);
            pathOut = path;
        }
        IFileReadingWriting writeFile = null;
        switch (outType) {
            case "txt":
                writeFile = new TxtFile();
                break;
            case "json":
                writeFile = new JsonFile();
                break;
            case "xml":
                writeFile = new XmlFile();
                break;
        }
        writeFile.writing(text, path);
        switch (outDo) {
            case "Файл заархивировать":
                pathOut = path.replaceAll(outType, "zip");
                archive.archivate(path, path.replaceAll(outType, "zip"));
                break;
            case "Файл зашифровать":
                new DecryptEncrypt().encrypt(path, path, key);
                break;
            case "Файл заархивировать, потом зашифровать":
                String path1 = path.replaceAll("." + outType, ".zip");
                pathOut = path1;
                archive.archivate(path, path1);
                new DecryptEncrypt().encrypt(path1, path1, key);
                break;
            case "Файл зашифровать, потом заархивировать":
                new DecryptEncrypt().encrypt(path, path, key);
                pathOut = path.replaceAll(outType, "zip");
                archive.archivate(path, path.replaceAll("." + outType, ".zip"));
                break;
            case "Ничего не делать":
                break;
        }
    }

    public String fileRead(String Filename) throws GeneralSecurityException, RarException, IOException, ParserConfigurationException, SAXException {
        String text = "";

        IArchive archive = null;
        if (typeArchive == "Zip") archive = new ArchiveZip();
        else if (typeArchive == "Rar") archive = new ArchiveRar();
        switch (inType) {
            case "Файл заархивирован":
                text = archive.unarchivate(Filename, false);
                break;
            case "Файл зашифрован":
                new DecryptEncrypt().decrypt(Filename, Filename, key);
                text = readfile(Filename, false);
                break;
            case "Файл заархивирован, потом зашифрован":
                text = archive.unarchivate(Filename, true);
                break;
            case "Файл зашифрован, потом заархивирован":
                new DecryptEncrypt().decrypt(Filename, Filename, key);
                text = archive.unarchivate(Filename, false);
                break;
            case "Ничего из перечисленного":
                text = readfile(Filename, false);
                break;
        }
        return text;
    }

    public void onin1Button(ActionEvent evt) {
        InFile.setBackground(Color.green);
        int num = InFile.getSelectedIndex();
        if (num == 1 || num == 3 || num == 4) {
            comboBox1.setVisible(true);
            TypeArchive.setVisible(true);
            in2Button.setVisible(true);
            in2Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    typeArchive = (String) comboBox1.getSelectedItem();
                    comboBox1.setBackground(Color.green);
                    return;
                }
            });
        } else {
            comboBox1.setVisible(false);
            TypeArchive.setVisible(false);
            in2Button.setVisible(false);
            comboBox1.setSelectedIndex(0);
            typeArchive = (String) comboBox1.getSelectedItem();
        }
    }

    public void onLibButton(ActionEvent evt, String file1, String fileout) throws GeneralSecurityException, IOException, ParserConfigurationException, TransformerException, RarException, SAXException {
        text = fileRead(file1);
        CalculationRun calculation = new CalculationRun();
        text = calculation.CalculationRunByLibrary(text);
        fileWrite(fileout);
        Label.setText(String.format("Файл сохранен в %s", pathOut));
        Label.setVisible(true);
    }

    public void onFunctButton(ActionEvent evt, String file1, String fileout) throws CalculationByFunctionException, GeneralSecurityException, IOException, ParserConfigurationException, TransformerException, RarException, SAXException {
        text = fileRead(file1);
        CalculationRun calculation = new CalculationRun();
        text = calculation.CalculationRunByFunction(text);
        fileWrite(fileout);
        Label.setText(String.format("Файл сохранен в %s", pathOut));
        Label.setVisible(true);
    }

    public MainUI() throws GeneralSecurityException, RarException, IOException, ParserConfigurationException, SAXException {
        setContentPane(contentPane);
        setModal(true);
        Label.setVisible(false);
        comboBox1.setVisible(false);
        TypeArchive.setVisible(false);
        in2Button.setVisible(false);
        JFilePicker filePickerIn = new JFilePicker(textField1, ChooseInButton);
        filePickerIn.setMode(JFilePicker.MODE_OPEN);
        JFilePicker filePickerOut = new JFilePicker(textField2, ChooseOutButton);
        filePickerOut.setMode(JFilePicker.MODE_SAVE);
        in1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inType = (String) InFile.getSelectedItem();
                onin1Button(e);
            }
        });
        outButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outType = (String) TypeOfOutFile.getSelectedItem();
                TypeOfOutFile.setBackground(Color.green);
            }
        });
        out2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outDo = (String) OutFile.getSelectedItem();
                OutFile.setBackground(Color.green);
            }
        });
        String finalText = text;
        LibButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pathIn = filePickerIn.getSelectedFilePath();
                pathOut = filePickerOut.getSelectedFilePath();
                try {
                    onLibButton(e, pathIn, pathOut);
                } catch (GeneralSecurityException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (TransformerException ex) {
                    throw new RuntimeException(ex);
                } catch (RarException ex) {
                    throw new RuntimeException(ex);
                } catch (SAXException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        FunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathIn = filePickerIn.getSelectedFilePath();
                pathOut = filePickerOut.getSelectedFilePath();
                try {
                    onFunctButton(e, pathIn, pathOut);
                } catch (CalculationByFunctionException ex) {
                    throw new RuntimeException(ex);
                } catch (GeneralSecurityException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (TransformerException ex) {
                    throw new RuntimeException(ex);
                } catch (RarException ex) {
                    throw new RuntimeException(ex);
                } catch (SAXException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) throws GeneralSecurityException, RarException, IOException, ParserConfigurationException, SAXException {
        MainUI dialog = new MainUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(15, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ChooseInButton = new JButton();
        ChooseInButton.setText("Выбрать входной файл");
        panel2.add(ChooseInButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ChooseOutButton = new JButton();
        ChooseOutButton.setText("Выберите/создайте выходной файл");
        panel2.add(ChooseOutButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CharecterInFile = new JLabel();
        CharecterInFile.setText("Выберите характеристи входного файла.");
        panel2.add(CharecterInFile, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CharecterOutFile = new JLabel();
        CharecterOutFile.setText("Выберите характеристики входного файла.");
        panel2.add(CharecterOutFile, new GridConstraints(1, 0, 4, 2, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TypeOfOUtFileLabel = new JLabel();
        TypeOfOUtFileLabel.setText("Тип выходного файла.");
        panel2.add(TypeOfOUtFileLabel, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TypeOfOutFile = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Выберите...");
        defaultComboBoxModel1.addElement("txt");
        defaultComboBoxModel1.addElement("json");
        defaultComboBoxModel1.addElement("xml");
        TypeOfOutFile.setModel(defaultComboBoxModel1);
        panel2.add(TypeOfOutFile, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InFile = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Выберите....");
        defaultComboBoxModel2.addElement("Файл заархивирован");
        defaultComboBoxModel2.addElement("Файл зашифрован");
        defaultComboBoxModel2.addElement("Файл заархивирован, потом зашифрован");
        defaultComboBoxModel2.addElement("Файл зашифрован, потом заархивирован");
        defaultComboBoxModel2.addElement("Ничего из перечисленного");
        InFile.setModel(defaultComboBoxModel2);
        panel2.add(InFile, new GridConstraints(5, 0, 10, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        WhatToDoWithOutFile = new JLabel();
        WhatToDoWithOutFile.setText("Выберите, что нужно сделать с файлом.");
        panel2.add(WhatToDoWithOutFile, new GridConstraints(5, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        OutFile = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Выберите....");
        defaultComboBoxModel3.addElement("Файл заархивировать");
        defaultComboBoxModel3.addElement("Файл зашифровать");
        defaultComboBoxModel3.addElement("Файл заархивировать, потом зашифровать");
        defaultComboBoxModel3.addElement("Файл зашифровать, потом заархивировать");
        defaultComboBoxModel3.addElement("Ничего не делать");
        OutFile.setModel(defaultComboBoxModel3);
        panel2.add(OutFile, new GridConstraints(6, 3, 9, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        panel2.add(textField1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField2 = new JTextField();
        textField2.setText("");
        panel2.add(textField2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        TypeArchive = new JLabel();
        TypeArchive.setText("Выберите тип архива");
        panel2.add(TypeArchive, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Выберите...");
        defaultComboBoxModel4.addElement("Zip");
        defaultComboBoxModel4.addElement("Rar");
        comboBox1.setModel(defaultComboBoxModel4);
        panel2.add(comboBox1, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        in1Button = new JButton();
        in1Button.setText("Сохранить выбор");
        panel2.add(in1Button, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        out2Button = new JButton();
        out2Button.setText("Сохранить выбор");
        panel2.add(out2Button, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outButton = new JButton();
        outButton.setText("Сохранить выбор");
        panel2.add(outButton, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        in2Button = new JButton();
        in2Button.setText("Сохранить выбор");
        panel2.add(in2Button, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LibButton = new JButton();
        LibButton.setText("Обработать библиотекой");
        panel1.add(LibButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        HowRun = new JLabel();
        HowRun.setText("Каким образом обработать файл?");
        panel1.add(HowRun, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        FunctionButton = new JButton();
        FunctionButton.setText("Обработать функцией");
        panel1.add(FunctionButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Label = new JLabel();
        Label.setText("Hello");
        panel1.add(Label, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPane.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
