/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ws.api.addressing;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.jboss.ws.api.Log;
import org.jboss.ws.api.util.ServiceLoader;

/**
 * Factory for {@link org.jboss.ws.api.addressing.MAPBuilder}; to be used to get an instance of the proper MAPBuilder
 * implementation which depends on the JBossWS stack in use.
 * 
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @since 25-May-2009
 *
 */
public abstract class MAPBuilderFactory
{
   private static final String CLASSLOADER_PROVIDER = "org.jboss.wsf.spi.classloading.ClassLoaderProvider";
   private static final String CLASSLOADER_PROVIDER_GET_METHOD = "getDefaultProvider";
   private static final String CLASSLOADER_PROVIDER_GET_CL_METHOD = "getServerIntegrationClassLoader";
   private static final String JBOSSWS_SPI_MODULE = "org.jboss.ws.spi";
   
   /**
    * Get the proper {@link org.jboss.ws.api.addressing.MAPBuilderFactory} instance according to stack in use;
    * this uses the ws server integration modular classloader if available
    * (alternatively the current thread context classloader is used) for
    * looking up the factory name. 
    * 
    * @return a {@link org.jboss.ws.api.addressing.MAPBuilderFactory} instance
    */
   public static MAPBuilderFactory getInstance()
   {
      return getInstance(getServerIntegrationClassLoader());
   }
   
   /**
    * The same as {@link #getInstance()} except the provided ClassLoader instance
    * is used to lookup the factory name.
    * 
    * @param loader the classloader instance to use
    * @return a {@link org.jboss.ws.api.addressing.MAPBuilderFactory} instance
    */
   public static MAPBuilderFactory getInstance(ClassLoader loader)
   {
      return (MAPBuilderFactory)ServiceLoader.loadService(MAPBuilderFactory.class.getName(), null, loader);
   }
   
   /**
    * Get an instance of {@link org.jboss.ws.api.addressing.MAPBuilder}
    * 
    * @return a {@link org.jboss.ws.api.addressing.MAPBuilder} instance
    */
   public abstract MAPBuilder getBuilderInstance();
   
   private static ClassLoader getServerIntegrationClassLoader()
   {
      ClassLoader cl = null;
      try
      {
         //if jboss-modules is available (i.e. AS7 in-container) we get the org.jboss.ws.spi:main module first,
         //the we use the ClassLoaderProvider facilities for getting the integration classloader.
         //The reason for going this way is in not exposing the ClassLoaderProvider to the public API,
         //while MAPBuilderFactory needs to work automatically (no need to specify the loader to use)
         final ClassLoader spiLoader = getSPIClassLoader();
         if (spiLoader != null)
         {
            final Class<?> clazz = spiLoader.loadClass(CLASSLOADER_PROVIDER);
            final Object clProvider = clazz.getMethod(CLASSLOADER_PROVIDER_GET_METHOD).invoke(null);
            cl = (ClassLoader)clazz.getMethod(CLASSLOADER_PROVIDER_GET_CL_METHOD).invoke(clProvider);
         }
      }
      catch (RuntimeException re)
      {
         throw re;
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
      return (cl != null) ? cl : getContextClassLoader();
   }
   
   private static ClassLoader getSPIClassLoader()
   {
      ClassLoader moduleClassLoader = null;
      try {
         Class<?> moduleClass = Class.forName("org.jboss.modules.Module");
         Class<?> moduleIdentifierClass = Class.forName("org.jboss.modules.ModuleIdentifier");
         Class<?> moduleLoaderClass = Class.forName("org.jboss.modules.ModuleLoader");
         Object moduleLoader = moduleClass.getMethod("getBootModuleLoader").invoke(null);
         Object moduleIdentifier = moduleIdentifierClass.getMethod("create", String.class).invoke(null, JBOSSWS_SPI_MODULE);
         Object module = moduleLoaderClass.getMethod("loadModule", moduleIdentifierClass).invoke(moduleLoader, moduleIdentifier);
         moduleClassLoader = (ClassLoader)moduleClass.getMethod("getClassLoader").invoke(module);
      } catch (Exception e) {
         //ignore, JBoss Modules might not be available at all
         if (Log.LOGGER.isTraceEnabled()) {
            Log.LOGGER.couldNotGetModuleClassLoader(JBOSSWS_SPI_MODULE, e);
         }
      }
      return moduleClassLoader;
   }
   
   /**
    * Get context classloader.
    * 
    * @return the current context classloader
    */
   static ClassLoader getContextClassLoader()
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
