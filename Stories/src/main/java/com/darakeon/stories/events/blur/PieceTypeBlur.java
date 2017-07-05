package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Piece;

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
        String text = editText.getText().toString().toUpperCase();
        boolean isAllowed = piece.SetStyle(text);

        if (!isAllowed)
        {
            editText.setText(piece.GetStyle());
        }
    }
}
