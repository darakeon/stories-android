package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Paragraph;

public class ParagraphCharacterBlur extends GenericEditTextBlur
{
    private Paragraph paragraph;

    public ParagraphCharacterBlur(Paragraph paragraph)
    {
        this.paragraph = paragraph;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        paragraph.Character = editText.getText().toString();
    }
}
