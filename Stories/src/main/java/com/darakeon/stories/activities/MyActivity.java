package com.darakeon.stories.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.darakeon.stories.errorhandler.ErrorHandler;
import com.darakeon.stories.factories.IContext;
import com.darakeon.stories.factories.IErrorHandler;

import java.io.File;

public class MyActivity extends Activity implements IContext
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

    public IErrorHandler ErrorHandler;



    @Override
    public IErrorHandler GetErrorHandler()
    {
        return ErrorHandler;
    }

    @Override
    public File GetMainDirectory()
    {
        return getExternalFilesDir("");
    }

    public void ShowFile(File file)
    {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(intent);
    }

}
