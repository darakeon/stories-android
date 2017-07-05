package com.darakeon.stories.types;

import java.util.ArrayList;

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

