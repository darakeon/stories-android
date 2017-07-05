package com.darakeon.stories.structure;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Episode
{
    public Episode(Context context, String season, String episode)
    {
        File dir = context.getFilesDir();
        File seasonDir = new File(dir, season);
        File episodeDir = new File(seasonDir, episode);
        File summary = new File(episodeDir, "_.xml");

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(summary));
            StringBuilder sb = new StringBuilder();

            try {
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                sb.toString();
            } finally {
                br.close();
            }

            ByteArrayInputStream input = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

            Element root = doc.getDocumentElement();
            NodeList x = root.getChildNodes();

            for (int n = 0; n < x.getLength(); n++)
            {
                Node y = x.item(n);
                Element e = (Element) y;
                String a = e.getAttribute("x");
            }

        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getList(Context context, String season)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getFilesDir();
        File seasonDir = new File(dir.getAbsolutePath(), season);
        File[] filesList = seasonDir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                list.add(file.getName());
            }
        }

        return list;
    }
}
