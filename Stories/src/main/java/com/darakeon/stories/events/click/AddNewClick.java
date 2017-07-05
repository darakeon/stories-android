package com.darakeon.stories.events.click;

import android.view.View;
import android.view.ViewParent;
import android.widget.ListAdapter;
import android.widget.ListView;

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
        refresh(v);
    }

    private void refresh(View view)
    {
        if (view == null)
            return;

        if (view instanceof ListView)
        {
            ListView listView = (ListView) view;
            ListAdapter adapter = listView.getAdapter();
            listView.setAdapter(adapter);
            listView.requestLayout();
            return;
        }

        ViewParent parent = view.getParent();

        if (parent instanceof View)
        {
            refresh((View) parent);
        }
    }

    public interface IChildWithSibs
    {
        void AddSibling(Object... arg);
    }
}
