package com.darakeon.stories.structure;

import java.util.ArrayList;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Episode
{
    public static ArrayList<String> getList(String season)
    {
        ArrayList<String> list = new ArrayList<>();

        switch (season)
        {
            case "A":
            {
                list.add("01: Gaia e Cronos");
                list.add("02: Areia");
                list.add("03: Mudança");
                break;
            }
            case "B":
            {
                list.add("01: Deixado para trás");
                list.add("02: Nenhum lar é perfeito");
                list.add("03: Julgamento");
                list.add("03: Primeiros Casos");
                break;
            }
        }

        return list;
    }
}
