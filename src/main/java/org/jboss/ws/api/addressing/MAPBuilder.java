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
package org.jboss.ws.api.addressing;

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * MAPBuilder is a helper used to create objects used with class {@link org.jboss.ws.api.addressing.MAP}.
 * 
 * @author <a href="mailto:adinn@redhat.com">Andrew Dinn</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * 
 */
public interface MAPBuilder
{
   /**
    * Creates a new {@link org.jboss.ws.api.addressing.MAP} instance
    * 
    * @return a {@link org.jboss.ws.api.addressing.MAP} instance
    */
   public MAP newMap();

   /**
    * Retrieves the inbound server message address properties attached to a message context
    * 
    * @param ctx the server message context
    * @return the inbound server Message Addressing Properties instance
    */
   public MAP inboundMap(Map<String, Object> ctx);

   /**
    * Retrieves the outbound client message address properties attached to a message request map
    * 
    * @param ctx the client request properties map
    * @return the outbound server Message Addressing Properties instance
    */
   public MAP outboundMap(Map<String, Object> ctx);

   /**
    * Creates a new {@link org.jboss.ws.api.addressing.MAPConstants} instance
    * 
    * @return a {@link org.jboss.ws.api.addressing.MAPConstants} instance
    */
   public MAPConstants newConstants();

   /**
    * Creates a new {@link org.jboss.ws.api.addressing.MAPEndpoint} instance
    *
    * @param address  endpoint address
    * @return a {@link org.jboss.ws.api.addressing.MAPEndpoint} instance
    */
   public MAPEndpoint newEndpoint(String address);

   /**
    * Creates a new {@link org.jboss.ws.api.addressing.MAPRelatesTo} instance
    *
    * @param id    identifier
    * @param type  Qname
    * @return a {@link org.jboss.ws.api.addressing.MAPRelatesTo} instance
    */
   public MAPRelatesTo newRelatesTo(String id, QName type);

}
