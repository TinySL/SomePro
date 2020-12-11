package com.midtest.midtes;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;



public class XmlAys {

/*    public static void main(String[] args)  {
        Map mas = XmlAys.getxml();
        String ipadd = (String) mas.get("ipaddress");
        //String spostr = (String)mas.get("serport");
        //String clipostr = (String)mas.get("clientport");
        int serverport = Integer.parseInt((String)mas.get("serport"));
        int clientport = Integer.parseInt((String)mas.get("clientport"));

        System.out.println("IP"+ ipadd);
        System.out.println("sep"+ serverport);
        System.out.println("clp"+ clientport);
    }*/

    public static  Map getxml(){
        Map<String, String> cateMap = new HashMap<String, String>();
        try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            String xmlpath = getJarPath()+"servconfig.xml";
            //System.out.println(xmlpath);
            //Document doc = docBuilder.parse (new File("E:\\servconfig.xml"));
            Document doc = docBuilder.parse (new File(xmlpath));

            doc.getDocumentElement ().normalize ();



            NodeList listOfPersons = doc.getElementsByTagName("config");
            int totalPersons = listOfPersons.getLength();


            for(int s=0; s<listOfPersons.getLength() ; s++){


                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstPersonElement = (Element)firstPersonNode;

                    //-------
                    NodeList ipaddressList = firstPersonElement.getElementsByTagName("service_port");
                    Element ipadderssElement = (Element)ipaddressList.item(0);
                    NodeList textipadssist = ipadderssElement.getChildNodes();
                    cateMap.put("serport",((Node)textipadssist.item(0)).getNodeValue().trim());
                    //-------
                    NodeList serportList = firstPersonElement.getElementsByTagName("client_addr");
                    Element serportElement = (Element)serportList.item(0);
                    NodeList textserportist = serportElement.getChildNodes();
                    cateMap.put("ipaddress",((Node)textserportist.item(0)).getNodeValue().trim());
                    //--------
                    NodeList clientportList = firstPersonElement.getElementsByTagName("client_port");
                    Element clientportElement = (Element)clientportList.item(0);
                    NodeList textClientportList = clientportElement.getChildNodes();
                    cateMap.put("clientport",((Node)textClientportList.item(0)).getNodeValue().trim());

                }

            }

        }
        catch (SAXParseException err) {
            //System.out.println ("** Parsing error" + ", line "
            //        + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());

        }
        catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();

        }
        catch (Throwable t) {
            t.printStackTrace ();
        }

        return  cateMap;
    }

    //获取当前JAR包路径
    public static String getJarPath(){
        String path = XmlAys.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        int leng = path.indexOf(":/");
        String ss = path.substring(leng+2,path.length());
        leng = ss.indexOf(".jar");
        ss = ss.substring(0,leng+4);

        if (ss.endsWith(".jar"))
            ss = ss.substring(0, ss.lastIndexOf("/") + 1);
;
        return ss;

    }
}
