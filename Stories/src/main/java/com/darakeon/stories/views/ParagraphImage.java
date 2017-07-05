package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.darakeon.stories.R;
import com.darakeon.stories.types.ParagraphType;

public class ParagraphImage extends ImageView
{
    public ParagraphImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setImage(ParagraphType paragraphType)
    {
        switch (paragraphType)
        {
            case TALK:
                setImageResource(R.drawable.balloon);
                break;
            case TELLER:
                setImageResource(R.drawable.microphone);
                break;
        }
    }

}
