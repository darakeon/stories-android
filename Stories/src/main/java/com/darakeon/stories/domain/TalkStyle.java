package com.darakeon.stories.domain;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
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