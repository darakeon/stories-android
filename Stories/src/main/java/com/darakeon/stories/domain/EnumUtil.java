package com.darakeon.stories.domain;

import java.util.ArrayList;

public class EnumUtil
{
    public static ArrayList<String> GetAllowedTypes(Object[] enumList)
    {
        ArrayList<String> stringList = new ArrayList<>();

        for (int p = 0; p < enumList.length; p++)
        {
            stringList.add(enumList[p].toString().toLowerCase());
        }

        return stringList;
    }
}
