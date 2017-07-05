package com.darakeon.stories.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.EditEpisodeActivity;
import com.darakeon.stories.domain.Piece;
import com.darakeon.stories.views.PieceView;

import java.util.ArrayList;

public class PieceAdapter extends BaseAdapter
{
    private ArrayList<Piece> pieceList;
    private PieceView[] viewList;

    private static LayoutInflater inflater = null;

    public PieceAdapter(EditEpisodeActivity activity, ArrayList<Piece> pieceList)
    {
        this.pieceList = pieceList;

        inflater = activity.getLayoutInflater();
        viewList = new PieceView[pieceList.size()];
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        PieceView holder = viewList[position];

        if (holder == null)
            holder = (PieceView) inflater.inflate(R.layout.edit_episode_scene_edit_piece_list, null);

        Piece piece = pieceList.get(position);
        holder.SetContent(piece);

        return holder;
    }

}


