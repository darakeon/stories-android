package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.darakeon.stories.R;
import com.darakeon.stories.domain.Piece;
import com.darakeon.stories.events.blur.PieceTextBlur;
import com.darakeon.stories.events.blur.PieceTypeBlur;
import com.darakeon.stories.events.click.AddNewListener;

public class PieceView extends LinearLayout
{
    public PieceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        type = (AutoComplete) findViewById(R.id.scene_edit_piece_list_type);
        text = (EditText) findViewById(R.id.scene_edit_piece_list_text);
        plus = (ImageView) findViewById(R.id.plus_piece);
    }

    private AutoComplete type;
    private EditText text;

    private ImageView plus;
    private Piece piece;

    public void SetContent(Piece piece)
    {
        this.piece = piece;
        setType();
        setText();
        setClick();
    }

    private void setType()
    {
        type.setText(piece.GetStyle());
        type.setOnFocusChangeListener(new PieceTypeBlur(piece));
        type.SetAutoCompleteList(super.getContext(), piece.GetAllowedStyles());
    }

    private void setText()
    {
        text.setText(piece.Text);
        text.setOnFocusChangeListener(new PieceTextBlur(piece));
    }

    private void setClick()
    {
        plus.setOnClickListener(new AddNewListener(piece));
    }

}
