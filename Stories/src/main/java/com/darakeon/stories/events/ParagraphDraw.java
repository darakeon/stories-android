package com.darakeon.stories.events;

import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.darakeon.stories.adapters.ParagraphAdapter;

/**
 * Created by Keon on 09/02/2016.
 */
public class ParagraphDraw implements ViewTreeObserver.OnDrawListener
{
    private final ParagraphAdapter adapter;
    private final ListView view;

    public ParagraphDraw(ParagraphAdapter adapter, ListView view)
    {
        this.adapter = adapter;
        this.view = view;
    }

    @Override
    public void onDraw()
    {
        adapter.AdjustAllHeight(view);
    }
}