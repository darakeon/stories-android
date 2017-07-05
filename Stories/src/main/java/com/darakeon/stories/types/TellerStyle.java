package com.darakeon.stories.types;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
public enum TellerStyle
{
    DEFAULT,
    FIRST,
    DIVISION,
    LETTER,
    TRIGGER;

    public static ArrayList<String> GetAllowedTypes()
    {
        TellerStyle[] enumList = TellerStyle.values();
        return EnumUtil.GetAllowedTypes(enumList);
    }
}

