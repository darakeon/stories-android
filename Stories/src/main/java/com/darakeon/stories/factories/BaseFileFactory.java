package com.darakeon.stories.factories;

import android.support.annotation.Nullable;

import com.darakeon.stories.R;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected IContext Context;
    protected IErrorHandler ErrorHandler;
    private Charset Encoding;

    protected BaseFileFactory(IContext context)
    {
        Context = context;
        ErrorHandler = context.GetErrorHandler();
        Encoding = StandardCharsets.UTF_8;
    }

    Pattern patternOpenTag = Pattern.compile("<[^/].+[^/]>");
    Pattern patternCloseTag = Pattern.compile("</.+>");



    @Nullable
    protected Element GetXml(File file)
    {
        if (!file.exists())
            return null;

        StringBuilder sb = getContent(file);
        if (sb == null) return null;

        byte[] bytes = getBytes(sb);

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
                line = br.readLine();
            }

            return sb;
        }
        catch (IOException exception)
        {
            ErrorHandler.WriteLogAndShowMessage(exception, R.string.ERROR_file_reader);
            return null;
        }
    }

    @Nullable
    private byte[] getBytes(StringBuilder sb)
    {
        return sb.toString().getBytes(Encoding);
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

        boolean done = transform(source, result);

        if (done)
        {
            indent(file);
        }

        return done;
    }

    private void indent(File file)
    {
        StringBuilder content = getContent(file);

        if (content != null)
        {
            try(FileOutputStream outputStream = new FileOutputStream(file))
            {
                try(Writer writer = new OutputStreamWriter(outputStream, Encoding))
                {
                    StringBuilder indentedContent = indent(content);
                    writer.write(indentedContent.toString());
                }
            }
            catch (IOException e)
            {
                ErrorHandler.WriteLogAndShowMessage(e, R.string.ERROR_indent_file);
            }
        }
    }

    private StringBuilder indent(StringBuilder builder)
    {
        String content =
            builder.toString()
                .replace("\t", "")
                .replace("><", ">\n<");

        String[] lines = content.split("\n");
        StringBuilder filledLines = new StringBuilder();

        int tabCount = 0;

        for(String line : lines)
        {
            if (!line.isEmpty())
            {
                boolean xmlDefineLine = addLine(filledLines, line, tabCount);

                if (!xmlDefineLine)
                    tabCount = adjustTabCount(tabCount, line);
            }
        }

        return filledLines;
    }

    private boolean addLine(StringBuilder filledLines, String line, int tabCount)
    {
        boolean xmlDefineLine = line.startsWith("<?");

        if (xmlDefineLine)
        {
            line = line.replace("UTF-8", "utf-8");
        }
        else
        {
            line = line.replace("/>", " />");
            filledLines.append("\r\n");
        }

        int tabToAdd = line.startsWith("</") ? tabCount - 1 : tabCount;

        for(int t = 0; t < tabToAdd; t++)
        {
            filledLines.append("\t");
        }

        filledLines.append(line);

        return xmlDefineLine;
    }

    private int adjustTabCount(int tabCount, String line)
    {
        Matcher matcherCloseTag = patternCloseTag.matcher(line);
        while(matcherCloseTag.find())
            tabCount--;

        Matcher matcherOpenTag = patternOpenTag.matcher(line);
        while(matcherOpenTag.find())
            tabCount++;

        return tabCount;
    }



    private boolean transform(DOMSource source, StreamResult result)
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            return true;
        }
        catch (TransformerException exception)
        {
            ErrorHandler.WriteLogAndShowMessage(exception, R.string.ERROR_transformer);
            return false;
        }
    }


    protected boolean CreateNewXml(File directory, char fileName, Tag mainTag)
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
            ShowFile(Context, file);
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



    public static boolean ShowFile(IContext context, File file)
    {
        boolean success = true;

        if (file.isDirectory())
        {
            File tempFile = new File(file, "keon.temp");

            success = createFile(tempFile);

            if (success)
            {
                ShowFile(context, tempFile);
            }
        }
        else
        {
            context.ShowFile(file);
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
        catch (SAXException | IOException exception)
        {
            ErrorHandler.WriteLogAndShowMessage(exception, R.string.ERROR_document_parser);
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
        catch (ParserConfigurationException exception)
        {
            ErrorHandler.WriteLogAndShowMessage(exception, R.string.ERROR_document_builder);
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
