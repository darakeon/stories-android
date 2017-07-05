package com.darakeon.stories.events.click;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.views.SceneButton;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class SceneClick implements View.OnClickListener
{
    private EditEpisodeActivity activity;

    private ArrayList<Integer> colorList;

    public SceneClick(EditEpisodeActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClick(View view)
    {
        SceneButton thisScene = (SceneButton)view;
        String scene = thisScene.Letter;

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
        View parent = (View)thisScene.getParent();
        ListView listView = (ListView)parent.getParent();
        TextView thisSceneLetter = (TextView)parent.findViewById(R.id.scene_button_letter);

        for(int c = 0; c < listView.getChildCount(); c++)
        {
            View child = listView.getChildAt(c);
            SceneButton otherScene = (SceneButton) child.findViewById(R.id.scene_button);
            TextView otherSceneLetter = (TextView) child.findViewById(R.id.scene_button_letter);

            if (!otherScene.Letter.equals(thisScene.Letter) && otherScene.IsSelected())
            {
                setInactive(otherScene, otherSceneLetter);
                otherScene.Deselect();
            }
        }

        setActive(thisScene, thisSceneLetter);
        thisScene.Select();
    }


    private void setInactive(SceneButton sceneButton, TextView sceneButtonLetter)
    {
        changeColors(sceneButton, sceneButtonLetter, activity, R.drawable.puzzle, R.color.black, R.color.white);
    }

    private void setActive(SceneButton sceneButton, TextView sceneButtonLetter)
    {
        changeColors(sceneButton, sceneButtonLetter, activity, R.drawable.puzzle_selected, R.color.white, R.color.black);
    }

    private void changeColors(SceneButton sceneButton, TextView sceneButtonLetter, EditEpisodeActivity activity, int imageId, int backgroundColorId, int textColorId)
    {
        int backgroundColor = ContextCompat.getColor(activity, backgroundColorId);
        int textColor = ContextCompat.getColor(activity, textColorId);

        sceneButton.setBackgroundColor(backgroundColor);
        sceneButton.setImageResource(imageId);

        sceneButtonLetter.setTextColor(textColor);
        float radius = sceneButtonLetter.getShadowRadius();
        float dx = sceneButtonLetter.getShadowDx();
        float dy = sceneButtonLetter.getShadowDy();
        sceneButtonLetter.setShadowLayer(radius, dx, dy, backgroundColor);
    }

}
