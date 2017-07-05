package com.darakeon.stories.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class Episode
{
    public Episode(Element episodeSummaryNode)
    {
        this.episodeSummaryNode = episodeSummaryNode;
    }

    public Episode(Element episodeSummaryNode, Map<String, Element> sceneNodes)
    {
        this(episodeSummaryNode);
        this.sceneNodes = sceneNodes;
        this.sceneList = new ArrayList<>();
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
        for (String sceneLetter : sceneNodes.keySet())
        {
            Scene scene = new Scene(sceneLetter, sceneNodes.get(sceneLetter));
            scene.SetParagraphList();
            sceneList.add(scene);
        }
    }

    private Element episodeSummaryNode;
    private Map<String, Element> sceneNodes;

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

    public String[] GetSceneLetterList()
    {
        String[] sceneLetters = new String[sceneList.size()];

        for (int i = 0; i < sceneList.size(); i++)
        {
            Scene scene = sceneList.get(i);
            sceneLetters[i] = scene.Letter;
        }

        return sceneLetters;
    }

    public Scene GetScene(String letter)
    {
        for (Scene scene : sceneList)
        {
            if (scene.Letter == letter)
                return scene;
        }

        return null;
    }

}

