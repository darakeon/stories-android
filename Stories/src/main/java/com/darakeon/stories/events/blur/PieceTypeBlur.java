package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Piece;
import com.darakeon.stories.types.ParagraphType;

public class PieceTypeBlur extends GenericEditTextBlur
{
    private Piece piece;

    public PieceTypeBlur(Piece piece)
    {
        this.piece = piece;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        String text = editText.getText().toString();
        piece.SetStyle(text);
    }
}
