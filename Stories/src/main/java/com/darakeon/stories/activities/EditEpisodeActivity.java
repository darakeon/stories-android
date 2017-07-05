package com.darakeon.stories.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.clicks.SceneClick;
import com.darakeon.stories.factories.EpisodeFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

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

    private void setScenes() throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        String[] list = episodeFactory.GetCompleteEpisode().GetSceneLetterList();
        ArrayAdapter<String> adapter = getListAdapter(list);
        ListView view = (ListView) findViewById(R.id.scene_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new SceneClick(this));
    }

    private ArrayAdapter<String> getListAdapter(String[] list)
    {
        return new ArrayAdapter<>(this, R.layout.scene_button, R.id.scene_button, list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_episode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setScene(String scene)
    {

    }
}

