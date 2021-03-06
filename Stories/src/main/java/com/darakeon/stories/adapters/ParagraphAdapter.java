package com.darakeon.stories.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.domain.Paragraph;
import com.darakeon.stories.views.ParagraphView;

import java.util.ArrayList;

public class ParagraphAdapter extends BaseAdapter
{
    private ArrayList<Paragraph> paragraphList;
    private ParagraphView[] viewList;
    private ArrayList<String> characterList;

    private static LayoutInflater inflater = null;

    public ParagraphAdapter(ArrayList<Paragraph> paragraphList, LayoutInflater inflater)
    {
        this.paragraphList = paragraphList;

        characterList = new ArrayList<>();

        for (Paragraph paragraph : paragraphList)
        {
            if (!characterList.contains(paragraph.Character) && paragraph.Character != null)
            {
                characterList.add(paragraph.Character);
            }
        }

        this.inflater = inflater;
        viewList = new ParagraphView[paragraphList.size()];
    }

    @Override
    public int getCount()
    {
        return paragraphList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null || true)
            convertView = inflater.inflate(R.layout.edit_episode_scene_edit, parent, false);

        ParagraphView view = (ParagraphView)convertView;
        Paragraph paragraph = paragraphList.get(position);
        view.SetContent(paragraph, characterList, inflater);

        return view;
    }


    public void AdjustAllHeight(ListView paragraphList)
    {
        for (int c = 0; c < paragraphList.getChildCount(); c++)
        {
            View paragraphView = paragraphList.getChildAt(c);
            ListView pieceList = (ListView)paragraphView.findViewById(R.id.piece_list);
            adjustHeight(pieceList);
        }
    }

    private void adjustHeight(ListView view)
    {
        ListAdapter adapter = view.getAdapter();

        int paddingHeight = view.getPaddingTop() + view.getPaddingBottom();
        int dividerHeight = view.getDividerHeight() * (adapter.getCount() - 1);

        int itemsHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < adapter.getCount(); i++)
        {
            View item = adapter.getView(i, null, view);

            if (item != null)
            {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                item.setLayoutParams(layoutParams);
                item.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                itemsHeight += item.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = paddingHeight + itemsHeight + dividerHeight;
        view.setLayoutParams(params);
        view.requestLayout();
    }

}



