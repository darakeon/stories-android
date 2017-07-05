package com.darakeon.stories.events.click;

import android.view.View;

import com.darakeon.stories.activities.EditEpisodeActivity;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class AddSceneClick implements View.OnClickListener
{
    private EditEpisodeActivity activity;

    public AddSceneClick(EditEpisodeActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            activity.SaveCurrentContent();
            activity.AddScene();
        }
        catch (ParserConfigurationException | TransformerException e)
        {
            e.printStackTrace();
        }
    }


}
