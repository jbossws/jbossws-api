/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
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
package org.jboss.ws.api;

import org.jboss.logging.Cause;
import org.jboss.logging.Message;
import org.jboss.logging.MessageBundle;

/**
 * JBossWS API exception messages
 * 
 * @author alessio.soldano@jboss.com
 */
@MessageBundle(projectCode = "JBWS")
public interface Messages {

    Messages MESSAGES = org.jboss.logging.Messages.getBundle(Messages.class);
    
    @Message(id = 20000, value = "Failed to load %s")
    IllegalStateException failedToLoad(@Cause Throwable cause, Object objs);
    
    @Message(id = 20001, value = "Cannot load properties: %s")
    SecurityException cannotLoadProperties(@Cause Throwable cause, String s);
    
    @Message(id = 20002, value = "Cannot find namespace uri for %s")
    IllegalArgumentException cannotFindNamespaceURI(String qualifiedName);

    @Message(id = 20004, value = "Source type not implemented: %s")
    RuntimeException sourceTypeNotImplemented(Class<?> clazz);
    
    @Message(id = 20011, value = "Cannot obtain required property: %s")
    IllegalStateException cannotObtainRequiredProperty(String prop);
    
    @Message(id = 20013, value = "Illegal auth method: %s")
    IllegalArgumentException illegalAuthMethod(String method);

    @Message(id = 20014, value = "Illegal transport guarantee: %s")
    IllegalArgumentException illegalTransportGuarantee(String method);

}
