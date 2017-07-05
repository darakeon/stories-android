package com.darakeon.stories.events.blur;

import android.view.View;
import android.widget.EditText;

public abstract class GenericEditTextBlur implements View.OnFocusChangeListener
{
    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus)
            return;

        OnBlur(((EditText) view));
    }

    protected abstract void OnBlur(EditText editText);

}
