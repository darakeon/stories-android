package com.darakeon.stories.errorhandler;

import com.darakeon.stories.activities.MyActivity;

public class ErrorHandler
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
