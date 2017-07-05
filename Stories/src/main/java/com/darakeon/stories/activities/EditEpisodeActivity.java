package com.darakeon.stories.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.adapters.ParagraphAdapter;
import com.darakeon.stories.clicks.SceneClick;
import com.darakeon.stories.domain.Episode;
import com.darakeon.stories.domain.Scene;
import com.darakeon.stories.factories.EpisodeFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

public class EditEpisodeActivity extends Activity
{
    private EpisodeFactory episodeFactory;
    private Episode episode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_episode);

        String season = getIntent().getStringExtra("season");
        String episode = getIntent().getStringExtra("episode");

        episodeFactory = new EpisodeFactory(this, season, episode);

        try
        {
            setScenes();
        }
        catch (ParserConfigurationException | SAXException | IOException | ParseException e)
        {
            e.printStackTrace();
        }
    }


    private void setScenes() throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        episode = episodeFactory.GetCompleteEpisode();
        String[] list = episode.GetSceneLetterList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.edit_episode_scene_button_list, R.id.scene_button, list);
        ListView view = (ListView) findViewById(R.id.scene_button_list);

        view.setAdapter(adapter);
        view.setOnItemClickListener(new SceneClick(this));
    }

    public void setScene(String sceneLetter)
    {
        Scene scene = episode.GetScene(sceneLetter);

        ParagraphAdapter adapter = new ParagraphAdapter(this, scene.GetParagraphList());
        ListView view = (ListView) findViewById(R.id.scene_edit);

        view.setAdapter(adapter);
        view.setItemsCanFocus(true);
    }
}

