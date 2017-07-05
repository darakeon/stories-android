package com.darakeon.stories.activities;

import android.app.Activity;

public class MyActivity extends Activity
{
    public void Refresh()
    {
        finish();
        startActivity(getIntent());
    }

}
