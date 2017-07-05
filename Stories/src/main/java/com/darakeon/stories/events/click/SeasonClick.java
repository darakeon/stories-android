package com.darakeon.stories.events.click;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.SelectEpisodeActivity;
import com.darakeon.stories.factories.SeasonFactory;

public class SeasonClick implements AdapterView.OnItemClickListener
{
    private SelectEpisodeActivity activity;
    private String lastSeason;

    public SeasonClick(SelectEpisodeActivity activity, String lastSeason)
    {
        this.activity = activity;
        this.lastSeason = lastSeason;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView textView = (TextView)view;
        String seasonName = (String) textView.getText();
        String season = seasonName.replace(activity.getString(R.string.season), "");

        if (season.equals(activity.getString(R.string.plus)))
        {
            SeasonFactory seasonFactory = new SeasonFactory(activity);
            seasonFactory.CreateSeason(activity, lastSeason);

            activity.Refresh();
        }
        else
        {
            activity.getEpisodeList(season);
        }

    }
}
