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

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * Message Addressing Properties is a wrapper for the stack-specific JSR-261 addressing properties
 * classes implemented by JBossWS Native and CXF. It is used to localize dependence upon the WS
 * stack.
 * 
 * @author <a href="mailto:adinn@redhat.com">Andrew Dinn</a>
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * 
 */
public interface MAP
{
   public String getTo();

   public MAPEndpoint getFrom();

   public String getMessageID();

   public String getAction();

   public MAPEndpoint getFaultTo();

   public MAPEndpoint getReplyTo();

   public MAPRelatesTo getRelatesTo();

   public void setTo(String address);

   public void setFrom(MAPEndpoint epref);

   public void setMessageID(String messageID);

   public void setAction(String action);

   public void setReplyTo(MAPEndpoint epref);

   public void setFaultTo(MAPEndpoint epref);

   public void setRelatesTo(MAPRelatesTo relatesTo);

   public void addReferenceParameter(Element refParam);
   
   public List<Object> getReferenceParameters();

   public void initializeAsDestination(MAPEndpoint epref);

   public void installOutboundMapOnServerSide(Map<String, Object> requestContext, MAP map);
   
   public void installOutboundMapOnClientSide(Map<String, Object> requestContext, MAP map);

}
