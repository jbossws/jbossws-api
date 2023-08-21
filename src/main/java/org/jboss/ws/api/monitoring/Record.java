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
    * @param groupID  id
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
    * @param date   date
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
    * @param host   source host
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
    * @param host  destination host
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
    * @param type  message type
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
    * @param envelope   SOAP message envelope
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
    * @param key  id
    * @param value   value
    */
   public void addHeaders(String key, List<String> value);

   /**
    * Sets the HTTP headers of the record
    * 
    * @param headers  HTTP headers
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
    * @param operation   record operation
    */
   public void setOperation(QName operation);
}
