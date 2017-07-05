package com.darakeon.stories.factories;

public interface IErrorHandler
{
    void ShowMessage(int resourceId);
    void WriteLogAndShowMessage(Exception exception, int resourceId);
}
