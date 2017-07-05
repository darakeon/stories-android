package com.darakeon.stories.domain;

import com.darakeon.stories.types.ParagraphType;
import com.darakeon.stories.types.TalkStyle;
import com.darakeon.stories.types.TellerStyle;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
public class Piece
{
    private Piece(Node pieceNode, ParagraphType paragraphType)
    {
        this.paragraphType = paragraphType;

        switch (paragraphType)
        {
            case TALK:
                talkStyle = TalkStyle.valueOf(pieceNode.getNodeName().toUpperCase());
                break;

            case TELLER:
                tellerStyle = TellerStyle.valueOf(pieceNode.getNodeName().toUpperCase());
                break;
        }

        Text = pieceNode.getTextContent();
    }

    private static ArrayList<String> allowedTalkTypes = TalkStyle.GetAllowedTypes();
    private static ArrayList<String> allowedTellerTypes = TellerStyle.GetAllowedTypes();



    private ParagraphType paragraphType;
    private TellerStyle tellerStyle;
    private TalkStyle talkStyle;

    public String GetStyle()
    {
        switch (paragraphType)
        {
            case TALK:
                return talkStyle.toString();

            case TELLER:
                return tellerStyle.toString();
        }

        return null;
    }

    public void SetStyle(String style)
    {
        switch (paragraphType)
        {
            case TALK:
                talkStyle = TalkStyle.valueOf(style);
                break;

            case TELLER:
                tellerStyle = TellerStyle.valueOf(style);
                break;
        }
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

        return new Piece(pieceNode, type);
    }
}
