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
        this.episodeSummaryRoot = episodeSummaryNode;
    }

    public void SetMainInfo() throws ParseException
    {
        Node portugueseNode = episodeSummaryRoot.getElementsByTagName("portuguese").item(0);
        Title = portugueseNode.getAttributes().getNamedItem("title").getTextContent();

        Publish = episodeSummaryRoot.getAttributes().getNamedItem("publish").getTextContent();

        Node summaryNode = episodeSummaryRoot.getElementsByTagName("summary").item(0);
        Summary = summaryNode.getTextContent();
    }

    private Element episodeSummaryRoot;

    public String Title;
    public String Publish;
    public String Summary;

    public Calendar getPublish()
    {
        Calendar publishCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA_FRENCH);

        try
        {
            publishCalendar.setTime(sdf.parse(Publish));
        }
        catch (ParseException e)
        {
            return null;
        }

        return publishCalendar;
    }

    public Element getNode()
    {
        return episodeSummaryRoot;
    }

    public void Save()
    {
        Node portugueseNode = episodeSummaryRoot.getElementsByTagName("portuguese").item(0);

        Node titleNode = portugueseNode.getAttributes().getNamedItem("title");
        titleNode.setNodeValue(Title);

        Node publishNode = episodeSummaryRoot.getAttributes().getNamedItem("publish");
        publishNode.setNodeValue(Publish);

        Node summaryNode = episodeSummaryRoot.getElementsByTagName("summary").item(0);
        summaryNode.setTextContent(Summary);
    }

}

