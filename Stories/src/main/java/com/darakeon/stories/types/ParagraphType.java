package com.darakeon.stories.types;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
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
