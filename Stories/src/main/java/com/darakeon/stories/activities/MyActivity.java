package com.darakeon.stories.activities;

import android.app.Activity;
import android.widget.Toast;

public class MyActivity extends Activity
{
    public void Refresh()
    {
        finish();
        startActivity(getIntent());
    }

    public void ShowToast(String text)
    {
        Toast t = Toast.makeText(this, text, Toast.LENGTH_LONG);
        t.show();
    }

}
