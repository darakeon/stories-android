package com.darakeon.stories.events.click;

import android.view.View;

/**
 * Created by Keon on 05/03/2016.
 */
public class AddNewListener implements View.OnClickListener
{
    private final IChildWithSibs item;
    private final Object[] arg;

    public AddNewListener(IChildWithSibs item, Object... arg)
    {
        this.item = item;
        this.arg = arg;
    }

    @Override
    public void onClick(View v)
    {
        item.AddSibling(arg);
    }
}
