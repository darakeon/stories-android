package com.darakeon.stories.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.events.click.AddSceneClick;
import com.darakeon.stories.events.click.SceneClick;
import com.darakeon.stories.views.SceneButton;

import java.util.ArrayList;

public class SceneLetterAdapter extends BaseAdapter
{
    private final EditEpisodeActivity activity;
    private final ArrayList<String> sceneLetterList;

    private static LayoutInflater inflater = null;

    private final String PLUS = "+";

    public SceneLetterAdapter(EditEpisodeActivity activity, ArrayList<String> sceneLetterList)
    {
        this.activity = activity;
        this.sceneLetterList = sceneLetterList;

        sceneLetterList.add(PLUS);

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return sceneLetterList.size(); }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View rowView = inflater.inflate(R.layout.edit_episode_scene_button_list, null);

        setButton(rowView, position);

        return rowView;
    }

    private void setButton(View rowView, int position)
    {
        SceneButton sceneButton = (SceneButton) rowView;

        String sceneLetter = sceneLetterList.get(position);
        sceneButton.SetLetter(sceneLetter);

        if (sceneLetter.equals(PLUS))
        {
            AddSceneClick sceneClick = new AddSceneClick(activity);
            sceneButton.setOnClickListener(sceneClick);
        }
        else
        {
            SceneClick sceneClick = new SceneClick(activity);
            sceneButton.setOnClickListener(sceneClick);
        }
    }

    public void AdjustHeight(ListView listView)
    {
        int buttonHeight = listView.getHeight() / sceneLetterList.size();

        for (int v = 0; v < listView.getChildCount(); v++)
        {
            SceneButton childView = (SceneButton)listView.getChildAt(v);

            childView.setMinimumHeight(buttonHeight);
            childView.TextView.setMinimumHeight(buttonHeight);
            childView.ImageView.setMinimumHeight(buttonHeight);
        }

    }

}
