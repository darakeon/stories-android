package com.darakeon.stories.factories;

import android.content.Context;

import com.darakeon.stories.domain.Episode;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Handhara on 09/09/2015.
 */
public class EpisodeFactory extends BaseFileFactory
{
    private File episodeDirectory;

    public EpisodeFactory(File episodeDirectory)
    {
        this.episodeDirectory = episodeDirectory;
    }

    public EpisodeFactory(Context context, String seasonLetter, String episodeNumber)
    {
        File externalFilesDirectory = context.getExternalFilesDir("");
        File seasonDirectory = new File(externalFilesDirectory, "_" + seasonLetter);
        this.episodeDirectory = new File(seasonDirectory, episodeNumber);
    }

    public Episode GetEpisodeForList() throws IOException, ParserConfigurationException, SAXException, ParseException
    {
        Element node = GetEpisodePiece("_");

        Episode episode = new Episode(node);
        episode.SetMainInfo();

        return episode;
    }

    public Element GetEpisodePiece(String filename) throws IOException, ParserConfigurationException, SAXException, ParseException
    {
        File summary = new File(episodeDirectory, filename + ".xml");
        return getFileBody(summary);
    }

    public ArrayList<String> GetEpisodeSceneList() throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        File[] files = episodeDirectory.listFiles();
        ArrayList<String> sceneList = new ArrayList<>();

        for (File file : files)
        {
            if (!file.getName().startsWith("_"))
            {
                String sceneLetter = file.getName().substring(0, 1);
                sceneList.add(sceneLetter);
            }
        }

        return sceneList;
    }



}

