package com.example.hashsoapclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HashClient {
    public String getHashCode(byte[] bytes) {
        String wsURL = "http://localhost:8080/Service/ServiceHello";
        URL url;
        URLConnection connection;
        HttpURLConnection httpConn;
        String responseString;
        StringBuilder outputString= new StringBuilder();
        OutputStream out;
        InputStreamReader isr;
        BufferedReader in;

        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.ws.sample/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <ser:sayHello>\n" +
                        "         <!--Optional:-->\n" +
                        "         <file>" + Arrays.toString(bytes) + "</file>\n" +
                        "      </ser:sayHello>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";

        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;

            byte[] buffer;
            buffer = xmlInput.getBytes();

            String SOAPAction = "urn:SayHello";
            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length", String
                    .valueOf(buffer.length));

            httpConn.setRequestProperty("Content-Type", "text/xml; charset=iso-8859-1");


            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();

            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);

            while ((responseString = in.readLine()) != null)
            {
                outputString.append(responseString);
            }
//            System.out.println(outputString);
//            System.out.println("");

            // Get the response from the web service call
            Document document = parseXmlFile(outputString.toString());
//
            NodeList nodeLst = document.getElementsByTagName("ns2:sayHelloResponse");
            //            System.out.println("The response from the web service call is : " + webServiceResponse);
            return nodeLst.item(0).getTextContent();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
