package com.darakeon.stories.domain;

import com.darakeon.stories.types.ParagraphType;

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

        type = ParagraphType.valueOf(node.getNodeName().toUpperCase());

        if (type == ParagraphType.TALK)
        {
            Character = node.getAttributes().getNamedItem("character").getNodeValue();
        }

        SetPieces(node);
    }

    private static ArrayList<String> allowedTypes = ParagraphType.GetAllowedTypes();

    private Node node;
    private ParagraphType type;
    public String Character;

    private ArrayList<Piece> pieceList;

    private void SetPieces(Node node)
    {
        NodeList children = node.getChildNodes();
        pieceList = new ArrayList<>();

        for (int e = 0; e < children.getLength(); e++)
        {
            Node child = children.item(e);
            Piece piece = Piece.New(child, type);

            if (piece != null)
                pieceList.add(piece);
        }
    }

    public String GetStringType()
    {
        return type.toString().toLowerCase();
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
        savePieceList();

        boolean isEmpty = removeIfEmpty();

        if (!isEmpty)
        {
            saveCharacter();
        }
    }

    private void savePieceList()
    {
        for (Piece piece: pieceList)
        {
            boolean save = piece.Save();

            if (!save)
            {
                pieceList.remove(piece);
            }
        }
    }

    private boolean removeIfEmpty()
    {
        boolean isEmpty = pieceList.size() == 0;

        if (isEmpty)
        {
            Node parent = node.getParentNode();
            parent.removeChild(node);
        }

        return isEmpty;
    }

    private void saveCharacter()
    {
        Node characterNode = node.getAttributes().getNamedItem("character");

        if (type == ParagraphType.TALK && characterNode != null)
        {
            characterNode.setNodeValue(Character);
        }
    }
}
