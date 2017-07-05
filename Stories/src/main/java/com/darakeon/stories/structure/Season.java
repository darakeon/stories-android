package com.darakeon.stories.structure;

import android.content.Context;

import com.darakeon.stories.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Season
{
    public static ArrayList<String> getList(Context context)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getExternalFilesDir("");
        File[] filesList = dir.listFiles();

        Arrays.sort(filesList);

        for (File file : filesList) {
            if (file.isDirectory() && file.getName().startsWith("_")) {
                list.add(context.getString(R.string.season) + file.getName().substring(1));
            }
        }

        return list;
    }
}
