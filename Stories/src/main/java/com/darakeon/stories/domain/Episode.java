package com.darakeon.stories.domain;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Episode
{
    public Episode(Element episodeSummaryNode)
    {
        this.episodeSummaryNode = episodeSummaryNode;
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

    private Element episodeSummaryNode;

    private String title;
    private Calendar publish;

    public String getTitle()
    {
        return title;
    }

    public Calendar getPublish()
    {
        return publish;
    }

}

