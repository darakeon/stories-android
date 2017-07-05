package com.darakeon.stories.errorhandler;

import com.darakeon.stories.activities.MyActivity;
import com.darakeon.stories.factories.IErrorHandler;

public class ErrorHandler implements IErrorHandler
{
    private MyActivity myActivity;

    public ErrorHandler(MyActivity myActivity)
    {
        this.myActivity = myActivity;
    }

    public void Write(int resourceId, Exception e)
    {
        myActivity.ShowToast(resourceId);
    }
}
