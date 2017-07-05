package com.darakeon.stories.domain;

import com.darakeon.stories.types.ParagraphType;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by Keon on 06/02/2016.
 */
public class Paragraph
{
    private Paragraph(Node paragraphNode)
    {
        node = paragraphNode;

        Type = ParagraphType.valueOf(node.getNodeName().toUpperCase());

        if (Type == ParagraphType.TALK)
        {
            Character = node.getAttributes().getNamedItem("character").getNodeValue();
        }

        SetPieces(node);
    }

    private static ArrayList<String> allowedTypes = ParagraphType.GetAllowedTypes();

    public Node node;
    public ParagraphType Type;
    public String Character;

    private ArrayList<Piece> pieceList;

    private void SetPieces(Node node)
    {
        NodeList children = node.getChildNodes();
        pieceList = new ArrayList<>();

        for (int e = 0; e < children.getLength(); e++)
        {
            Node child = children.item(e);
            Piece piece = Piece.New(child, Type);

            if (piece != null)
                pieceList.add(piece);
        }
    }

    public ArrayList<Piece> GetPieceList()
    {
        return pieceList;
    }



    public static Paragraph New(Node paragraphNode)
    {
        String name = paragraphNode.getNodeName();

        if (!allowedTypes.contains(name))
        {
            return null;
        }

        Paragraph paragraph = new Paragraph(paragraphNode);

        return paragraph;
    }

    public void Save()
    {
        Node characterNode = node.getAttributes().getNamedItem("character");

        if (Type == ParagraphType.TALK && characterNode != null)
        {
            characterNode.setNodeValue(Character);
        }

        for (Piece piece: pieceList)
        {
            piece.Save();
        }
    }
}
