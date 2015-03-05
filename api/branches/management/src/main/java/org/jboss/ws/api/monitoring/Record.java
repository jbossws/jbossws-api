/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.ws.api.monitoring;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * An interface defining recordable data
 * 
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @since 8-Dec-2007
 */
public interface Record extends Serializable
{
   /**
    * {@link org.jboss.ws.api.monitoring.Record}'s message type; can be either inboud or outbound
    * 
    * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
    *
    */
   public enum MessageType {INBOUND, OUTBOUND};
   
   /**
    * Gets the group ID corresponding to the current message exchange flow
    * 
    * @return the groupID of the record
    */
   public String getGroupID();
   
   /**
    * Sets the groupID
    * 
    * @param groupID
    */
   public void setGroupID(String groupID);
   
   /**
    * Gets the date of this record
    * 
    * @return the date of the record
    */
   public Date getDate();
   
   /**
    * Sets the date of the record
    * 
    * @param date
    */
   public void setDate(Date date);
   
   /**
    * Gets the source (message sender) host. The result format conforms to RFC2732
    * 
    * @return source host
    */
   public String getSourceHost();
   
   /**
    * Sets the source host
    * 
    * @param host
    */
   public void setSourceHost(String host);
   
   /**
    * Gets the source (message sender) host. The result format conforms to RFC2732
    * 
    * @return the source host
    */
   public String getDestinationHost();
   
   /**
    * Sets the destination host
    * 
    * @param host
    */
   public void setDestinationHost(String host);
   
   /**
    * Gets the message type, i.e. MessageType.INBOUND or MessageType.OUTBOUND
    * 
    * @return the message type
    */
   public MessageType getMessageType();
   
   /**
    * Sets the message type
    * 
    * @param type
    */
   public void setMessageType(MessageType type);
   
   
   /**
    * Gets the SOAP message envelope
    * 
    * @return the SOAP message envelope
    */
   public String getEnvelope();
   
   /**
    * Sets the SOAP message envelope
    * 
    * @param envelope
    */
   public void setEnvelope(String envelope);
   
   /**
    * Gets the HTTP headers
    * 
    * @return the headers
    */
   public Map<String, List<String>> getHeaders();
   
   /**
    * Adds a HTTP header to the record
    * 
    * @param key
    * @param value
    */
   public void addHeaders(String key, List<String> value);

   /**
    * Sets the HTTP headers of the record
    * 
    * @param headers
    */
   public void setHeaders(Map<String, List<String>> headers);
   
   /**
    * Gets the invoked operation
    * 
    * @return the operation
    */
   public QName getOperation();
   
   /**
    * Sets the record's operation
    * 
    * @param operation
    */
   public void setOperation(QName operation);
}
