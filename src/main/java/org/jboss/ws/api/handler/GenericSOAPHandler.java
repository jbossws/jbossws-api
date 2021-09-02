/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ws.api.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

/**
 * A generic JAX-WS soap handler
 *
 * @author <a href="mailto:Thomas.Diesler@jboss.org">Thomas Diesler</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @since 13-Aug-2006
 */
public abstract class GenericSOAPHandler<C extends SOAPMessageContext> extends GenericHandler<C> implements SOAPHandler<C>
{
   // The header blocks that can be processed by this Handler instance
   private volatile Set<QName> headers = null;
   
   /**
    * Gets the header blocks that can be processed by this Handler instance.
    * 
    * @return a set of headers
    */
   public Set<QName> getHeaders()
   {
      if (headers == null) {
         return Collections.emptySet();
      } else {
         return headers;
      }
   }

   /**
    * Sets the header blocks that can be processed by this Handler instance.
    * 
    * @param headers the headers to be set
    */
   public void setHeaders(Set<QName> headers)
   {
      this.headers = Collections.unmodifiableSet(new HashSet<QName>(headers));
   }
}
