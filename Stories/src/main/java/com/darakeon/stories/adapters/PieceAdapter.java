package com.darakeon.stories.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.domain.ParagraphType;
import com.darakeon.stories.domain.Piece;

import java.util.ArrayList;

public class PieceAdapter extends BaseAdapter
{
    EditEpisodeActivity activity;
    private ParagraphType type;
    ArrayList<Piece> pieceList;

    private static LayoutInflater inflater=null;

    public PieceAdapter(EditEpisodeActivity activity, ParagraphType type, ArrayList<Piece> pieceList)
    {
        this.activity = activity;
        this.type = type;
        this.pieceList = pieceList;

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return pieceList.size();
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

    public class PieceHolder
    {
        EditText type;
        EditText text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        PieceHolder holder = new PieceHolder();
        View rowView = inflater.inflate(R.layout.edit_episode_scene_edit_piece, null);

        Piece piece = pieceList.get(position);

        holder.type = (EditText) rowView.findViewById(R.id.scene_edit_piece_list_type);

        switch (type)
        {
            case TALK:
                holder.type.setText(piece.GetStyle());
                break;

            case TELLER:
                holder.type.setText(piece.GetStyle());
                break;
        }

        holder.text = (EditText) rowView.findViewById(R.id.scene_edit_piece_list_text);
        holder.text.setText(piece.Text);

        return rowView;
    }
}
