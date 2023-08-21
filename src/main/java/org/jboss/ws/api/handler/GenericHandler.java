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

import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.MessageContext;

import org.jboss.ws.api.Messages;

/**
 * A generic JAX-WS handler
 *
 * @author <a href="mailto:Thomas.Diesler@jboss.org">Thomas Diesler</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @since 13-Aug-2006
 */
public abstract class GenericHandler<C extends MessageContext> implements Handler<C>
{
   private volatile String handlerName;
   
   public String getHandlerName()
   {
      return handlerName;
   }

   public void setHandlerName(String handlerName)
   {
      this.handlerName = handlerName;
   }

   public boolean handleMessage(C msgContext)
   {
      Boolean outbound = (Boolean)msgContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
      if (outbound == null)
         throw Messages.MESSAGES.cannotObtainRequiredProperty(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

      return outbound ? handleOutbound(msgContext) : handleInbound(msgContext);
   }

   protected boolean handleOutbound(C msgContext)
   {
      return true;
   }

   protected boolean handleInbound(C msgContext)
   {
      return true;
   }

   public boolean handleFault(C messagecontext)
   {
      return true;
   }

   public void close(MessageContext messageContext)
   {
   }
   
   public String toString()
   {
      return (handlerName != null ? handlerName : super.toString());
   }
}
