/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.ws.api.util;

import static org.jboss.ws.api.Messages.MESSAGES;
import static org.jboss.ws.api.Log.LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Basic DOM2 utilities
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @author <a href="mailto:Thomas.Diesler@jboss.org">Thomas Diesler</a>
 * 
 */
public abstract class DOMUtils
{
   /**
    * Get the qname of the given node.
    *
    * @param el  node
    * @return  Qname
    */
   public static QName getElementQName(Element el)
   {
      String qualifiedName = el.getNodeName();
      return resolveQName(el, qualifiedName);
   }

   /** Transform the given qualified name into a QName
    *
    * @param el  node
    * @param qualifiedName  qualified name
    * @return  QName
    */
   public static QName resolveQName(Element el, String qualifiedName)
   {
      QName qname;
      String prefix = "";
      String namespaceURI = "";
      String localPart = qualifiedName;

      int colIndex = qualifiedName.indexOf(":");
      if (colIndex > 0)
      {
         prefix = qualifiedName.substring(0, colIndex);
         localPart = qualifiedName.substring(colIndex + 1);

         if ("xmlns".equals(prefix))
         {
            namespaceURI = "URI:XML_PREDEFINED_NAMESPACE";
         }
         else
         {
            Element nsElement = el;
            while (namespaceURI.equals("") && nsElement != null)
            {
               namespaceURI = nsElement.getAttribute("xmlns:" + prefix);
               if (namespaceURI.equals(""))
                  nsElement = getParentElement(nsElement);
            }
         }

         if (namespaceURI.equals("") && el.getNamespaceURI() != null)
         {
            namespaceURI = el.getNamespaceURI();
         }

         if (namespaceURI.equals(""))
            throw MESSAGES.cannotFindNamespaceURI(qualifiedName);
      }
      else
      {
         Element nsElement = el;
         while (namespaceURI.equals("") && nsElement != null)
         {
            namespaceURI = nsElement.getAttribute("xmlns");
            if (namespaceURI.equals(""))
               nsElement = getParentElement(nsElement);
         }
      }

      qname = new QName(namespaceURI, localPart, prefix);
      return qname;
   }

   /** Get the value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return null if the attribute value is empty or the attribute is not present
    */
   public static String getAttributeValue(Element el, String attrName)
   {
      return getAttributeValue(el, new QName(attrName));
   }

   /** Get the value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return null if the attribute value is empty or the attribute is not present
    */
   public static String getAttributeValue(Element el, QName attrName)
   {
      String attr = null;
      if ("".equals(attrName.getNamespaceURI()))
         attr = el.getAttribute(attrName.getLocalPart());
      else
         attr = el.getAttributeNS(attrName.getNamespaceURI(), attrName.getLocalPart());

      if ("".equals(attr))
         attr = null;

      return attr;
   }

   /**
    * Get the qname value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return  QName
    */
   public static QName getAttributeValueAsQName(Element el, String attrName)
   {
      return getAttributeValueAsQName(el, new QName(attrName));

   }

   /**
    * Get the qname value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return  QName
    */
   public static QName getAttributeValueAsQName(Element el, QName attrName)
   {
      QName qname = null;

      String qualifiedName = getAttributeValue(el, attrName);
      if (qualifiedName != null)
      {
         qname = resolveQName(el, qualifiedName);
      }

      return qname;
   }

   /**
    * Get the boolean value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return  boolean
    */
   public static boolean getAttributeValueAsBoolean(Element el, String attrName)
   {
      return getAttributeValueAsBoolean(el, new QName(attrName));
   }

   /**
    * Get the boolean value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return  boolean
    */
   public static boolean getAttributeValueAsBoolean(Element el, QName attrName)
   {
      String attrVal = getAttributeValue(el, attrName);
      boolean ret = "true".equalsIgnoreCase(attrVal) || "1".equalsIgnoreCase(attrVal);
      return ret;
   }

   /**
    * Get the integer value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return  Integer
    */
   public static Integer getAttributeValueAsInteger(Element el, String attrName)
   {
      return getAttributeValueAsInteger(el, new QName(attrName));
   }

   /**
    * Get the integer value from the given attribute
    *
    * @param el   element
    * @param attrName   attribute name
    * @return  Integer
    */
   public static Integer getAttributeValueAsInteger(Element el, QName attrName)
   {
      String attrVal = getAttributeValue(el, attrName);
      return (attrVal != null ? new Integer(attrVal) : null);
   }

   /**
    * Get the attributes as Map&lt;QName, String&gt;
    *
    * @param el   element
    * @return  Map
    */
   public static Map<QName, String> getAttributes(Element el)
   {
      Map<QName, String> attmap = new HashMap<QName, String>();
      NamedNodeMap attribs = el.getAttributes();
      int len = attribs.getLength();
      for (int i = 0; i < len; i++)
      {
         Attr attr = (Attr) attribs.item(i);
         String name = attr.getName();
         QName qname = resolveQName(el, name);
         String value = attr.getNodeValue();
         attmap.put(qname, value);
      }
      return attmap;
   }

   /**
    * Copy attributes between elements
    *
    * @param destElement  element
    * @param srcElement   element
    */
   public static void copyAttributes(Element destElement, Element srcElement)
   {
      NamedNodeMap attribs = srcElement.getAttributes();
      int len = attribs.getLength();
      for (int i = 0; i < len; i++)
      {
         Attr attr = (Attr) attribs.item(i);
         String uri = attr.getNamespaceURI();
         String qname = attr.getName();
         String value = attr.getNodeValue();

         // Prevent DOMException: NAMESPACE_ERR: An attempt is made to create or
         // change an object in a way which is incorrect with regard to namespaces.
         if (uri == null && qname.startsWith("xmlns"))
         {
            if (LOGGER.isTraceEnabled())
               LOGGER.ignoringAttribute(uri, qname, value);
         }
         else
         {
            destElement.setAttributeNS(uri, qname, value);
         }
      }
   }

   /**
    * True if the node has text child elements only
    *
    * @param node  start node
    * @return  True if the node has text child
    */
   public static boolean hasTextChildNodesOnly(Node node)
   {
      NodeList nodeList = node.getChildNodes();
      int len = nodeList.getLength();
      if (len == 0)
         return false;

      for (int i = 0; i < len; i++)
      {
         Node acksToChildNode = nodeList.item(i);
         if (acksToChildNode.getNodeType() != Node.TEXT_NODE)
            return false;
      }

      return true;
   }

   /**
    * True if the node has child elements
    *
    * @param node  start node
    * @return  true when child has elements
    */
   public static boolean hasChildElements(Node node)
   {
      NodeList nlist = node.getChildNodes();
      int len = nlist.getLength();
      for (int i = 0; i < len; i++)
      {
         Node child = nlist.item(i);
         if (child.getNodeType() == Node.ELEMENT_NODE)
            return true;
      }
      return false;
   }

   /**
    * Gets child elements
    *
    * @param node  start node
    * @return  Iterator
    */
   public static Iterator<Element> getChildElements(Node node)
   {
      List<Element> list = new LinkedList<Element>();
      NodeList nlist = node.getChildNodes();
      int len = nlist.getLength();
      for (int i = 0; i < len; i++)
      {
         Node child = nlist.item(i);
         if (child.getNodeType() == Node.ELEMENT_NODE)
            list.add((Element) child);
      }
      return list.iterator();
   }

   /**
    * Get the concatenated text content, or null.
    *
    * @param node  node
    * @return  string
    */
   public static String getTextContent(Node node)
   {
      boolean hasTextContent = false;
      StringBuilder buffer = new StringBuilder();
      NodeList nlist = node.getChildNodes();
      int len = nlist.getLength();
      for (int i = 0; i < len; i++)
      {
         Node child = nlist.item(i);
         if (child.getNodeType() == Node.TEXT_NODE)
         {
            buffer.append(child.getNodeValue());
            hasTextContent = true;
         }
      }
      return (hasTextContent ? buffer.toString() : null);
   }

   /**
    * Gets the first child element
    *
    * @param node   start node
    * @return   element
    */
   public static Element getFirstChildElement(Node node)
   {
      return getFirstChildElement(node, false);
   }

   /**
    * Gets the first child element
    *
    * @param node  child
    * @param recursive  recurse flag
    * @return  element
    */
   public static Element getFirstChildElement(Node node, boolean recursive)
   {
      return getFirstChildElementIntern(node, null, recursive);
   }

   /** Gets the first child element for a given local name without namespace
    *
    * @param node   child
    * @param nodeName   name
    * @return  element
    */
   public static Element getFirstChildElement(Node node, String nodeName)
   {
      return getFirstChildElement(node, nodeName, false);
   }

   /**
    * Gets the first child element for a given local name without namespace
    *
    * @param node  child
    * @param nodeName  name
    * @param recursive   recurse flag
    * @return   element
    */
   public static Element getFirstChildElement(Node node, String nodeName, boolean recursive)
   {
      return getFirstChildElementIntern(node, new QName(nodeName), recursive);
   }

   /**
    * Gets the first child element for a given qname
    *
    * @param node  child
    * @param nodeName  name
    * @return   element
    */
   public static Element getFirstChildElement(Node node, QName nodeName)
   {
      return getFirstChildElement(node, nodeName, false);
   }

   /**
    * Gets the first child element for a given qname
    *
    * @param node  child
    * @param nodeName  name
    * @param recursive  recurse flag
    * @return   element
    */
   public static Element getFirstChildElement(Node node, QName nodeName, boolean recursive)
   {
      return getFirstChildElementIntern(node, nodeName, recursive);
   }

   private static Element getFirstChildElementIntern(Node node, QName nodeName, boolean recursive)
   {
      Element childElement = null;
      Iterator<Element> it = getChildElementsIntern(node, nodeName, recursive);
      if (it.hasNext())
      {
         childElement = (Element) it.next();
      }
      return childElement;
   }

   /**
    * Gets the child elements for a given local name without namespace
    *
    * @param node child
    * @param nodeName  name
    * @return  Iterator
    */
   public static Iterator<Element> getChildElements(Node node, String nodeName)
   {
      return getChildElements(node, nodeName, false);
   }

   /**
    * Gets the child elements for a given local name without namespace
    *
    * @param node  child
    * @param nodeName  name
    * @param recursive  recurse flag
    * @return  Iterator
    */
   public static Iterator<Element> getChildElements(Node node, String nodeName, boolean recursive)
   {
      return getChildElementsIntern(node, new QName(nodeName), recursive);
   }

   /**
    * Gets the child element for a given qname
    *
    * @param node  child
    * @param nodeName   name
    * @return  Iterator
    */
   public static Iterator<Element> getChildElements(Node node, QName nodeName)
   {
      return getChildElements(node, nodeName, false);
   }

   /**
    * Gets the child element for a given qname
    *
    * @param node    child
    * @param nodeName  name
    * @param recursive   recurse flag
    * @return  Iterator
    */
   public static Iterator<Element> getChildElements(Node node, QName nodeName, boolean recursive)
   {
      return getChildElementsIntern(node, nodeName, recursive);
   }

   public static List<Element> getChildElementsAsList(Node node, String nodeName)
   {
      return getChildElementsAsList(node, nodeName, false);
   }

   public static List<Element> getChildElementsAsList(Node node, String nodeName, boolean recursive)
   {
      return getChildElementsAsListIntern(node, new QName(nodeName), recursive);
   }

   public static List<Element> getChildElementsAsList(Node node, QName nodeName)
   {
      return getChildElementsAsList(node, nodeName, false);
   }

   public static List<Element> getChildElementsAsList(Node node, QName nodeName, boolean recursive)
   {
      return getChildElementsAsListIntern(node, nodeName, recursive);
   }

   private static List<Element> getChildElementsAsListIntern(Node node, QName nodeName, boolean recursive)
   {
      List<Element> list = new LinkedList<Element>();

      NodeList nlist = node.getChildNodes();
      int len = nlist.getLength();
      for (int i = 0; i < len; i++)
      {
         Node child = nlist.item(i);
         if (child.getNodeType() == Node.ELEMENT_NODE)
         {
            search(list, (Element) child, nodeName, recursive);
         }
      }
      return list;
   }

   private static void search(List<Element> list, Element baseElement, QName nodeName, boolean recursive)
   {
      if (nodeName == null)
      {
         list.add(baseElement);
      }
      else
      {
         QName qname;
         if (nodeName.getNamespaceURI().length() > 0)
         {
            qname = new QName(baseElement.getNamespaceURI(), baseElement.getLocalName());
         }
         else
         {
            qname = new QName(baseElement.getLocalName());
         }
         if (qname.equals(nodeName))
         {
            list.add(baseElement);
         }
      }
      if (recursive)
      {
         NodeList nlist = baseElement.getChildNodes();
         int len = nlist.getLength();
         for (int i = 0; i < len; i++)
         {
            Node child = nlist.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE)
            {
               search(list, (Element) child, nodeName, recursive);
            }
         }
      }
   }

   private static Iterator<Element> getChildElementsIntern(Node node, QName nodeName, boolean recursive)
   {
      return getChildElementsAsListIntern(node, nodeName, recursive).iterator();
   }

   /**
    * Gets parent element or null if there is none
    *
    * @param node  child
    * @return  element
    */
   public static Element getParentElement(Node node)
   {
      Node parent = node.getParentNode();
      return (parent instanceof Element ? (Element) parent : null);
   }

   public static Element sourceToElement(Source source, DocumentBuilder builder) throws IOException
   {
      Element retElement = null;

      if (source instanceof StreamSource)
      {
         StreamSource streamSource = (StreamSource) source;

         InputStream ins = streamSource.getInputStream();
         if (ins != null)
         {
            retElement = DOMUtils.parse(ins, builder);
         }
         Reader reader = streamSource.getReader();
         if (reader != null)
         {
            retElement = DOMUtils.parse(new InputSource(reader), builder);
         }
      }
      else if (source instanceof DOMSource)
      {
         DOMSource domSource = (DOMSource) source;
         Node node = domSource.getNode();
         if (node instanceof Element)
         {
            retElement = (Element) node;
         }
         else if (node instanceof Document)
         {
            retElement = ((Document) node).getDocumentElement();
         }
      }
      else if (source instanceof SAXSource)
      {
         // The fact that JAXBSource derives from SAXSource is an implementation detail.
         // Thus in general applications are strongly discouraged from accessing methods defined on SAXSource.
         // The XMLReader object obtained by the getXMLReader method shall be used only for parsing the InputSource object returned by the getInputSource method.

         final boolean hasInputSource = ((SAXSource) source).getInputSource() != null;
         final boolean hasXMLReader = ((SAXSource) source).getXMLReader() != null;

         if (hasInputSource || hasXMLReader)
         {
            try
            {
               TransformerFactory tf = TransformerFactory.newInstance();
               ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
               Transformer transformer = tf.newTransformer();
               transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
               transformer.setOutputProperty(OutputKeys.METHOD, "xml");
               transformer.transform(source, new StreamResult(baos));
               retElement = DOMUtils.parse(new ByteArrayInputStream(baos.toByteArray()), builder);
            }
            catch (TransformerException ex)
            {
               throw new IOException(ex);
            }
         }
      }
      else
      {
         throw MESSAGES.sourceTypeNotImplemented(source.getClass());
      }

      return retElement;
   }

   /**
    * Parse the given XML string and return the root Element
    *
    * @param xmlString   string to be parsed
    * @param builder     document builder
    * @return    element
    * @throws IOException    exception
    */
   public static Element parse(String xmlString, DocumentBuilder builder) throws IOException
   {
      try
      {
         return parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8")), builder);
      }
      catch (IOException e)
      {
         LOGGER.cannotParse(xmlString);
         throw e;
      }
   }

   /**
    * Parse the given XML stream and return the root Element
    *
    * @param xmlStream    input stream
    * @param builder     document builder
    * @return  element
    * @throws IOException   exception
    */
   public static Element parse(InputStream xmlStream, DocumentBuilder builder) throws IOException
   {
      try
      {
         Document doc;
         synchronized (builder) //synchronize to prevent concurrent parsing on the same DocumentBuilder
         {
            doc = builder.parse(xmlStream);
         }
         return doc.getDocumentElement();
      }
      catch (SAXException se)
      {
         throw new IOException(se.toString());
      }
      finally
      {
         xmlStream.close();
      }
   }

   /**
    * Parse the given input source and return the root Element
    *
    * @param source   input source
    * @param builder  document builder
    * @return  element
    * @throws IOException   exception
    */
   public static Element parse(InputSource source, DocumentBuilder builder) throws IOException
   {
      try
      {
         Document doc;
         synchronized (builder) //synchronize to prevent concurrent parsing on the same DocumentBuilder
         {
            doc = builder.parse(source);
         }
         return doc.getDocumentElement();
      }
      catch (SAXException se)
      {
         throw new IOException(se.toString());
      }
      finally
      {
         InputStream is = source.getByteStream();
         if (is != null)
         {
            is.close();
         }
         Reader r = source.getCharacterStream();
         if (r != null)
         {
            r.close();
         }
      }
   }

   /**
    * Create an Element for a given name, prefix and uri
    *
    * @param localPart   xml local part
    * @param prefix      xml prefix
    * @param uri         uri
    * @param doc         document
    * @return  element
    */
   public static Element createElement(String localPart, String prefix, String uri, Document doc)
   {
      if (prefix == null || prefix.length() == 0)
      {
         if (LOGGER.isTraceEnabled())
            LOGGER.creatingElement(uri, prefix);
         return doc.createElementNS(uri, localPart);
      }
      else
      {
         if (LOGGER.isTraceEnabled())
            LOGGER.creatingElement(uri, prefix, localPart);
         return doc.createElementNS(uri, prefix + ":" + localPart);
      }
   }
}
