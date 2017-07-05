package com.darakeon.stories.domain;

import com.darakeon.stories.events.click.AddNewClick.IChildWithSibs;
import com.darakeon.stories.types.ParagraphType;
import com.darakeon.stories.types.TalkStyle;
import com.darakeon.stories.types.TellerStyle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class Piece implements IChildWithSibs
{
    private Piece(Node pieceNode, Paragraph paragraph)
    {
        node = pieceNode;
        this.paragraph = paragraph;

        paragraphType = paragraph.GetType();

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
    private Paragraph paragraph;
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

    public static Piece New(Node pieceNode, Paragraph paragraph)
    {
        String name = pieceNode.getNodeName();

        switch (paragraph.GetType())
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

        return new Piece(pieceNode, paragraph);
    }

    public boolean SaveIfNotEmpty()
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

    public void AddSibling(Object... args)
    {
        AddSibling();
    }

    public void AddSibling()
    {
        ArrayList<Piece> pieceList = paragraph.GetPieceList();
        int nextIndex = pieceList.indexOf(this) + 1;

        Document document = node.getOwnerDocument();
        Element newPieceNode = document.createElement("default");

        Node paragraphNode = node.getParentNode();
        Node nextSibling = node.getNextSibling();
        paragraphNode.insertBefore(newPieceNode, nextSibling);

        pieceList.add(nextIndex, new Piece(newPieceNode, paragraph));
    }
}
