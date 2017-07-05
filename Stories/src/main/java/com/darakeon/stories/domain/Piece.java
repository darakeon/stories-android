package com.darakeon.stories.domain;

import org.w3c.dom.Node;

/**
 * Created by Keon on 06/02/2016.
 */
public class Piece
{
    public Piece(Node pieceNode, ParagraphType paragraphType)
    {
        switch (paragraphType)
        {
            case Talk:
                TalkStyle = Enum.valueOf(TalkStyle.class, pieceNode.getNodeName());
                break;

            case Teller:
                TellerStyle = Enum.valueOf(TellerStyle.class, pieceNode.getNodeName());
                break;
        }

        Text = pieceNode.getTextContent();
    }

    public TellerStyle TellerStyle;
    public TalkStyle TalkStyle;

    public String GetStyle()
    {
        if (TalkStyle != null)
            return TalkStyle.toString();

        if (TellerStyle != null)
            return TellerStyle.toString();

        return null;
    }

    public String Text;
}
