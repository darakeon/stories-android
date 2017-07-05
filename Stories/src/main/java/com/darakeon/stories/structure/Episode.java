package com.darakeon.stories.structure;

import android.content.Context;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Handhara on 09/09/2015.
 */
public class Episode
{
    public Episode(Context context, String season, String episode)
    {
        try
        {
            File dir = context.getFilesDir();
            File seasonDir = new File(dir, season);
            File episodeDir = new File(seasonDir, episode);

            episodeDir.mkdirs();

            File summary = new File(episodeDir, "_.xml");

            Writer w = new BufferedWriter(new FileWriter(summary));

            w.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
                    "<story season=\"A\" episode=\"01\" last=\"g\" publish=\"2015-03-27\">\r\n" +
                    "\t<portuguese title=\"Gaia e Cronos\" />\r\n" +
                    "\t<english title=\"Gaia and Kronos\" />\r\n" +
                    "\t<summary>Modret é descendente de atlantes que vive na Terra. Troca de lugar com Likín, que descobre que seu papel não é ser guia, e sim o de Modret. Tempos depois, ele volta à Terra atrás de Lisa, antiga paixão. O homem que o criou (sequestrou quando criança), Nilrem, volta junto, e deixa em coma a amiga dela, Halen. Anos mais tarde, Halen, Rith (seu noivo), e Hery, apaixonado por Lisa, por forças desconhecidas, acabam indo parar na lua de Saturno, onde está o povo atlante. Lisa tivera dois filhos com Modret: Angely e Edmont. Acaba ficando com Hery e tendo mais uma filha, Katerine. O cão de Edmont mata Lisa, Hery mata Modret e Edmont mata Hery. Rith e Halen fogem pra Terra com Katerine. Com 12 anos, Angely e Edmont se enfrentam e Angely foge para a Terra também, conhecendo Júlio e sua filha, de 10 anos, Agatha. Edmont chega algum tempo depois, a pedido de Mel, e pede desculpas a Angely. Júlio adota ambos. Angely tem um sonho sobre os dois, onde há um espelho, e só há o reflexo de um deles.</summary>\r\n" +
                    "</story>");

            w.flush();
            w.close();

            BufferedReader br = new BufferedReader(new FileReader(summary));
            StringBuilder sb = new StringBuilder();

            try {
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                sb.toString();
            } finally {
                br.close();
            }

            ByteArrayInputStream input = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

            Element root = doc.getDocumentElement();
            NodeList x = root.getChildNodes();

            for (int n = 0; n < x.getLength(); n++)
            {
                Node y = x.item(n);
                Element e = (Element) y;
                String a = e.getAttribute("x");
            }

        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }

    }

    private void get(String server, String user, String password, String serverRoad, File file) throws IOException
    {
        FTPClient ftpClient = getClient(server, user, password, serverRoad);

        BufferedOutputStream buffOut =
                new BufferedOutputStream(new FileOutputStream(file));

        ftpClient.retrieveFile(file.getName(), buffOut);
        buffOut.close();

        ftpClient.logout();
        ftpClient.disconnect();
    }

    private void set(String server, String user, String password, String serverRoad, File file) throws IOException
    {
        FTPClient ftpClient = getClient(server, user, password, serverRoad);

        BufferedInputStream buffIn =
                new BufferedInputStream(new FileInputStream(file));

        ftpClient.storeFile(file.getName(), buffIn);
        buffIn.close();

        ftpClient.logout();
        ftpClient.disconnect();
    }

    private FTPClient getClient(String server, String user, String password, String serverRoad) throws IOException
    {
        FTPClient ftpClient = new FTPClient();

        ftpClient.connect(InetAddress.getByName(server));
        ftpClient.login(user, password);
        ftpClient.enterLocalPassiveMode();

        ftpClient.changeWorkingDirectory(serverRoad);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        return ftpClient;
    }

    public static ArrayList<String> getList(Context context, String season)
    {
        ArrayList<String> list = new ArrayList<>();

        File dir = context.getFilesDir();
        File seasonDir = new File(dir.getAbsolutePath(), season);
        File[] filesList = seasonDir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                list.add(file.getName());
            }
        }

        return list;
    }
}
