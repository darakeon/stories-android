package com.darakeon.stories.factories;

import com.darakeon.stories.R;
import com.darakeon.stories.domain.Episode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SeasonFactory extends BaseFileFactory
{
    private IContext context;

    public SeasonFactory(IContext context)
    {
        super(context);
        this.context = context;
    }

    public ArrayList<String> GetSeasonList(String seasonString)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.GetMainDirectory();

        assert dir != null;
        File[] filesList = dir.listFiles();

        Arrays.sort(filesList);

        for (File file : filesList)
        {
            if (file.isDirectory() && file.getName().startsWith("_"))
            {
                list.add(seasonString + file.getName().substring(1));
            }
        }

        return list;
    }

    public ArrayList<String> GetEpisodeList(String season)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.GetMainDirectory();

        assert dir != null;

        File seasonDir = new File(dir.getAbsolutePath(), "_" + season);
        File[] filesList = seasonDir.listFiles();

        if (!seasonDir.exists())
        {
            Context.ShowToast(R.string.ERROR_directory_not_found);
            return list;
        }

        for (File file : filesList)
        {
            if (file.isDirectory())
            {
                EpisodeFactory episodeFactory = new EpisodeFactory(Context, file);
                Episode episode = episodeFactory.GetEpisodeMainInfo();

                String episodeName = season + file.getName() + getEpisodeDetails(episode);
                list.add(episodeName);
            }
        }

        return list;
    }

    private String getEpisodeDetails(Episode episode)
    {
        if (episode == null)
            return null;

        String title = ": " + episode.Title;

        Calendar publish = episode.getPublish();

        if (publish == null)
            return title;

        Calendar now = Calendar.getInstance();

        if (publish.getTimeInMillis() < now.getTimeInMillis())
            return title + " [!]";

        int monthNumber = publish.get(Calendar.DAY_OF_MONTH);
        int yearNumber = publish.get(Calendar.MONTH) + 1;

        String monthString = String.format("%02d", monthNumber);
        String yearString = String.format("%02d", yearNumber);

        return title + " - " + monthString + "/" + yearString;
    }

    public boolean CreateEpisode(String season, int lastEpisode)
    {
        File dir = context.GetMainDirectory();
        assert dir != null;

        int newEpisode = lastEpisode + 1;
        String newEpisodeDirName = String.format("%02d", newEpisode);

        File seasonDir = new File(dir, "_" + season);
        File episodeDir = new File(seasonDir, newEpisodeDirName);

        if (episodeDir.exists())
            return true;

        boolean createdDirectory = episodeDir.mkdir();

        if (!createdDirectory)
            return false;

        Tag story = new Tag("story");
        story.AddAttribute("season", season);
        story.AddAttribute("episode", newEpisodeDirName);
        story.AddAttribute("last", "");
        story.AddAttribute("publish", "");

        Tag portuguese = story.Add("portuguese");
        portuguese.AddAttribute("title", "");

        Tag english = story.Add("english");
        english.AddAttribute("title", "");

        story.Add("summary");

        return CreateNewXml(episodeDir, '_', story);
    }

    public boolean CreateSeason(String lastSeason)
    {
        File dir = context.GetMainDirectory();
        assert dir != null;

        char newSeason = lastSeason == null ? 'A' : (char)(lastSeason.charAt(0) + 1);

        File seasonDir = new File(dir, "_" + newSeason);

        if (seasonDir.exists())
            return true;

        boolean directoryCreated = seasonDir.mkdir();

        if (directoryCreated)
        {
            ShowFile(context, seasonDir);
        }

        return directoryCreated;
    }



}
