package com.darakeon.stories.events.draw;

import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.darakeon.stories.adapters.ParagraphAdapter;

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