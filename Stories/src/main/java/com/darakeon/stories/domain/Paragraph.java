package com.darakeon.stories.domain;

import com.darakeon.stories.events.click.AddNewClick.IChildWithSibs;
import com.darakeon.stories.types.ParagraphType;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Paragraph implements IChildWithSibs
{
    private Paragraph(Node paragraphNode, Scene scene)
    {
        node = paragraphNode;
        this.scene = scene;

        type = ParagraphType.valueOf(node.getNodeName().toUpperCase());

        if (type == ParagraphType.TALK)
        {
            Character = node.getAttributes().getNamedItem("character").getNodeValue();
        }

        SetPieces(node);
    }

    private static ArrayList<String> allowedTypes = ParagraphType.GetAllowedTypes();

    private Scene scene;

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
            Piece piece = Piece.New(child, this);

            if (piece != null)
                pieceList.add(piece);
        }
    }

    public ParagraphType GetType()
    {
        return type;
    }

    public ArrayList<Piece> GetPieceList()
    {
        return pieceList;
    }



    public static Paragraph New(Node paragraphNode, Scene scene)
    {
        String name = paragraphNode.getNodeName();

        if (!allowedTypes.contains(name))
        {
            return null;
        }

        Paragraph paragraph = new Paragraph(paragraphNode, scene);

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
        for (int p = 0; p < pieceList.size(); p++)
        {
            Piece piece = pieceList.get(p);

            boolean save = piece.Save();

            if (!save)
            {
                pieceList.remove(piece);
                p--;
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

    public void AddSibling(Object... args)
    {
        if (args.length > 0 && args[0].getClass() == ParagraphType.class)
        {
            AddSibling((ParagraphType)args[0]);
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }

    public void AddSibling(ParagraphType type)
    {
        ArrayList<Paragraph> paragraphList = scene.GetParagraphList();
        int nextIndex = paragraphList.indexOf(this) + 1;

        Document document = node.getOwnerDocument();
        Element newParagraphNode = document.createElement(type.toString().toLowerCase());

        Element newPieceNode = document.createElement("default");
        newParagraphNode.appendChild(newPieceNode);

        if (type == ParagraphType.TALK)
        {
            NamedNodeMap attributeList = newParagraphNode.getAttributes();
            Attr characterAttribute = document.createAttribute("character");
            characterAttribute.setValue("-");
            attributeList.setNamedItem(characterAttribute);
        }

        Node sceneNode = node.getParentNode();
        Node nextSibling = node.getNextSibling();
        sceneNode.insertBefore(newParagraphNode, nextSibling);

        paragraphList.add(nextIndex, new Paragraph(newParagraphNode, scene));
    }
}
