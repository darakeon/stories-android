package com.darakeon.stories.domain;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
public class Piece
{
    private Piece(Node pieceNode, ParagraphType paragraphType)
    {
        switch (paragraphType)
        {
            case TALK:
                talkStyle = Enum.valueOf(TalkStyle.class, pieceNode.getNodeName().toUpperCase());
                break;

            case TELLER:
                tellerStyle = Enum.valueOf(TellerStyle.class, pieceNode.getNodeName().toUpperCase());
                break;
        }

        Text = pieceNode.getTextContent();
    }

    private static ArrayList<String> allowedTalkTypes = TalkStyle.GetAllowedTypes();
    private static ArrayList<String> allowedTellerTypes = TellerStyle.GetAllowedTypes();



    private TellerStyle tellerStyle;
    private TalkStyle talkStyle;

    public String GetStyle()
    {
        if (talkStyle != null)
            return talkStyle.toString();

        if (tellerStyle != null)
            return tellerStyle.toString();

        return null;
    }

    public String Text;

    public static Piece New(Node pieceNode, ParagraphType type)
    {
        String name = pieceNode.getNodeName();

        switch (type)
        {
            case TALK:
                if (!allowedTalkTypes.contains(name))
                    return null;
                break;

            case TELLER:
                if (!allowedTellerTypes.contains(name))
                    return null;
                break;
        }

        Piece piece = new Piece(pieceNode, type);

        return piece;
    }
}
