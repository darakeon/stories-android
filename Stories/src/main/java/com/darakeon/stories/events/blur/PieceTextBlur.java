package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Piece;

public class PieceTextBlur extends GenericEditTextBlur
{
    private Piece piece;

    public PieceTextBlur(Piece piece)
    {
        this.piece = piece;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        piece.Text = editText.getText().toString();
    }
}
