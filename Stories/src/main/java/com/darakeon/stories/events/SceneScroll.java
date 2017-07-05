package com.darakeon.stories.events;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Keon on 15/02/2016.
 */
public class SceneScroll implements ListView.OnScrollListener
{
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        // do nothing
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if (SCROLL_STATE_TOUCH_SCROLL == scrollState)
        {
            Activity activity = (Activity) view.getContext();
            View currentFocus = activity.getCurrentFocus();

            if (currentFocus != null)
            {
                currentFocus.clearFocus();
            }
        }
    }

}