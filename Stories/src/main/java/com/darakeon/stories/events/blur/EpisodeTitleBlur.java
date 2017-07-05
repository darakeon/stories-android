package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Episode;

public class EpisodeTitleBlur extends GenericEditTextBlur
{
    private Episode episode;

    public EpisodeTitleBlur(Episode episode)
    {
        this.episode = episode;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        episode.Title = editText.getText().toString();
    }
}
