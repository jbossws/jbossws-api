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

import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.LogMessage;
import org.jboss.logging.Message;
import org.jboss.logging.MessageLogger;

/**
 * JBossWS API log messages
 * 
 * @author alessio.soldano@jboss.com
 */
@MessageLogger(projectCode = "JBWS")
public interface Log extends BasicLogger
{
    Log LOGGER = org.jboss.logging.Logger.getMessageLogger(Log.class, "org.jboss.ws.api");
    
    @LogMessage(level = TRACE)
    @Message(id = 20003, value = "Ignore attribute: [uri=%s, qname=%s, value=%s]")
    void ignoringAttribute(String uri, String qname, String value);
    
    @LogMessage(level = ERROR)
    @Message(id = 20005, value = "Cannnot parse: %s")
    void cannotParse(String s);
    
    @LogMessage(level = TRACE)
    @Message(id = 20006, value = "createElement {%s}%s")
    void creatingElement(String uri, String prefix);
    
    @LogMessage(level = TRACE)
    @Message(id = 20007, value = "createElement {%s}%s:%s")
    void creatingElement(String uri, String prefix, String localPart);
    
    @LogMessage(level = TRACE)
    @Message(id = 20008, value = "pushGroupID: %s (%s)")
    void pushGroupID(String groupId, String threadName);
    
    @LogMessage(level = TRACE)
    @Message(id = 20009, value = "peekGroupID: %s (%s)")
    void peekGroupID(String groupId, String threadName);
    
    @LogMessage(level = TRACE)
    @Message(id = 20010, value = "popGroupID: %s (%s)")
    void popGroupID(String groupId, String threadName);
    
    @LogMessage(level = WARN)
    @Message(id = 20012, value = "Non-standard method: %s")
    void nonStandardMethod(String s);
    
    @LogMessage(level = TRACE)
    @Message(id = 20015, value = "Could not get %s module classloader: %s")
    void couldNotGetModuleClassLoader(String module, Exception e);
    
}
