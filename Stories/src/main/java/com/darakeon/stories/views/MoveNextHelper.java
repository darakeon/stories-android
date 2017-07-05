package com.darakeon.stories.views;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;

public class MoveNextHelper
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



    public static boolean KeyNext(int keyCode, View view)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:

                int focusForwardId = view.getNextFocusForwardId();
                int focusDownId = view.getNextFocusDownId();

                focus(view, focusForwardId, focusDownId);

                return false;
            default:
                break;
        }

        return true;
    }

    private static void focus(View view, int focusForwardId, int focusDownId)
    {
        View parent = (View) view.getParent();

        View sibling = getSibling(view, focusForwardId, focusDownId, parent);

        if (sibling != null)
        {
            sibling.requestFocus();
        }
        else
        {
            focus(parent, focusForwardId, focusDownId);
        }
    }

    private static View getSibling(View view, int focusForwardId, int focusDownId, View parent)
    {
        if (parent.getClass() == ListView.class)
        {
            ListView listView = (ListView) parent;
            int viewIndex = listView.indexOfChild(view);
            int nextIndex = viewIndex + 1;

            if (nextIndex < listView.getChildCount())
            {
                View nextView = listView.getChildAt(nextIndex);
                return nextView.findViewById(focusDownId);
            }

            return null;
        }
        else

        if (view.getClass() == ListView.class)
        {
            int newFocusForwardId = view.getNextFocusForwardId();

            if (newFocusForwardId != -1)
            {
                View listSibling = parent.findViewById(newFocusForwardId);

                if (listSibling != null && listSibling.getVisibility() == View.VISIBLE)
                {
                    return listSibling;
                }
            }
        }

        return parent.findViewById(focusForwardId);
    }

}
