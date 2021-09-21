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
