package com.darakeon.stories.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.events.click.EpisodeClick;
import com.darakeon.stories.events.click.SeasonClick;
import com.darakeon.stories.factories.SeasonFactory;

import java.util.ArrayList;

public class SelectEpisodeActivity extends MyActivity
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
        ArrayList<String> list = seasonFactory.GetSeasonList();
        String lastSeason = getLastSeason(list);
        list.add(getString(R.string.plus));

        ListView view = (ListView) findViewById(R.id.season_list);

        ArrayAdapter<String> adapter = getListAdapter(list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new SeasonClick(this, lastSeason));
    }

    @Nullable
    private String getLastSeason(ArrayList<String> list)
    {
        if (list.size() == 0)
            return null;

        String lastSeasonName = list.get(list.size() - 1);
        int letterStartIndex = lastSeasonName.length() - 1;
        int letterEndIndex = lastSeasonName.length();

        return lastSeasonName.substring(letterStartIndex, letterEndIndex);
    }

    public void getEpisodeList(String season)
    {
        ArrayList<String> list = seasonFactory.GetEpisodeList(season);
        int lastEpisodeNumber = getLastEpisode(list);
        list.add(getString(R.string.plus));

        ListView view = (ListView) findViewById(R.id.episode_list);

        ArrayAdapter<String> adapter = getListAdapter(list);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new EpisodeClick(this, season, lastEpisodeNumber));
    }

    private int getLastEpisode(ArrayList<String> list)
    {
        if (list.size() == 0)
            return 0;

        String lastEpisodeName = list.get(list.size() - 1);
        String lastEpisodeNumber = lastEpisodeName.substring(1, 3);

        return Integer.parseInt(lastEpisodeNumber);
    }

    public void goToEpisode(String season, String episode)
    {
        Intent intent = new Intent(this, EditEpisodeActivity.class);
        intent.putExtra("season", season);
        intent.putExtra("episode", episode);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);
    }

    private ArrayAdapter<String> getListAdapter(ArrayList<String> list)
    {
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
    }
}
