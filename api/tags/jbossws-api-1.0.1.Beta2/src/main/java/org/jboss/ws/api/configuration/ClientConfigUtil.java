/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ws.api.configuration;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;

import javax.xml.ws.BindingProvider;

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
    * Resolves a ClientConfigurer instance by first using the defining classloader and
    * failing that by using the current thread context classloader.
    * 
    * @return A ClientConfigurer instance
    */
   public static ClientConfigurer resolveClientConfigurer() {
      Iterator<ClientConfigurer> it = ServiceLoader.load(ClientConfigurer.class, ClientConfigUtil.class.getClassLoader()).iterator();
      if (!it.hasNext()) {
         it = ServiceLoader.load(ClientConfigurer.class, getContextClassLoader()).iterator();
      }
      return it.next();
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
