package com.darakeon.stories.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.widget.Toast;

import com.darakeon.stories.errorhandler.ErrorHandler;
import com.darakeon.stories.factories.IContext;
import com.darakeon.stories.factories.IErrorHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
        showToast(getString(resourceId));
    }

    private void showToast(String text)
    {
        Toast t = Toast.makeText(this, text, Toast.LENGTH_LONG);
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
