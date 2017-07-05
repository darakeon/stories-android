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
            refreshListView((ListView) view);
            return;
        }

        ViewParent parent = view.getParent();

        if (parent instanceof View)
        {
            refresh((View) parent);
        }
    }

    private void refreshListView(ListView listView)
    {
        int firstIndexVisible = listView.getFirstVisiblePosition();
        int topOfList = getTopOfList(listView);

        ListAdapter adapter = listView.getAdapter();
        listView.setAdapter(adapter);
        listView.requestLayout();

        listView.setSelectionFromTop(firstIndexVisible, topOfList);
    }

    private int getTopOfList(ListView listView)
    {
        if (listView.getChildCount() == 0)
            return 0;

        View firstChild = listView.getChildAt(0);

        return firstChild.getTop() - listView.getPaddingTop();
    }

    public interface IChildWithSibs
    {
        void AddSibling(Object... arg);
    }
}
