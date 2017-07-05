package com.darakeon.stories.views;

import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;

public class MoveNextHelper
{
    public static void ChangeAttr(EditorInfo outAttrs)
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

                MoveNextHelper helper = new MoveNextHelper(view);
                helper.focus();

                return false;
            default:
                break;
        }

        return true;
    }

    public MoveNextHelper(View view)
    {
        this.view = view;
    }

    private View view;
    private boolean setRelatives = true;
    private int mainNextSibling = 0;
    private int mainNextCousin = 0;



    private void focus()
    {
        if (view == null)
            return;

        if (setRelatives)
        {
            mainNextSibling = getCurrentNextSiblingId();
            mainNextCousin = getCurrentNextCousinId();
            setRelatives = false;
        }

        View parent = (View)view.getParent();
        View nextView = getNext(parent);

        if (nextView == null)
        {
            view = parent;
            focus();
        }
        else if (nextView.getVisibility() == View.INVISIBLE)
        {
            view = nextView;
            setRelatives = true;
            focus();
        }
        else
        {
            nextView.requestFocus();
        }
    }



    @Nullable
    private View getNext(View parent)
    {
        if (parent == null)
        {
            return null;
        }

        View nextView = getNear(parent);

        if (nextView != null)
        {
            if (nextView.getClass() == ListView.class)
            {
                nextView = getFirstGrandChild(nextView);
            }
        }

        return nextView;
    }

    @Nullable
    private View getNear(View parent)
    {
        if (parent.getClass() == ListView.class)
        {
            return getCousin((ListView) parent);
        }

        return getSibling(parent);
    }

    @Nullable
    private View getCousin(ListView parent)
    {
        int originalParentIndex = parent.indexOfChild(view);
        int originalAuntIndex = originalParentIndex + 1;

        if (originalAuntIndex < parent.getChildCount())
        {
            View originalAunt = parent.getChildAt(originalAuntIndex);
            return originalAunt.findViewById(mainNextCousin);
        }

        setRelatives = true;
        return null;
    }

    @Nullable
    private View getSibling(View parent)
    {
        return parent.findViewById(mainNextSibling);
    }

    @Nullable
    private View getFirstGrandChild(View nextView)
    {
        ListView listView = (ListView)nextView;

        if (listView.getChildCount() <= 0)
            return null;

        View child = listView.getChildAt(0);
        int nextChildren = getCurrentNextGrandChildId(listView);

        return child.findViewById(nextChildren);
    }



    private int getCurrentNextSiblingId()
    {
        return view.getNextFocusRightId();
    }

    private int getCurrentNextCousinId()
    {
        return view.getNextFocusLeftId();
    }

    private int getCurrentNextGrandChildId(View view)
    {
        return view.getNextFocusDownId();
    }

}
