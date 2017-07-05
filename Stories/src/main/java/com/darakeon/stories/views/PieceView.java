package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
        type = (AutoComplete) findViewById(R.id.scene_edit_piece_list_type);
        text = (EditText) findViewById(R.id.scene_edit_piece_list_text);
        plus = (ImageView) findViewById(R.id.plus_piece);

        plus.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                piece.AddSibling();
            }
        });
    }

    private AutoComplete type;
    private EditText text;

    private ImageView plus;
    private Piece piece;

    public void SetContent(Piece piece)
    {
        this.piece = piece;
        setType(piece);
        setText(piece);
    }

    private void setType(Piece piece)
    {
        type.setText(piece.GetStyle());
        type.setOnFocusChangeListener(new PieceTypeBlur(piece));
        type.SetAutoCompleteList(super.getContext(), piece.GetAllowedStyles());
    }

    private void setText(Piece piece)
    {
        text.setText(piece.Text);
        text.setOnFocusChangeListener(new PieceTextBlur(piece));
    }

}
