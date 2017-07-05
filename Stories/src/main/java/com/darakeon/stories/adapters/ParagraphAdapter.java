package com.darakeon.stories.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.domain.Paragraph;
import com.darakeon.stories.domain.Piece;

import java.util.ArrayList;

public class ParagraphAdapter extends BaseAdapter
{
    EditEpisodeActivity activity;
    ArrayList<Paragraph> paragraphList;

    private static LayoutInflater inflater=null;

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
        EditText type = (EditText) rowView.findViewById(R.id.type);
        type.setText(paragraph.Type.toString().toLowerCase());
    }

    private void setCharacter(View rowView, Paragraph paragraph)
    {
        EditText character = (EditText) rowView.findViewById(R.id.character);

        if (paragraph.Character == null)
        {
            character.setVisibility(View.INVISIBLE);
        }
        else
        {
            character.setText(paragraph.Character);
        }
    }

    private void setPieceList(View rowView, Paragraph paragraph)
    {
        ArrayList<Piece> pieceList = paragraph.GetPieceList();
        PieceAdapter adapter = new PieceAdapter(activity, paragraph.Type, pieceList);

        ListView view = (ListView) rowView.findViewById(R.id.piece_list);
        view.setAdapter(adapter);
    }


}

