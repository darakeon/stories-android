package com.darakeon.stories.structure;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Episode
{
    public Episode(Element episodeSummaryNode)
    {
        this.episodeSummaryNode = episodeSummaryNode;
    }

    public Episode(Element episodeSummaryNode, Element[] sceneNodes)
    {
        this(episodeSummaryNode);
        this.sceneNodes = sceneNodes;
    }

    public void SetMainInfo() throws ParseException
    {
        Node portugueseNode = episodeSummaryNode.getElementsByTagName("portuguese").item(0);

        title = portugueseNode.getAttributes().getNamedItem("title").getTextContent();

        String textPublish = episodeSummaryNode.getAttributes().getNamedItem("publish").getTextContent();

        publish = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA_FRENCH);
        publish.setTime(sdf.parse(textPublish));
    }

    public void SetContent()
    {
        for (Element sceneNode : sceneNodes)
        {
            Scene scene = new Scene(sceneNode);
            scene.GetParagraphs();
            sceneList.add(scene);
        }
    }

    private Element episodeSummaryNode;
    private Element[] sceneNodes;

    private String title;
    private Calendar publish;

    private ArrayList<Scene> sceneList;

    public String getTitle()
    {
        return title;
    }

    public Calendar getPublish()
    {
        return publish;
    }

    public ArrayList<Scene> getSceneList()
    {
        return sceneList;
    }


}

