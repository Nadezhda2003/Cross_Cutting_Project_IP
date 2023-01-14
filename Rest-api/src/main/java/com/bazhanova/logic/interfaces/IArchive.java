package com.bazhanova.logic.builders;

import com.bazhanova.logic.Exceptions.RarArchivationException;
import com.github.junrar.exception.RarException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IArchive {
    void archivate(String FileName, String way) throws RarArchivationException;
    String unarchivate(String FileName, boolean a) throws RarException, IOException, GeneralSecurityException, ParserConfigurationException, SAXException;
}