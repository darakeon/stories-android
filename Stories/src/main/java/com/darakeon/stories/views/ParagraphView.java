package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.darakeon.stories.R;
import com.darakeon.stories.adapters.PieceAdapter;
import com.darakeon.stories.domain.Paragraph;
import com.darakeon.stories.domain.Piece;
import com.darakeon.stories.events.blur.ParagraphCharacterBlur;

import java.util.ArrayList;

public class ParagraphView extends LinearLayout
{
    public ParagraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        type = (ParagraphImage) findViewById(R.id.type);
        character = (AutoComplete) findViewById(R.id.character);
    }

    public void SetContent(Paragraph paragraph, ArrayList<String> characterList, LayoutInflater inflater)
    {
        setType(paragraph);
        setCharacter(paragraph, characterList);
        setPieceListView(paragraph, inflater);
    }

    ParagraphImage type;
    AutoComplete character;
    ListView pieceListView;

    private void setType(Paragraph paragraph)
    {
        type.setImage(paragraph.GetType());
    }

    private void setCharacter(Paragraph paragraph, ArrayList<String> characterList)
    {
        if (paragraph.Character == null)
        {
            character.setVisibility(View.INVISIBLE);
        }
        else
        {
            character.setText(paragraph.Character);
            character.setOnFocusChangeListener(new ParagraphCharacterBlur(paragraph));

            character.SetAutoCompleteList(getContext(), characterList);
        }
    }

    private void setPieceListView(Paragraph paragraph, LayoutInflater inflater)
    {
        ArrayList<Piece> pieceList = paragraph.GetPieceList();
        PieceAdapter adapter = new PieceAdapter(pieceList, inflater);

        pieceListView = (ListView) findViewById(R.id.piece_list);
        pieceListView.setAdapter(adapter);
    }
}
