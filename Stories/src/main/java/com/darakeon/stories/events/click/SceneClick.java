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
        setColors();
    }

    private void setColors()
    {
        colorList = new ArrayList<>();
        colorList.add(R.color.scene_1);
        colorList.add(R.color.scene_2);
        colorList.add(R.color.scene_3);
        colorList.add(R.color.scene_4);
        colorList.add(R.color.scene_5);
        colorList.add(R.color.scene_6);
        colorList.add(R.color.scene_7);
    }

    @Override
    public void onClick(View view)
    {
        SceneButton thisScene = (SceneButton)view;
        String scene = thisScene.getText().toString();

        getSelectedSceneAndChange(thisScene);

        try
        {
            activity.SaveCurrentScene();
            activity.SetNewScene(scene);
        }
        catch (ParserConfigurationException | SAXException | ParseException | IOException | TransformerException e)
        {
            e.printStackTrace();
        }

    }

    private void getSelectedSceneAndChange(SceneButton thisScene)
    {
        ListView listView = (ListView)thisScene.getParent().getParent();

        for(int c = 0; c < listView.getChildCount(); c++)
        {
            View child = listView.getChildAt(c);
            SceneButton otherScene = (SceneButton) child.findViewById(R.id.scene_button);

            if (otherScene.getText() != thisScene.getText() && otherScene.IsSelected())
            {
                SetColorByPosition(otherScene, c);
                otherScene.Deselect();
            }
        }

        setColorByResource(thisScene, R.color.scene_chosen);
        thisScene.Select();
    }


    public void SetColorByPosition(SceneButton sceneButton, Integer position)
    {
        int colorPosition = position % colorList.size();
        Integer colorCode = colorList.get(colorPosition);
        setColorByResource(sceneButton, colorCode);
    }

    private void setColorByResource(TextView sceneButton, Integer colorCode)
    {
        int color = ContextCompat.getColor(activity, colorCode);

        sceneButton.setTextColor(color);
        sceneButton.setBackgroundColor(color);
    }

}
