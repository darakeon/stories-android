package com.darakeon.stories.events.draw;

import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.darakeon.stories.adapters.SceneLetterAdapter;

public class SceneDraw implements ViewTreeObserver.OnPreDrawListener
{
    private final SceneLetterAdapter adapter;
    private final ListView view;

    public SceneDraw(SceneLetterAdapter adapter, ListView view)
    {
        this.adapter = adapter;
        this.view = view;
    }

    @Override
    public boolean onPreDraw()
    {
        adapter.AdjustHeight(view);
        return true;
    }
}
