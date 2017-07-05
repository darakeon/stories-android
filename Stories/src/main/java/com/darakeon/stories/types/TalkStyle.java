package com.darakeon.stories.types;

import java.util.ArrayList;

public enum TalkStyle
{
    ARRIVE,
    DEFAULT,
    FRIZZ,
    GHOST,
    HUSHED,
    IRONY,
    MUTE,
    OUT,
    READ,
    SCREAMED,
    SPECIAL,
    TELLER,
    THOUGHT,
    TRANSLATE;

    public static ArrayList<String> GetAllowedTypes()
    {
        TalkStyle[] enumList = TalkStyle.values();
        return EnumUtil.GetAllowedTypes(enumList);
    }

}