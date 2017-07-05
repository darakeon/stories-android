package com.darakeon.stories.events.blur;

import android.widget.EditText;

import com.darakeon.stories.domain.Paragraph;
import com.darakeon.stories.types.ParagraphType;

public class ParagraphTypeBlur extends GenericEditTextBlur
{
    private Paragraph paragraph;

    public ParagraphTypeBlur(Paragraph paragraph)
    {
        this.paragraph = paragraph;
    }

    @Override
    protected void OnBlur(EditText editText)
    {
        String text = editText.getText().toString().toUpperCase();
        paragraph.Type = ParagraphType.valueOf(text);
    }
}
