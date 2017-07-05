package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.darakeon.stories.R;
import com.darakeon.stories.domain.Piece;
import com.darakeon.stories.events.blur.PieceTextBlur;
import com.darakeon.stories.events.blur.PieceTypeBlur;

public class PieceView extends LinearLayout
{
    public PieceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        Type = (AutoComplete) findViewById(R.id.scene_edit_piece_list_type);
        Text = (EditText) findViewById(R.id.scene_edit_piece_list_text);
    }

    AutoComplete Type;
    EditText Text;

    public void SetContent(Piece piece)
    {
        setType(piece);
        setText(piece);
    }

    private void setType(Piece piece)
    {
        Type.setText(piece.GetStyle());
        Type.setOnFocusChangeListener(new PieceTypeBlur(piece));
        Type.SetAutoCompleteList(super.getContext(), piece.GetAllowedStyles());
    }

    private void setText(Piece piece)
    {
        Text.setText(piece.Text);
        Text.setOnFocusChangeListener(new PieceTextBlur(piece));
    }

}
