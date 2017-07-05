package com.darakeon.stories.structure;

import android.content.Context;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Handhara on 09/09/2015.
 */
public class EpisodeFactory
{
    private File episodeDir;

    public EpisodeFactory(File episodeDir)
    {
        this.episodeDir = episodeDir;
    }

    public EpisodeFactory(Context context, String seasonLetter, String episodeNumber)
    {
        File dir = context.getExternalFilesDir("");
        File seasonDir = new File(dir, seasonLetter);
        this.episodeDir = new File(seasonDir, episodeNumber);
    }

    public Episode GetEpisodeForList() throws IOException, ParserConfigurationException, SAXException, ParseException
    {
        File summary = new File(episodeDir, "_.xml");
        Element episodeSummaryNode = getFileBody(summary);

        Episode episode = new Episode(episodeSummaryNode);
        episode.SetMainInfo();

        return episode;
    }

    public Episode GetCompleteEpisode() throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        File[] files = episodeDir.listFiles();
        Element summaryNode = null;
        Element[] sceneNodes = new Element[files.length - 1];
        Integer position = 0;

        for (File file : files)
        {
            if (file.getName().startsWith("_"))
            {
                summaryNode = getFileBody(file);
            }
            else
            {
                sceneNodes[position] = getFileBody(file);
                position++;
            }
        }

        Episode episode = new Episode(summaryNode, sceneNodes);
        episode.SetContent();

        return episode;
    }

    private Element getFileBody(File file) throws IOException, SAXException, ParserConfigurationException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();

        try
        {
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
        } finally
        {
            br.close();
        }

        ByteArrayInputStream input = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

        return doc.getDocumentElement();
    }





    private void get(String server, String user, String password, String serverRoad, File file) throws IOException
    {
        FTPClient ftpClient = getClient(server, user, password, serverRoad);

        BufferedOutputStream buffOut =
                new BufferedOutputStream(new FileOutputStream(file));

        ftpClient.retrieveFile(file.getName(), buffOut);
        buffOut.close();

        ftpClient.logout();
        ftpClient.disconnect();
    }

    private void set(String server, String user, String password, String serverRoad, File file) throws IOException
    {
        FTPClient ftpClient = getClient(server, user, password, serverRoad);

        BufferedInputStream buffIn =
                new BufferedInputStream(new FileInputStream(file));

        ftpClient.storeFile(file.getName(), buffIn);
        buffIn.close();

        ftpClient.logout();
        ftpClient.disconnect();
    }

    private FTPClient getClient(String server, String user, String password, String serverRoad) throws IOException
    {
        FTPClient ftpClient = new FTPClient();

        ftpClient.connect(InetAddress.getByName(server));
        ftpClient.login(user, password);
        ftpClient.enterLocalPassiveMode();

        ftpClient.changeWorkingDirectory(serverRoad);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        return ftpClient;
    }

}

