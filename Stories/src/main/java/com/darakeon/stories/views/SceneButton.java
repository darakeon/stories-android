package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darakeon.stories.R;

public class SceneButton extends RelativeLayout
{
    public SceneButton(Context context, AttributeSet attributeSet)
    {
        super(context,  attributeSet);
        selected = false;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        ImageView = (ImageView)findViewById(R.id.scene_button_image);
        TextView = (TextView)findViewById(R.id.scene_button_letter);
    }

    private Boolean selected;
    private String letter;
    public ImageView ImageView;
    public TextView TextView;

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

    public void SetLetter(String sceneLetter)
    {
        letter = sceneLetter;
        TextView.setText(sceneLetter);
    }

    public String GetLetter()
    {
        return letter;
    }
}
