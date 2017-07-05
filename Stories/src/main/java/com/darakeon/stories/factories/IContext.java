package com.darakeon.stories.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public interface IContext
{
    File GetMainDirectory();


    IErrorHandler GetErrorHandler();

    void ShowToast(int resourceId);


    void ShowFile(File file);
}
