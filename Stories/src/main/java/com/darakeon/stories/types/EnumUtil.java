package com.darakeon.stories.types;

import java.util.ArrayList;

public class EnumUtil
{
    public static ArrayList<String> GetAllowedTypes(Object[] enumList)
    {
        ArrayList<String> stringList = new ArrayList<>();

        for (Object item : enumList)
        {
            stringList.add(item.toString().toLowerCase());
        }

        return stringList;
    }
}

