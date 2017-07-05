package com.darakeon.stories.factories;

import java.io.File;

public interface IContext
{
    File GetMainDirectory();

    IErrorHandler GetErrorHandler();

    void ShowToast(int resourceId);

    void ShowFile(File file);
}
