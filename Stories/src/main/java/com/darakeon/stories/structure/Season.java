package com.darakeon.stories.structure;

import android.content.Context;

import com.darakeon.stories.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Season
{
    public static ArrayList<String> getList(Context context)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getFilesDir();
        File[] filesList = dir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                list.add(context.getString(R.string.season) + file.getName());
            }
        }

        new Episode(context, "A", "01");

        list.add(dir.getAbsolutePath());

        return list;
    }
}
