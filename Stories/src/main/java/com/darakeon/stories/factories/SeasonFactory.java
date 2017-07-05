package com.darakeon.stories.factories;

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

/**
 * Created by Handhara on 09/09/2015.
 */
public class SeasonFactory
{
    private Context context;

    public SeasonFactory(Context context)
    {
        this.context = context;
    }

    public ArrayList<String> getSeasonList()
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

    public ArrayList<String> getEpisodeList(String season) throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getExternalFilesDir("");

        assert dir != null;

        File seasonDir = new File(dir.getAbsolutePath(), "_" + season);
        File[] filesList = seasonDir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {

                EpisodeFactory episodeFactory = new EpisodeFactory(file);
                Episode episode = episodeFactory.GetEpisodeForList();
                String title = season + file.getName() + ": " + episode.getTitle();
                Calendar publish = episode.getPublish();

                Calendar now = Calendar.getInstance();

                if (publish.getTimeInMillis() < now.getTimeInMillis())
                {
                    title += " [!]";
                }
                else
                {
                    title += " - "+ String.format("%02d", publish.get(Calendar.DAY_OF_MONTH))
                            + "/" + String.format("%02d", publish.get(Calendar.MONTH)+1);
                }

                list.add(title);
            }
        }

        return list;
    }





}
