package com.darakeon.stories.types;

import java.util.ArrayList;

public enum ParagraphType
{
    TELLER,
    TALK;

    public static ArrayList<String> GetAllowedTypes()
    {
        ParagraphType[] enumList = ParagraphType.values();
        return EnumUtil.GetAllowedTypes(enumList);
    }

}
