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
package org.jboss.ws.api.configuration;

import jakarta.xml.ws.BindingProvider;

/**
 * JBossWS Client Configuration interface
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public interface ClientConfigurer
{
   /**
    * Reads a client configuration and setups the handlers in the provided BindingProvider accordingly.
    * 
    * @param bp             The BindingProvider instance to setup
    * @param configFile     The configuration file
    * @param configName     The configuration name
    */
   public void setConfigHandlers(BindingProvider bp, String configFile, String configName);
   
   /**
    * Reads a client configuration and setups the properties in the provided client proxy accordingly.
    * 
    * @param obj            The client proxy (port) or dispatch
    * @param configFile     The configuration file
    * @param configName     The configuration name
    */
   public void setConfigProperties(Object obj, String configFile, String configName);

}
