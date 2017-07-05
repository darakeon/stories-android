package com.darakeon.stories.structure;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Episode
{
    public Episode(Element storyNode)
    {
        node = storyNode;
    }

    public void SetMainInfo() throws ParseException
    {
        Node portugueseNode = node.getElementsByTagName("portuguese").item(0);

        title = portugueseNode.getAttributes().getNamedItem("title").getTextContent();

        String textPublish = node.getAttributes().getNamedItem("publish").getTextContent();

        publish = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA_FRENCH);
        publish.setTime(sdf.parse(textPublish));
    }

    private Element node;
    private String title;
    private Calendar publish;

    public String getTitle()
    {
        return title;
    }
    public Calendar getPublish()
    {
        return this.publish;
    }

}
