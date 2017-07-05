package com.darakeon.stories.events.click;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.views.SceneButton;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class SceneClick implements View.OnClickListener
{
    private EditEpisodeActivity activity;

    public SceneClick(EditEpisodeActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClick(View view)
    {
        SceneButton thisScene = (SceneButton)view;
        String scene = thisScene.GetLetter();

        getSelectedSceneAndChange(thisScene);

        try
        {
            activity.SaveCurrentContent();
            activity.GetScene(scene);
        }
        catch (ParserConfigurationException | SAXException | ParseException | IOException | TransformerException e)
        {
            e.printStackTrace();
        }

    }

    private void getSelectedSceneAndChange(SceneButton thisScene)
    {
        ListView listView = (ListView)thisScene.getParent();

        for(int c = 0; c < listView.getChildCount(); c++)
        {
            SceneButton otherScene = (SceneButton)listView.getChildAt(c);

            if (!otherScene.GetLetter().equals(thisScene.GetLetter()) && otherScene.IsSelected())
            {
                setInactive(otherScene);
                otherScene.Deselect();
            }
        }

        setActive(thisScene);
        thisScene.Select();
    }


    private void setInactive(SceneButton sceneButton)
    {
        changeColors(sceneButton, activity, R.drawable.puzzle, R.color.white, R.color.black);
    }

    private void setActive(SceneButton sceneButton)
    {
        changeColors(sceneButton, activity, R.drawable.puzzle_selected, R.color.black, R.color.white);
    }

    private void changeColors(SceneButton sceneButton, EditEpisodeActivity activity, int imageId, int textColorId, int backgroundColorId)
    {
        int backgroundColor = ContextCompat.getColor(activity, backgroundColorId);
        int textColor = ContextCompat.getColor(activity, textColorId);

        sceneButton.setBackgroundColor(backgroundColor);
        sceneButton.ImageView.setImageResource(imageId);

        sceneButton.TextView.setTextColor(textColor);
        float radius = sceneButton.TextView.getShadowRadius();
        float dx = sceneButton.TextView.getShadowDx();
        float dy = sceneButton.TextView.getShadowDy();
        sceneButton.TextView.setShadowLayer(radius, dx, dy, backgroundColor);
    }

}
