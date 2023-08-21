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

import org.w3c.dom.Element;

/**
 * MAPEndpoint is a wrapper which works with class {@link org.jboss.ws.api.addressing.MAP}.
 * 
 * @author <a href="adinn@redhat.com">Andrew Dinn</a>
 * @author <a href="alessio.soldano@jboss.com">Alessio Soldano</a>
 * 
 */
public interface MAPEndpoint
{
   public String getAddress();

   public void addReferenceParameter(Element element);
   
   public List<Object> getReferenceParameters();

}
