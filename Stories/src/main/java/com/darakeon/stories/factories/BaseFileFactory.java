package com.darakeon.stories.factories;

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
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();

        try
        {
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
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



    protected void CreateNewXml(IFileUncover fileUncover, File directory, char fileName, Tag motherTag) throws ParserConfigurationException, TransformerException
    {
        File file = new File(directory, fileName + ".xml");

        Document document = getDocument();

        Element node = createTag(document, motherTag);

        SetFileBody(file, node);

        fileUncover.ShowFile(file);
    }

    private Element createTag(Document document, Tag tag)
    {
        Element parent = document.createElement(tag.Name);

        for(int t = 0; t < tag.Children.size(); t++)
        {
            Element child = createTag(document, tag.Children.get(t));
            parent.appendChild(child);
        }

        return parent;
    }



    private Document getDocument() throws ParserConfigurationException
    {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }



    public interface IFileUncover
    {
        void ShowFile(File file);
    }

    public class Tag
    {
        public Tag(String name)
        {
            Name = name;
            Children = new ArrayList<>();
        }

        public Tag Add(String name)
        {
            Tag child = new Tag(name);
            Children.add(child);
            return child;
        }

        public String Name;
        public ArrayList<Tag> Children;
    }
}
