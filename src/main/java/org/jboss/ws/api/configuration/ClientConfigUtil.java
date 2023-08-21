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

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import org.jboss.ws.api.util.ServiceLoader;

import jakarta.xml.ws.BindingProvider;

/**
 * A facility for setting a JBossWS Client Configuration
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public abstract class ClientConfigUtil
{
   /**
    * Reads a client configuration and setups the handlers in the provided BindingProvider accordingly.
    * This leverages the resolveClientConfigurer() method for getting the ClientConfigure to use.
    * 
    * @param bp             The BindingProvider instance to setup
    * @param configFile     The configuration file
    * @param configName     The configuration name
    */
   public static void setConfigHandlers(BindingProvider bp, String configFile, String configName) {
      ClientConfigurer configurer = resolveClientConfigurer();
      configurer.setConfigHandlers(bp, configFile, configName);
   }
   
   /**
    * Reads a client configuration and setups the properties in the provided proxy accordingly.
    * This leverages the resolveClientConfigurer() method for getting the ClientConfigure to use.
    * 
    * @param proxy             The client proxy (port) instance to setup
    * @param configFile     The configuration file
    * @param configName     The configuration name
    */
   public static void setConfigProperties(Object proxy, String configFile, String configName) {
      ClientConfigurer configurer = resolveClientConfigurer();
      configurer.setConfigProperties(proxy, configFile, configName);
   }
   
   /**
    * Resolves a ClientConfigurer instance by first using the current thread context classloader and
    * failing that by using the defining classloader.
    * 
    * @return A ClientConfigurer instance
    */
   public static ClientConfigurer resolveClientConfigurer() {
      Iterator<ClientConfigurer> it = java.util.ServiceLoader.load(ClientConfigurer.class, getContextClassLoader()).iterator();
      if (it.hasNext()) {
         return it.next();
      } else {
         return (ClientConfigurer)ServiceLoader.loadService(ClientConfigurer.class.getName(),
               "org.jboss.ws.common.configuration.ConfigHelper", ClientConfigUtil.class.getClassLoader());
      }
   }
   
   private static ClassLoader getContextClassLoader()
   {
      SecurityManager sm = System.getSecurityManager();
      if (sm == null)
      {
         return Thread.currentThread().getContextClassLoader();
      }
      else
      {
         return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run()
            {
               return Thread.currentThread().getContextClassLoader();
            }
         });
      }
   }

}
