package com.darakeon.stories.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by Dara on 20/02/2016.
 */
public class AutoComplete extends AutoCompleteTextView
{
    public AutoComplete(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void SetAutoCompleteList(Context context, ArrayList<String> items)
    {
        int dropdownItem = android.R.layout.simple_spinner_dropdown_item;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, dropdownItem, items);

        setAdapter(adapter);

        //Characters Number do start auto-complete
        setThreshold(0);
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
