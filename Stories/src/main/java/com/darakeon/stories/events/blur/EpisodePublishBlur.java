package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Episode;

public class EpisodePublishBlur extends GenericEditTextBlur
{
    private Episode episode;

    public EpisodePublishBlur(Episode episode)
    {
        this.episode = episode;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        episode.Publish = editText.getText().toString();
    }
}
