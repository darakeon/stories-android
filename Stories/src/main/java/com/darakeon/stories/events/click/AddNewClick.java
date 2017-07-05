package com.darakeon.stories.events.click;

import android.view.View;

public class AddNewClick implements View.OnClickListener
{
    private final IChildWithSibs item;
    private final Object[] arg;

    public AddNewClick(IChildWithSibs item, Object... arg)
    {
        this.item = item;
        this.arg = arg;
    }

    @Override
    public void onClick(View v)
    {
        item.AddSibling(arg);
    }

    public interface IChildWithSibs
    {
        void AddSibling(Object... arg);
    }
}
