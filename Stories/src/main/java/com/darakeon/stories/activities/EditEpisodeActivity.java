package com.darakeon.stories.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.adapters.ParagraphAdapter;
import com.darakeon.stories.adapters.SceneLetterAdapter;
import com.darakeon.stories.domain.Scene;
import com.darakeon.stories.events.draw.ParagraphDraw;
import com.darakeon.stories.events.draw.SceneDraw;
import com.darakeon.stories.events.scroll.SceneScroll;
import com.darakeon.stories.factories.EpisodeFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class EditEpisodeActivity extends Activity
{
    private EpisodeFactory episodeFactory;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_episode, menu);

        return true;
    }

    private void setScenes() throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        ArrayList<String> list = episodeFactory.GetEpisodeSceneList();

        ListView view = (ListView) findViewById(R.id.scene_button_list);
        SceneLetterAdapter adapter = new SceneLetterAdapter(this, list);

        view.setAdapter(adapter);

        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnPreDrawListener(new SceneDraw(adapter, view));
    }



    Scene scene;

    public void SetNewScene(String sceneLetter) throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        scene = episodeFactory.GetScene(sceneLetter);

        ListView view = (ListView) findViewById(R.id.scene_edit);
        final ParagraphAdapter adapter = new ParagraphAdapter(scene.GetParagraphList(), getLayoutInflater());

        view.setAdapter(adapter);
        view.setOnScrollListener(new SceneScroll());

        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnDrawListener(new ParagraphDraw(adapter, view));
    }

    public void SaveCurrentScene() throws TransformerException, ParserConfigurationException
    {
        SaveCurrentScene(null);
    }

    public void SaveCurrentScene(MenuItem menuItem) throws TransformerException, ParserConfigurationException
    {
        if (scene != null)
            episodeFactory.SaveScene(scene);
    }
}

