package com.darakeon.stories.domain;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
public class Paragraph
{
    private Paragraph() { }

    private static ArrayList<String> allowedTypes = getAllowedTypes();

    public static ArrayList<String> getAllowedTypes()
    {
        ParagraphType[] paragraphTypeList = ParagraphType.values();
        ArrayList<String> paragraphTypeStringList = new ArrayList<>();

        for (int p = 0; p < paragraphTypeList.length; p++)
        {
            paragraphTypeStringList.add(paragraphTypeList[p].toString());
        }

        return paragraphTypeStringList;
    }

    public static Paragraph New(Node paragraphNode)
    {
        String name = paragraphNode.getNodeName();

        if (!allowedTypes.contains(name))
        {
            return null;
        }

        Paragraph paragraph = new Paragraph();
        paragraph.Type = Enum.valueOf(ParagraphType.class, name);

        if (paragraph.Type == ParagraphType.Talk)
        {
            paragraph.Character = paragraphNode.getAttributes().getNamedItem("character").getNodeValue();
        }

        return paragraph;
    }

    public ParagraphType Type;
    public String Character;
    private ArrayList<Piece> pieceList;

}
