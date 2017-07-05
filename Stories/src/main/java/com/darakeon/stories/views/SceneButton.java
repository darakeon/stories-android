package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SceneButton extends ImageView
{
    public SceneButton(Context context, AttributeSet attributeSet)
    {
        super(context,  attributeSet);
        selected = false;
    }

    private Boolean selected;
    public String Letter;

    public Boolean IsSelected()
    {
        return selected;
    }

    public void Select()
    {
        selected = true;
    }

    public void Deselect()
    {
        selected = false;
    }

}
