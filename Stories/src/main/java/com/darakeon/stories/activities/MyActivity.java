package com.darakeon.stories.activities;

import android.app.Activity;
import android.widget.Toast;

import com.darakeon.stories.errorhandler.ErrorHandler;

public class MyActivity extends Activity
{
    protected MyActivity()
    {
        ErrorHandler = new ErrorHandler(this);
    }

    public void Refresh()
    {
        finish();
        startActivity(getIntent());
    }

    public void ShowToast(int resourceId)
    {
        Toast t = Toast.makeText(this, getString(resourceId), Toast.LENGTH_LONG);
        t.show();
    }

    public ErrorHandler ErrorHandler;

}
