package com.darakeon.stories.errorhandler;

import android.support.annotation.NonNull;

import com.darakeon.stories.R;
import com.darakeon.stories.factories.IContext;
import com.darakeon.stories.factories.IErrorHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorHandler implements IErrorHandler
{
    private IContext context;

    public ErrorHandler(IContext context)
    {
        this.context = context;
    }

    public void ShowMessage(int resourceId)
    {
        context.ShowToast(resourceId);
    }

    public void WriteLogAndShowMessage(Exception exception, int resourceId)
    {
        ShowMessage(resourceId);

        File file = new File(context.GetMainDirectory(), "keon.log");

        if (!file.exists())
        {
            boolean fileCreated = false;

            try
            {
                fileCreated = file.createNewFile();
            } catch (IOException ignored) { }

            if (fileCreated)
            {
                showFile(file);
            }
            else
            {
                context.ShowToast(R.string.ERROR_create_log_file);
                return;
            }
        }

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile())) )
        {
            writer.append(exception.toString());
            writer.append("\r\n");
            writer.flush();
        }
        catch (IOException e)
        {
            context.ShowToast(R.string.ERROR_save_log_file_not_found);
            return;
        }

        showFile(file);
        exception.printStackTrace();
    }

    @NonNull
    private File showFile(File file)
    {
        file = new File(file.getAbsolutePath());
        context.ShowFile(file);
        return file;
    }

}
