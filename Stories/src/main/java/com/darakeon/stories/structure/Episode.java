package com.darakeon.stories.structure;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Episode
{
    public static ArrayList<String> getList(Context context, String season)
    {
        ArrayList<String> list = new ArrayList<>();


        File dir = context.getFilesDir();
        File seasonDir = new File(dir.getAbsolutePath(), season);
        File[] filesList = seasonDir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                list.add(file.getName());
            }
        }

        return list;
    }
}
