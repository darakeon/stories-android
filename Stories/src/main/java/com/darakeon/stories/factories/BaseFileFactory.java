package com.darakeon.stories.factories;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.darakeon.stories.R;
import com.darakeon.stories.activities.MyActivity;
import com.darakeon.stories.errorhandler.ErrorHandler;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class BaseFileFactory
{
    protected MyActivity Activity;
    protected ErrorHandler ErrorHandler;

    protected BaseFileFactory(MyActivity activity)
    {
        Activity = activity;
        ErrorHandler = Activity.ErrorHandler;
    }

    @Nullable
    protected Element GetXml(File file)
    {
        if (!file.exists())
            return null;

        StringBuilder sb = getContent(file);
        if (sb == null) return null;

        byte[] bytes = getBytes(sb);

        if (bytes == null)
            return null;

        ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        DocumentBuilder builder = getDocumentBuilder();

        if (builder == null)
            return null;

        Document document = parseDocument(input, builder);

        if (document == null)
            return null;

        return document.getDocumentElement();
    }

    @Nullable
    private StringBuilder getContent(File file)
    {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
                sb.append("\r\n");
                line = br.readLine();
            }

            return sb;
        }
        catch (IOException e)
        {
            ErrorHandler.Write(R.string.ERROR_file_reader, e);
            return null;
        }
    }

    @Nullable
    private byte[] getBytes(StringBuilder sb)
    {
        String text = sb.toString();

        try
        {
            return text.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            ErrorHandler.Write(R.string.ERROR_wrong_encoding, e);
            return null;
        }
    }


    protected boolean SetFileBody(File file, Element node)
    {
        Document document = createDocument();

        if (document == null)
        {
            return false;
        }

        Node importedNode = document.importNode(node, true);
        document.appendChild(importedNode);

        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(file);

        return transform(source, result);
    }

    private boolean transform(DOMSource source, StreamResult result)
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            return true;
        }
        catch (TransformerException e)
        {
            ErrorHandler.Write(R.string.ERROR_transformer, e);
            return false;
        }
    }


    protected boolean CreateNewXml(Activity activity, File directory, char fileName, Tag mainTag)
    {
        File file = new File(directory, fileName + ".xml");

        Document document = createDocument();

        if (document == null)
        {
            return false;
        }

        Element node = createElement(document, mainTag);

        boolean createdFile = SetFileBody(file, node);

        if (createdFile)
        {
            ShowFile(activity, file);
        }

        return createdFile;
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



    public static boolean ShowFile(Activity activity, File file)
    {
        boolean success = true;

        if (file.isDirectory())
        {
            File tempFile = new File(file, "temp.keon");

            success = createFile(tempFile);

            if (success)
            {
                ShowFile(activity, tempFile);
            }
        }
        else
        {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            activity.sendBroadcast(intent);
        }

        return success;
    }

    private static boolean createFile(File file)
    {
        boolean success;

        try
        {
            success = file.exists() || file.createNewFile();
        }
        catch (IOException e)
        {
            success = false;
        }

        return success;
    }





    @Nullable
    private Document parseDocument(ByteArrayInputStream input, DocumentBuilder builder)
    {
        try
        {
            return builder.parse(input);
        }
        catch (SAXException | IOException e)
        {
            ErrorHandler.Write(R.string.ERROR_document_parser, e);
            return null;
        }
    }

    @Nullable
    private Document createDocument()
    {
        DocumentBuilder builder = getDocumentBuilder();
        return builder == null ? null : builder.newDocument();
    }

    @Nullable
    private DocumentBuilder getDocumentBuilder()
    {
        try
        {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            ErrorHandler.Write(R.string.ERROR_document_builder, e);
            return null;
        }
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
