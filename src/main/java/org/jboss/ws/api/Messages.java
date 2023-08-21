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

import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageBundle;

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
