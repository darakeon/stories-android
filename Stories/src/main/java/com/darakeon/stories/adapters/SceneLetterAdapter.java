package com.darakeon.stories.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.events.SceneClick;

import java.util.ArrayList;

/**
 * Created by Keon on 09/02/2016.
 */
public class SceneLetterAdapter extends BaseAdapter
{
    private final EditEpisodeActivity activity;
    private final ArrayList<String> sceneLetterList;

    private static LayoutInflater inflater = null;

    public SceneLetterAdapter(EditEpisodeActivity activity, ArrayList<String> sceneLetterList)
    {
        this.activity = activity;
        this.sceneLetterList = sceneLetterList;

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
        TextView sceneButton = (TextView) rowView.findViewById(R.id.scene_button);

        String sceneLetter = sceneLetterList.get(position);
        sceneButton.setText(sceneLetter);

        SceneClick sceneClick = new SceneClick(activity);
        sceneClick.SetColorByPosition(sceneButton, position);

        sceneButton.setOnClickListener(sceneClick);
    }

    public void AdjustHeight(ListView listView)
    {
        int buttonHeight = listView.getHeight() / sceneLetterList.size();

        for (int v = 0; v < listView.getChildCount(); v++)
        {
            View childView = listView.getChildAt(v);
            TextView sceneButton = (TextView) childView.findViewById(R.id.scene_button);

            childView.setMinimumHeight(buttonHeight);
            sceneButton.setHeight(buttonHeight);
        }

    }

}
