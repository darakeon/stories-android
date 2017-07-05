package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dara on 18/02/2016.
 */
public class SceneButton extends TextView
{
    public SceneButton(Context context, AttributeSet attributeSet)
    {
        super(context,  attributeSet);
        selected = false;
    }

    private Boolean selected;

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
