package com.darakeon.stories.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Scene
{
    public Scene(String sceneLetter, Element sceneNode)
    {
        Letter = sceneLetter;
        node = sceneNode;
        paragraphList = new ArrayList<>();
    }

    public String Letter;
    private Element node;

    private ArrayList<Paragraph> paragraphList;

    public void SetParagraphList()
    {
        NodeList children = node.getChildNodes();

        for (int e = 0; e < children.getLength(); e++)
        {
            Node child = children.item(e);
            Paragraph paragraph = Paragraph.New(child);

            if (paragraph != null)
                paragraphList.add(paragraph);
        }
    }

    public ArrayList<Paragraph> GetParagraphList()
    {
        return paragraphList;
    }

    public void Save()
    {
        for (Paragraph paragraph: paragraphList)
        {
            paragraph.Save();
        }
    }
}
