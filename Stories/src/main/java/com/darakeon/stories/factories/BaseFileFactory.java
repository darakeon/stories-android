package com.darakeon.stories.factories;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Keon on 14/02/2016.
 */
public class BaseFileFactory
{
    protected Element getFileBody(File file) throws IOException, SAXException, ParserConfigurationException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();

        try
        {
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
                sb.append("\n");
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

}
