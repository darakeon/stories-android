package com.darakeon.stories.errorhandler;

import com.darakeon.stories.factories.IContext;
import com.darakeon.stories.factories.IErrorHandler;

public class ErrorHandler implements IErrorHandler
{
    private IContext context;

    public ErrorHandler(IContext context)
    {
        this.context = context;
    }

    public void Write(int resourceId, Exception e)
    {
        context.ShowToast(resourceId);
    }
}
