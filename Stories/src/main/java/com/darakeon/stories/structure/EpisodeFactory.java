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
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Handhara on 09/09/2015.
 */
public class EpisodeFactory
{
    public static Episode GetEpisodeJustWithName(File episodeDir)
    {
        Episode episode = null;

        try
        {
            File summary = new File(episodeDir, "_.xml");

            BufferedReader br = new BufferedReader(new FileReader(summary));
            StringBuilder sb = new StringBuilder();

            try {
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
            } finally {
                br.close();
            }

            ByteArrayInputStream input = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

            Element storyNode = doc.getDocumentElement();

            episode = new Episode(storyNode);
            episode.SetMainInfo();

        } catch (ParserConfigurationException | SAXException | IOException | ParseException e)
        {
            e.printStackTrace();
        }

        return episode;
    }

    public static Episode GetCompleteEpisode(Context context, String seasonLetter, String episodeNumber)
    {
        File episodeFile = episodePath(context, seasonLetter, episodeNumber);
        Episode episode = GetEpisodeJustWithName(episodeFile);



        return episode;
    }

    private static File episodePath(Context context, String season, String episode)
    {
        File dir = context.getExternalFilesDir("");
        File seasonDir = new File(dir, season);
        return new File(seasonDir, episode);
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

    public static ArrayList<String> getList(Context context, String season)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getExternalFilesDir("");
        File seasonDir = new File(dir.getAbsolutePath(), "_" + season);
        File[] filesList = seasonDir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                Episode episode = EpisodeFactory.GetEpisodeJustWithName(file);
                String title = file.getName() + ": " + episode.getTitle();
                Calendar publish = episode.getPublish();

                Calendar now = Calendar.getInstance();
                if (publish.getTimeInMillis() < now.getTimeInMillis())
                {
                    title += " [!]";
                }
                else
                {
                    title += " - "+ String.format("%02d", publish.get(publish.DAY_OF_MONTH))
                            + "/" + String.format("%02d", publish.get(publish.MONTH)+1);
                }

                list.add(title);
            }
        }

        return list;
    }
}
