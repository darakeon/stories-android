package com.darakeon.stories.clicks;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.darakeon.stories.activities.SelectEpisodeActivity;

/**
 * Created by Handhara on 09/09/2015.
 */
public class SeasonClick implements AdapterView.OnItemClickListener
{
    private SelectEpisodeActivity activity;

    public SeasonClick(SelectEpisodeActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView textView = (TextView)view;
        String seasonName = (String) textView.getText();
        String season = seasonName.replace("Temporada ", "");

        activity.getEpisodeList(season);
    }
}
