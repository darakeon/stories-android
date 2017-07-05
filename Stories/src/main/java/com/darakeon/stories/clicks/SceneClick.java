package com.darakeon.stories.clicks;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.darakeon.stories.activities.EditEpisodeActivity;

/**
 * Created by Keon on 06/02/2016.
 */
public class SceneClick implements AdapterView.OnItemClickListener
{
    private EditEpisodeActivity activity;

    public SceneClick(EditEpisodeActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView textView = (TextView)view;
        String scene = (String) textView.getText();;

        activity.setScene(scene);

    }
}
