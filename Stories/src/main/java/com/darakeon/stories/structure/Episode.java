package com.darakeon.stories.structure;

import android.content.Context;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Episode
{
    public Episode(File episodeDir)
    {
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

            Node portugueseNode = storyNode.getElementsByTagName("portuguese").item(0);

            title = portugueseNode.getAttributes().getNamedItem("title").getTextContent();

            String textPublish = storyNode.getAttributes().getNamedItem("publish").getTextContent();

            publish = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA_FRENCH);
            publish.setTime(sdf.parse(textPublish));

        } catch (ParserConfigurationException | SAXException | IOException | ParseException e)
        {
            e.printStackTrace();
        }

    }

    public Episode(Context context, String season, String episode)
    {
        this(episodePath(context, season, episode));
    }

    private static File episodePath(Context context, String season, String episode)
    {
        File dir = context.getExternalFilesDir("");
        File seasonDir = new File(dir, season);
        return new File(seasonDir, episode);
    }

    private String title;
    private Calendar publish;

    public String getTitle()
    {
        return title;
    }

    public Calendar getPublish()
    {
        return this.publish;
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
                Episode episode = new Episode(file);
                String title = file.getName() + ": " + episode.getTitle();

                Calendar now = Calendar.getInstance();
                if (episode.getPublish().getTimeInMillis() < now.getTimeInMillis())
                {
                    title += " [!]";
                }

                list.add(title);
            }
        }

        return list;
    }
}
