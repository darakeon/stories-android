package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Episode;

public class EpisodeSummaryBlur extends GenericEditTextBlur
{
    private Episode episode;

    public EpisodeSummaryBlur(Episode episode)
    {
        this.episode = episode;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        episode.Summary = editText.getText().toString();
    }
}
