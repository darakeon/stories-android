package com.darakeon.stories.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.clicks.EpisodeClick;
import com.darakeon.stories.clicks.SeasonClick;
import com.darakeon.stories.structure.SeasonFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class SelectEpisodeActivity extends Activity
{
    private SeasonFactory seasonFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_episode);

        seasonFactory = new SeasonFactory(this);

        getSeasonList();
    }

    private void getSeasonList()
    {
        ArrayList<String> list = seasonFactory.getSeasonList();
        ArrayAdapter<String> adapter = getListAdapter(list);
        ListView view = (ListView) findViewById(R.id.season_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new SeasonClick(this));
    }

    public void getEpisodeList(String season) throws ParserConfigurationException, SAXException, ParseException, IOException
    {
        ArrayList<String> list = seasonFactory.getEpisodeList(season);
        ArrayAdapter<String> adapter = getListAdapter(list);
        ListView view = (ListView) findViewById(R.id.episode_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new EpisodeClick(this));
    }

    public void goToEpisode(String season, String episode)
    {
        Intent intent = new Intent(this, EditEpisodeActivity.class);
        intent.putExtra("season", season);
        intent.putExtra("episode", episode);

        startActivity(intent);
    }

    private ArrayAdapter<String> getListAdapter(ArrayList<String> list)
    {
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
    }
}
