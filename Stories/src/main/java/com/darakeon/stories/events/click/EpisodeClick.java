package com.darakeon.stories.events.click;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.SelectEpisodeActivity;
import com.darakeon.stories.factories.SeasonFactory;

public class EpisodeClick implements AdapterView.OnItemClickListener
{
    private SelectEpisodeActivity activity;
    private String season;
    private int lastEpisode;

    public EpisodeClick(SelectEpisodeActivity activity, String season, int lastEpisode)
    {
        this.activity = activity;
        this.season = season;
        this.lastEpisode = lastEpisode;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView textView = (TextView)view;

        String episodeTitle = (String) textView.getText();

        if (episodeTitle.equals(activity.getString(R.string.plus)))
        {
            SeasonFactory seasonFactory = new SeasonFactory(activity);
            boolean created = seasonFactory.CreateEpisode(activity, season, lastEpisode);

            if (created)
            {
                activity.Refresh();
            }
        }
        else
        {
            String season = episodeTitle.substring(0, 1);
            String episode = episodeTitle.substring(1, 3);

            activity.goToEpisode(season, episode);
        }
    }
}
