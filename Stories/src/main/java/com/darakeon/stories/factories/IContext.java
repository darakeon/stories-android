package com.darakeon.stories.factories;

import java.io.File;

public interface IContext
{
    File getMainDirectory();

    IErrorHandler GetErrorHandler();

    void ShowToast(int resourceId);
}
