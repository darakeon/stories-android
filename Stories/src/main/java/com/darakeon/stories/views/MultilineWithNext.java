package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * Created by Keon on 29/02/2016.
 */
public class MultilineWithNext extends EditText
{
    public MultilineWithNext(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        MoveNextHelper.ChangeAttr(outAttrs);
        return connection;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        return MoveNextHelper.KeyNext(keyCode, this);
    }
}

