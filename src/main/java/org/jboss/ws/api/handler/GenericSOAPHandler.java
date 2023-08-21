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
