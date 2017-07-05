package com.darakeon.stories.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.darakeon.stories.events.blur.EpisodeSummaryBlur;
import com.darakeon.stories.events.blur.EpisodeTitleBlur;
import com.darakeon.stories.events.draw.ParagraphDraw;
import com.darakeon.stories.events.draw.SceneDraw;
import com.darakeon.stories.events.scroll.SceneScroll;
import com.darakeon.stories.factories.EpisodeFactory;

import java.util.ArrayList;

public class EditEpisodeActivity extends MyActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_episode);

        setTitle();
        setFactory();
        setMenu();

        boolean episodeGet = setViewsWithEpisode();

        if (episodeGet)
            getSummary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_episode, menu);

        return true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        SaveCurrentContent(false);
    }


    private EpisodeFactory episodeFactory;

    private void setFactory()
    {
        episodeFactory = new EpisodeFactory(this, seasonLetter, episodeNumber);
    }

    String seasonLetter;
    String episodeNumber;
    String appName;

    public void setTitle()
    {
        if (appName == null)
            appName = getString(R.string.app_name);

        if (seasonLetter == null)
            seasonLetter = getIntent().getStringExtra("season");

        if (episodeNumber == null)
            episodeNumber = getIntent().getStringExtra("episode");

        setTitle((Scene)null);
    }

    public void setTitle(Scene scene)
    {
        String sceneTitle = getSceneTitle(scene);
        setTitle(appName + ": " + seasonLetter + episodeNumber + sceneTitle);
    }

    @NonNull
    private String getSceneTitle(Scene scene)
    {
        if (scene == null)
            return "";

        String sceneLetter = scene.GetLetter();
        long size = scene.GetFileSize();
        int paragraphCount = scene.CountNotEmptyParagraphs();

        return sceneLetter + " (" + normalize(size) + ", " + paragraphCount + "p)";
    }

    private String normalize(long size)
    {
        int magnitude = (int)Math.floor(Math.log(size) / Math.log(1024));

        if (magnitude >= magnitudeList.length)
        {
            magnitude = magnitudeList.length - 1;
        }

        double divisor = Math.pow(1024, magnitude);
        int sizeInMagnitude = (int) Math.round(size / divisor);

        return sizeInMagnitude + magnitudeList[magnitude];
    }

    private String[] magnitudeList = new String[]{ "B", "KB", "MB", "GB", "TB" };

    private void setMenu()
    {
        ListView view = (ListView) findViewById(R.id.scene_button_list);

        if (view != null)
        {
            ArrayList<String> list = episodeFactory.GetEpisodeSceneLetterList();
            SceneLetterAdapter adapter = new SceneLetterAdapter(this, list);

            view.setAdapter(adapter);

            ViewTreeObserver observer = view.getViewTreeObserver();
            observer.addOnPreDrawListener(new SceneDraw(adapter, view));
        }
    }

    private ListView sceneView;
    private LinearLayout mainInfoView;
    private EditText titleView;
    private EditText summaryView;
    private EditText publishView;

    private boolean setViewsWithEpisode()
    {
        sceneView = (ListView) findViewById(R.id.scene_edit);

        mainInfoView = (LinearLayout) findViewById(R.id.main_info);

        titleView = (EditText) findViewById(R.id.main_info_title);
        publishView = (EditText) findViewById(R.id.main_info_publish);
        summaryView = (EditText) findViewById(R.id.main_info_summary);

        episode = episodeFactory.GetEpisodeMainInfo();
        boolean episodeExists = episode != null;

        if (episodeExists)
        {
            titleView.setOnFocusChangeListener(new EpisodeTitleBlur(episode));
            publishView.setOnFocusChangeListener(new EpisodePublishBlur(episode));
            summaryView.setOnFocusChangeListener(new EpisodeSummaryBlur(episode));
        }
        else
        {
            toggleSummary(false);
        }

        return episodeExists;
    }



    private Episode episode;
    private Scene scene;

    private void toggleSummary(boolean show)
    {
        if (sceneView == null)
            return;

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

    public void ChangeScene(String sceneLetter)
    {
        toggleSummary(false);

        boolean saved = SaveCurrentContent(true);

        if (!saved)
            return;

        scene = episodeFactory.GetScene(sceneLetter);

        if (scene == null)
            return;

        setTitle(scene);

        final ParagraphAdapter adapter = new ParagraphAdapter(scene.GetParagraphList(), getLayoutInflater());

        sceneView.setAdapter(adapter);
        sceneView.setOnScrollListener(new SceneScroll());

        ViewTreeObserver observer = sceneView.getViewTreeObserver();
        observer.addOnDrawListener(new ParagraphDraw(adapter, sceneView));
    }

    public void GetSummary()
    {
        boolean saved = SaveCurrentContent(true);

        if (saved)
        {
            getSummary();
        }
    }

    private void getSummary()
    {
        toggleSummary(true);
        titleView.setText(episode.Title);
        publishView.setText(episode.Publish);
        summaryView.setText(episode.Summary);
    }

    public boolean SaveCurrentContent(boolean isClosing)
    {
        View focused = getCurrentFocus();

        if (focused != null)
        {
            focused.clearFocus();
        }

        boolean saved;

        if (scene == null)
        {
            saved = episodeFactory.SaveMainInfo(episode);
        }
        else
        {
            saved = episodeFactory.SaveScene(scene, isClosing);

            if (!isClosing)
            {
                setTitle(scene);
            }
        }

        if (saved)
        {
            ShowToast(R.string.saved);
        }

        return saved;
    }

    public void AddScene()
    {
        boolean saved = SaveCurrentContent(true);

        if (saved)
            saved = episodeFactory.AddScene();

        if (saved)
            Refresh();
    }



    public void GetSummary(MenuItem menuItem)
    {
        GetSummary();
    }

    public void SaveCurrentContent(MenuItem menuItem)
    {
        SaveCurrentContent(false);
    }

    public void SelectAll(MenuItem menuItem)
    {
        EditText editText = (EditText) getCurrentFocus();

        if (editText != null)
        {
            editText.selectAll();
        }
    }



}

