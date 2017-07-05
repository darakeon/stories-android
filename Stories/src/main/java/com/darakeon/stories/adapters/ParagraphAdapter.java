package com.darakeon.stories.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.domain.Paragraph;
import com.darakeon.stories.domain.Piece;
import com.darakeon.stories.events.blur.ParagraphCharacterBlur;

import java.util.ArrayList;

public class ParagraphAdapter extends BaseAdapter
{
    EditEpisodeActivity activity;
    ArrayList<Paragraph> paragraphList;

    private static LayoutInflater inflater = null;

    public ParagraphAdapter(EditEpisodeActivity activity, ArrayList<Paragraph> paragraphList)
    {
        this.paragraphList = paragraphList;
        this.activity = activity;

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View rowView = inflater.inflate(R.layout.edit_episode_scene_edit, null);
        Paragraph paragraph = paragraphList.get(position);

        setType(rowView, paragraph);
        setCharacter(rowView, paragraph);
        setPieceList(rowView, paragraph);

        return rowView;
    }

    private void setType(View rowView, Paragraph paragraph)
    {
        TextView type = (TextView) rowView.findViewById(R.id.type);
        type.setText(paragraph.GetStringType());
    }

    private void setCharacter(View rowView, Paragraph paragraph)
    {
        AutoCompleteTextView character = (AutoCompleteTextView) rowView.findViewById(R.id.character);

        if (paragraph.Character == null)
        {
            character.setVisibility(View.INVISIBLE);
        }
        else
        {
            character.setText(paragraph.Character);
            character.setOnFocusChangeListener(new ParagraphCharacterBlur(paragraph));
        }
    }

    private void setPieceList(View rowView, Paragraph paragraph)
    {
        ArrayList<Piece> pieceList = paragraph.GetPieceList();
        PieceAdapter adapter = new PieceAdapter(activity, paragraph.GetType(), pieceList);

        ListView view = (ListView) rowView.findViewById(R.id.piece_list);
        view.setAdapter(adapter);
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
                item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                item.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                itemsHeight += item.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = paddingHeight + itemsHeight + dividerHeight;
        view.setLayoutParams(params);
        view.requestLayout();

        /*
        int height = 0;

        for (int c = 0; c < view.getChildCount(); c++)
        {
            View child = view.getChildAt(c);
            height += child.getHeight();
        }

        if (height != 0)
        {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
        */
    }


}

