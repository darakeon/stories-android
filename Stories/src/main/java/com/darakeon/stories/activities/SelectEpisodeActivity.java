package com.darakeon.stories.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.clicks.SeasonClick;
import com.darakeon.stories.structure.Episode;
import com.darakeon.stories.structure.Season;

import java.util.ArrayList;

public class SelectEpisodeActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_episode);

        getSeasonList();
    }

    private void getSeasonList()
    {
        ArrayList<String> list = Season.getList(this);
        ArrayAdapter<String> adapter = getListAdapter(list);
        ListView view = (ListView) findViewById(R.id.season_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new SeasonClick(this));
    }

    public void getEpisodeList(String season)
    {
        ArrayList<String> list = Episode.getList(this, season);
        ArrayAdapter<String> adapter = getListAdapter(list);
        ListView view = (ListView) findViewById(R.id.episode_list);
        view.setAdapter(adapter);
    }

    private ArrayAdapter<String> getListAdapter(ArrayList<String> list)
    {
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
    }

}
