package com.darakeon.stories.clicks;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.darakeon.stories.activities.SelectEpisodeActivity;

public class EpisodeClick implements AdapterView.OnItemClickListener
{
    private SelectEpisodeActivity activity;

    public EpisodeClick(SelectEpisodeActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView textView = (TextView)view;

        String episodeTitle = (String) textView.getText();

        String season = episodeTitle.substring(0, 1);
        String episode = episodeTitle.substring(1, 3);

        activity.goToEpisode(season, episode);
    }
}
