package com.darakeon.stories.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

    public void SetContent(Paragraph paragraph, ArrayList<String> characterList)
    {
        setType(paragraph);
        setCharacter(paragraph, characterList);
        setPieceListView(paragraph);
    }

    TextView type;
    AutoComplete character;
    ListView pieceListView;

    private void setType(Paragraph paragraph)
    {
        type = (TextView) findViewById(R.id.type);
        type.setText(paragraph.GetStringType());
    }

    private void setCharacter(Paragraph paragraph, ArrayList<String> characterList)
    {
        character = (AutoComplete) findViewById(R.id.character);

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

    private void setPieceListView(Paragraph paragraph)
    {
        ArrayList<Piece> pieceList = paragraph.GetPieceList();
        PieceAdapter adapter = new PieceAdapter((Activity)getContext(), pieceList);

        pieceListView = (ListView) findViewById(R.id.piece_list);
        pieceListView.setAdapter(adapter);
    }
}
