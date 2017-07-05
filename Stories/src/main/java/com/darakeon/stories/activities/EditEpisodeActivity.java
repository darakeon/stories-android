package com.darakeon.stories.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.adapters.ParagraphAdapter;
import com.darakeon.stories.adapters.SceneLetterAdapter;
import com.darakeon.stories.domain.Episode;
import com.darakeon.stories.domain.Scene;
import com.darakeon.stories.events.blur.EpisodePublishBlur;
import com.darakeon.stories.events.blur.EpisodeTitleBlur;
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

public class EditEpisodeActivity extends MyActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_episode);

        setFactory();
        setMenu();
        setViews();
        GetSummary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_episode, menu);

        return true;
    }

    private EpisodeFactory episodeFactory;

    private void setFactory()
    {
        String season = getIntent().getStringExtra("season");
        String episode = getIntent().getStringExtra("episode");

        episodeFactory = new EpisodeFactory(this, season, episode);
    }

    private void setMenu()
    {
        try
        {
            ArrayList<String> list = episodeFactory.GetEpisodeSceneLetterList();

            ListView view = (ListView) findViewById(R.id.scene_button_list);
            SceneLetterAdapter adapter = new SceneLetterAdapter(this, list);

            view.setAdapter(adapter);

            ViewTreeObserver observer = view.getViewTreeObserver();
            observer.addOnPreDrawListener(new SceneDraw(adapter, view));
        }
        catch (ParserConfigurationException | SAXException | IOException | ParseException e)
        {
            e.printStackTrace();
        }
    }

    private ListView sceneView;
    private LinearLayout mainInfoView;
    private EditText titleView;
    private EditText summaryView;
    private EditText publishView;

    private void setViews()
    {
        sceneView = (ListView) findViewById(R.id.scene_edit);

        mainInfoView = (LinearLayout) findViewById(R.id.main_info);

        titleView = (EditText) findViewById(R.id.main_info_title);
        publishView = (EditText) findViewById(R.id.main_info_publish);
        summaryView = (EditText) findViewById(R.id.main_info_summary);

        try
        {
            episode = episodeFactory.GetEpisodeMainInfo();
            titleView.setOnFocusChangeListener(new EpisodeTitleBlur(episode));
            publishView.setOnFocusChangeListener(new EpisodePublishBlur(episode));
            titleView.setOnFocusChangeListener(new EpisodeTitleBlur(episode));
        }
        catch (IOException | ParserConfigurationException | ParseException | SAXException e)
        {
            e.printStackTrace();
            toggleSummary(false);
        }
    }



    private Episode episode;
    private Scene scene;

    private void toggleSummary(boolean show)
    {
        if (show)
        {
            scene = null;

            mainInfoView.setVisibility(View.VISIBLE);
            sceneView.setVisibility(View.GONE);
        }
        else
        {
            mainInfoView.setVisibility(View.GONE);
            sceneView.setVisibility(View.VISIBLE);
        }
    }

    public void GetScene(String sceneLetter) throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        toggleSummary(false);

        scene = episodeFactory.GetScene(sceneLetter);

        final ParagraphAdapter adapter = new ParagraphAdapter(scene.GetParagraphList(), getLayoutInflater());

        sceneView.setAdapter(adapter);
        sceneView.setOnScrollListener(new SceneScroll());

        ViewTreeObserver observer = sceneView.getViewTreeObserver();
        observer.addOnDrawListener(new ParagraphDraw(adapter, sceneView));
    }

    public void GetSummary()
    {
        toggleSummary(true);

        titleView.setText(episode.Title);
        publishView.setText(episode.Publish);
        summaryView.setText(episode.Summary);
    }

    public void SaveCurrentContent() throws TransformerException, ParserConfigurationException
    {
        if (scene != null)
            episodeFactory.SaveScene(scene);
        else
            episodeFactory.SaveMainInfo(episode);
    }



    public void GetSummary(MenuItem menuItem) throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        GetSummary();
    }

    public void SaveCurrentContent(MenuItem menuItem) throws TransformerException, ParserConfigurationException
    {
        SaveCurrentContent();
    }


    public void AddScene() throws ParserConfigurationException, TransformerException, SAXException, ParseException, IOException
    {
        SaveCurrentContent();
        episodeFactory.AddScene(this);
        Refresh();
    }
}

