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
package org.jboss.ws.api;

import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

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
