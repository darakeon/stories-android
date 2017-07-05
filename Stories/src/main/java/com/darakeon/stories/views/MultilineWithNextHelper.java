package com.darakeon.stories.views;

import android.view.inputmethod.EditorInfo;

public class MultilineWithNextHelper
{
    public static void changeAttr(EditorInfo outAttrs)
    {
        int imeActions = outAttrs.imeOptions & EditorInfo.IME_MASK_ACTION;

        if ((imeActions & EditorInfo.IME_ACTION_NEXT) != 0)
        {
            // clear the existing action
            outAttrs.imeOptions ^= imeActions;
            // set the new action
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_NEXT;
        }

        if ((outAttrs.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0)
        {
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
    }

}
