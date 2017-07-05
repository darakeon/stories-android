package com.darakeon.stories.events.click;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Keon on 06/02/2016.
 */
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
        TextView textView = (TextView)view;
        String scene = (String) textView.getText();

        try
        {
            activity.setScene(scene);
        }
        catch (ParserConfigurationException | SAXException | ParseException | IOException e)
        {
            e.printStackTrace();
            return;
        }

        ListView listView = (ListView)textView.getParent().getParent();

        for(int c = 0; c < listView.getChildCount(); c++)
        {
            View child = listView.getChildAt(c);
            TextView otherScene = (TextView) child.findViewById(R.id.scene_button);

            if (otherScene.getText() != textView.getText())
            {
                SetColorByPosition(otherScene, c);
            }
        }

        SetColorByResource(textView, R.color.scene_chosen);
    }

    public void SetColorByPosition(TextView sceneButton, Integer position)
    {
        int colorPosition = position % colorList.size();
        Integer colorCode = colorList.get(colorPosition);
        SetColorByResource(sceneButton, colorCode);
    }

    public void SetColorByResource(TextView sceneButton, Integer colorCode)
    {
        int color = ContextCompat.getColor(activity, colorCode);

        sceneButton.setTextColor(color);
        sceneButton.setBackgroundColor(color);
    }

}
