package com.darakeon.stories.factories;

import android.content.Context;

import com.darakeon.stories.domain.Episode;
import com.darakeon.stories.domain.Scene;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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

    public Episode GetEpisodeMainInfo() throws IOException, ParserConfigurationException, SAXException, ParseException
    {
        Element node = getEpisodePiece("_");

        Episode episode = new Episode(node);
        episode.SetMainInfo();

        return episode;
    }

    public Scene GetScene(String sceneLetter) throws IOException, ParserConfigurationException, SAXException, ParseException
    {
        Element sceneNode = getEpisodePiece(sceneLetter);
        return new Scene(sceneLetter, sceneNode);
    }

    private Element getEpisodePiece(String filename) throws IOException, ParserConfigurationException, SAXException, ParseException
    {
        File file = new File(episodeDirectory, filename + ".xml");
        return GetFileBody(file);
    }

    public ArrayList<String> GetEpisodeSceneLetterList() throws ParserConfigurationException, SAXException, ParseException, IOException
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

    public void SaveScene(Scene scene) throws TransformerException, ParserConfigurationException
    {
        scene.Save();

        File file = new File(episodeDirectory, scene.GetLetter() + ".xml");

        SetFileBody(file, scene.GetNode());
    }

    public void SaveMainInfo(Episode episode) throws TransformerException, ParserConfigurationException
    {
        episode.Save();

        File file = new File(episodeDirectory, "_.xml");

        SetFileBody(file, episode.getNode());
    }

    public void AddScene(IFileUncover fileUncover) throws ParserConfigurationException, SAXException, ParseException, IOException, TransformerException
    {
        ArrayList<String> sceneLetters = GetEpisodeSceneLetterList();

        String lastScene = sceneLetters.size() == 0
                ? "a" : sceneLetters.get(sceneLetters.size() - 1);

        char newScene = (char)(lastScene.charAt(0) + 1);

        Tag story = new Tag("story");
        Tag paragraph = story.Add("teller");
        paragraph.Add("default");

        CreateNewXml(fileUncover, episodeDirectory, newScene, story);
    }


}

