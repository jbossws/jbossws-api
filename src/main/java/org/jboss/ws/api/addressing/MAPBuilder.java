/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
    * @return a {@link org.jboss.ws.api.addressing.MAPEndpoint} instance
    */
   public MAPEndpoint newEndpoint(String address);

   /**
    * Creates a new {@link org.jboss.ws.api.addressing.MAPRelatesTo} instance
    * 
    * @return a {@link org.jboss.ws.api.addressing.MAPRelatesTo} instance
    */
   public MAPRelatesTo newRelatesTo(String id, QName type);

}
