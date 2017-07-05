package com.darakeon.stories.factories;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class BaseFileFactory
{
    protected Element GetFileBody(File file) throws IOException, SAXException, ParserConfigurationException
    {
        if (!file.exists())
            return null;

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();

        try
        {
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
                sb.append("\r\n");
                line = br.readLine();
            }
        }
        finally
        {
            br.close();
        }

        ByteArrayInputStream input = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

        return doc.getDocumentElement();
    }

    protected void SetFileBody(File file, Element node) throws ParserConfigurationException, TransformerException
    {
        Document document = getDocument();

        Node importedNode = document.importNode(node, true);
        document.appendChild(importedNode);

        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(file);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }



    protected void CreateNewXml(Activity activity, File directory, char fileName, Tag mainTag)
    {
        File file = new File(directory, fileName + ".xml");

        try
        {
            Document document = getDocument();

            Element node = createElement(document, mainTag);

            SetFileBody(file, node);

            ShowFile(activity, file);
        }
        catch (ParserConfigurationException | TransformerException e)
        {
            e.printStackTrace();
        }

    }

    public static void ShowFile(Activity activity, File file)
    {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        activity.sendBroadcast(intent);
    }

    private Element createElement(Document document, Tag tag)
    {
        Element parent = document.createElement(tag.Name);

        for(int t = 0; t < tag.Children.size(); t++)
        {
            Tag child = tag.Children.get(t);
            Element childElement = createElement(document, child);
            parent.appendChild(childElement);
        }

        for(int t = 0; t < tag.AttributeList.size(); t++)
        {
            Attribute attribute = tag.AttributeList.get(t);
            Attr attributeElement = document.createAttribute(attribute.Name);
            attributeElement.setValue(attribute.Value);
            parent.getAttributes().setNamedItem(attributeElement);
        }

        return parent;
    }



    private Document getDocument() throws ParserConfigurationException
    {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }



    public class Tag
    {
        public Tag(String name)
        {
            Name = name;
            Children = new ArrayList<>();
            AttributeList = new ArrayList<>();
        }

        public Tag Add(String name)
        {
            Tag child = new Tag(name);
            Children.add(child);
            return child;
        }

        public void AddAttribute(String name, String value)
        {
            Attribute child = new Attribute(name, value);
            AttributeList.add(child);
        }

        public String Name;
        public ArrayList<Tag> Children;
        public ArrayList<Attribute> AttributeList;
    }

    public class Attribute
    {
        public String Name;
        public String Value;

        public Attribute(String name, String value)
        {
            Name = name;
            Value = value;
        }
    }

}
