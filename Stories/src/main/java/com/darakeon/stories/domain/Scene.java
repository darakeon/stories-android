package com.darakeon.stories.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Scene
{
    public Scene(String sceneLetter, Element sceneNode, long fileSize)
    {
        letter = sceneLetter;
        node = sceneNode;
        this.fileSize = fileSize;

        SetParagraphList();
    }

    private String letter;
    private Element node;
    private long fileSize;

    private ArrayList<Paragraph> paragraphList;

    public String GetLetter()
    {
        return letter;
    }

    public Element GetNode()
    {
        return node;
    }

    public long GetFileSize()
    {
        return fileSize;
    }

    private void SetParagraphList()
    {
        NodeList children = node.getChildNodes();
        paragraphList = new ArrayList<>();

        for (int e = 0; e < children.getLength(); e++)
        {
            Node child = children.item(e);
            Paragraph paragraph = Paragraph.New(child, this);

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
        for (int p = 0; p < paragraphList.size(); p++)
        {
            Paragraph paragraph = paragraphList.get(p);
            paragraph.Save();
        }

        fileSize = 0;
    }

    public void SetFileSize(long fileSize)
    {
        if (this.fileSize == 0)
            this.fileSize = fileSize;
    }



    public void SaveCleaning()
    {
        for (int p = 0; p < paragraphList.size(); p++)
        {
            Paragraph paragraph = paragraphList.get(p);

            boolean storyUniqueParagraph = paragraphList.size() == 1;

            //Story has just one paragraph
            if (storyUniqueParagraph)
            {
                paragraph.Save();
            }
            else
            {
                boolean saved = paragraph.SaveIfNotEmpty();

                if (!saved)
                {
                    paragraphList.remove(paragraph);
                    p--;
                }
            }
        }

    }

    public int CountNotEmptyParagraphs()
    {
        int count = 0;

        for (Paragraph paragraph : paragraphList)
        {
            if (!paragraph.IsEmpty())
            {
                count++;
            }
        }

        return count;
    }


}
