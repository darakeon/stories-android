package com.darakeon.stories.factories;

import android.app.Activity;
import android.content.Context;

import com.darakeon.stories.R;
import com.darakeon.stories.domain.Episode;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

public class SeasonFactory extends BaseFileFactory
{
    private Context context;

    public SeasonFactory(Context context)
    {
        this.context = context;
    }

    public ArrayList<String> GetSeasonList()
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getExternalFilesDir("");

        assert dir != null;
        File[] filesList = dir.listFiles();

        Arrays.sort(filesList);

        for (File file : filesList)
        {
            if (file.isDirectory() && file.getName().startsWith("_"))
            {
                list.add(context.getString(R.string.season) + file.getName().substring(1));
            }
        }

        return list;
    }

    public ArrayList<String> GetEpisodeList(String season) throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getExternalFilesDir("");

        assert dir != null;

        File seasonDir = new File(dir.getAbsolutePath(), "_" + season);
        File[] filesList = seasonDir.listFiles();

        for (File file : filesList)
        {
            if (file.isDirectory())
            {
                EpisodeFactory episodeFactory = new EpisodeFactory(file);
                Episode episode = episodeFactory.GetEpisodeMainInfo();

                if (episode != null)
                {
                    String title = season + file.getName() + ": " + episode.Title;
                    Calendar publish = episode.getPublish();

                    if (publish != null)
                    {
                        Calendar now = Calendar.getInstance();

                        if (publish.getTimeInMillis() < now.getTimeInMillis())
                        {
                            title += " [!]";
                        } else
                        {
                            title += " - " + String.format("%02d", publish.get(Calendar.DAY_OF_MONTH))
                                    + "/" + String.format("%02d", publish.get(Calendar.MONTH) + 1);
                        }
                    }

                    list.add(title);
                }

            }
        }

        return list;
    }

    public boolean CreateEpisode(Activity activity, String season, int lastEpisode)
    {
        File dir = context.getExternalFilesDir("");
        assert dir != null;

        int newEpisode = lastEpisode + 1;
        String newEpisodeDirName = String.format("%02d", newEpisode);

        File seasonDir = new File(dir, "_" + season);
        File episodeDir = new File(seasonDir, newEpisodeDirName);

        if (episodeDir.exists())
            return true;

        episodeDir.mkdir();

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

        CreateNewXml(activity, episodeDir, '_', story);

        return true;
    }

    public boolean CreateSeason(Activity activity, String lastSeason)
    {
        File dir = context.getExternalFilesDir("");
        assert dir != null;

        char newSeason = lastSeason == null ? 'A' : (char)(lastSeason.charAt(0) + 1);

        File seasonDir = new File(dir, "_" + newSeason);

        if (seasonDir.exists())
            return true;

        seasonDir.mkdir();
        ShowFile(activity, seasonDir);

        return true;
    }



}
