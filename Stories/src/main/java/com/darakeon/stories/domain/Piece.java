package com.darakeon.stories.domain;

import com.darakeon.stories.types.ParagraphType;
import com.darakeon.stories.types.TalkStyle;
import com.darakeon.stories.types.TellerStyle;

import org.w3c.dom.Document;
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
        node = pieceNode;

        String nodeName = node.getNodeName().toUpperCase();

        switch (paragraphType)
        {
            case TALK:
                talkStyle = TalkStyle.valueOf(nodeName);
                break;

            case TELLER:
                tellerStyle = TellerStyle.valueOf(nodeName);
                break;
        }

        Text = node.getTextContent();
    }

    private static ArrayList<String> allowedTalkTypes = TalkStyle.GetAllowedTypes();
    private static ArrayList<String> allowedTellerTypes = TellerStyle.GetAllowedTypes();



    private Node node;
    private ParagraphType paragraphType;
    private TellerStyle tellerStyle;
    private TalkStyle talkStyle;

    public String GetStyle()
    {
        switch (paragraphType)
        {
            case TALK:
                return talkStyle.toString().toLowerCase();

            case TELLER:
                return tellerStyle.toString().toLowerCase();
        }

        return null;
    }

    public ArrayList<String> GetAllowedStyles()
    {
        switch (paragraphType)
        {
            case TALK:
                return allowedTalkTypes;

            case TELLER:
                return allowedTellerTypes;
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

    public boolean Save()
    {
        boolean isEmpty = removeIfEmpty();

        if (!isEmpty)
        {
            saveType();
            saveText();
        }

        return !isEmpty;
    }

    private boolean removeIfEmpty()
    {
        if (Text.isEmpty())
        {
            Node parent = node.getParentNode();
            parent.removeChild(node);
            return true;
        }

        return false;
    }

    private void saveType()
    {
        Document parent = node.getOwnerDocument();

        String newType = GetStyle();
        String oldType = node.getNodeName();

        if (!newType.equals(oldType))
            parent.renameNode(node, null, newType);
    }

    private void saveText()
    {
        node.setTextContent(Text);
    }
}
