package com.darakeon.stories.factories;

import com.darakeon.stories.domain.Episode;
import com.darakeon.stories.domain.Scene;

import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

public class EpisodeFactory extends BaseFileFactory
{
    private File episodeDirectory;

    public EpisodeFactory(IContext context, File episodeDirectory)
    {
        super(context);
        this.episodeDirectory = episodeDirectory;
    }

    public EpisodeFactory(IContext context, String seasonLetter, String episodeNumber)
    {
        super(context);
        File externalFilesDirectory = context.GetMainDirectory();
        File seasonDirectory = new File(externalFilesDirectory, "_" + seasonLetter);
        this.episodeDirectory = new File(seasonDirectory, episodeNumber);
    }

    public Episode GetEpisodeMainInfo()
    {
        Element node = getEpisodeXml("_");

        if (node == null)
            return null;

        Episode episode = new Episode(node);
        episode.SetMainInfo();

        return episode;
    }

    public Scene GetScene(String sceneLetter)
    {
        Element node = getEpisodeXml(sceneLetter);

        if (node == null)
            return null;

        return new Scene(sceneLetter, node, getFileSize(sceneLetter));
    }

    private long getFileSize(String filename)
    {
        return new File(episodeDirectory, filename + ".xml").length();
    }

    private Element getEpisodeXml(String filename)
    {
        File file = new File(episodeDirectory, filename + ".xml");
        return GetXml(file);
    }

    public ArrayList<String> GetEpisodeSceneLetterList()
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

    public boolean SaveScene(Scene scene, boolean isClosing)
    {
        if (isClosing)
            scene.SaveCleaning();
        else
            scene.Save();

        File file = new File(episodeDirectory, scene.GetLetter() + ".xml");

        return SetFileBody(file, scene.GetNode());
    }

    public boolean SaveMainInfo(Episode episode)
    {
        episode.Save();

        File file = new File(episodeDirectory, "_.xml");

        return SetFileBody(file, episode.getNode());
    }

    public boolean AddScene()
    {
        ArrayList<String> sceneLetters = GetEpisodeSceneLetterList();

        char newScene = getNewScene(sceneLetters);

        if (newScene > 'z')
        {
            return false;
        }

        Tag story = new Tag("story");
        Tag paragraph = story.Add("teller");
        paragraph.Add("default");

        return CreateNewXml(episodeDirectory, newScene, story);
    }

    private char getNewScene(ArrayList<String> sceneLetters)
    {
        if (sceneLetters.size() == 0)
            return 'a';

        String lastScene = sceneLetters.get(sceneLetters.size() - 1);

        return (char)(lastScene.charAt(0) + 1);
    }


}

