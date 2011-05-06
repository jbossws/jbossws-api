/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.spi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Load a service class using this ordered lookup procedure
 *
 * @author Thomas.Diesler@jboss.com
 * @author alessio.soldano@jboss.com
 * @since 14-Dec-2006
 */
public final class ServiceLoader
{
   /**
    * A synchronized weak hash map that keeps factory names retrieved using Service API (META-INF/services/*) for each classloader.
    * Weak keys are used to remove entries when classloaders are garbage collected; values are service-property-name -> factory name maps.
    */
   private static Map<ClassLoader, Map<String, String>> serviceMap = Collections.synchronizedMap(new WeakHashMap<ClassLoader, Map<String, String>>());
   
   /**
    * Constructor.
    */
   private ServiceLoader()
   {
       // forbidden instantiation
   }

   /**
    * This method uses the algorithm below using the JAXWS Provider as an example.
    * 
    * 1. If a resource with the name of META-INF/services/javax.xml.ws.spi.Provider exists, then
    * its first line, if present, is used as the UTF-8 encoded name of the implementation class.
    * 
    * 2. If the ${java.home}/lib/jaxws.properties file exists and it is readable by the 
    * java.util.Properties.load(InputStream) method and it contains an entry whose key is 
    * javax.xml.ws.spi.Provider, then the value of that entry is used as the name of the implementation class.
    * 
    * 3. If a system property with the name javax.xml.ws.spi.Provider is defined, then its value is used
    * as the name of the implementation class.
    * 
    * 4. Finally, a default implementation class name is used.
    * 
    * @param propertyName   The property name for the service to resolve
    * @param defaultFactory Default factory class name to be used when not able to resolve anything
    * @param cl             The classLoader to be used for loading resolved service
    * @return               A new instance of the required service
    */
   public static Object loadService(String propertyName, String defaultFactory, ClassLoader cl)
   {
      Object factory = loadFromServices(propertyName, cl);
      if (factory == null)
      {
         factory = loadFromPropertiesFile(propertyName, cl);
      }
      if (factory == null)
      {
         factory = loadFromSystemProperty(propertyName, defaultFactory, cl);
      }
      return factory;
   }
   
   /**
    * This method uses the algorithm below using the JAXWS Provider as an example.
    * 
    * 1. If a resource with the name of META-INF/services/javax.xml.ws.spi.Provider exists, then
    * its first line, if present, is used as the UTF-8 encoded name of the implementation class.
    * 
    * 2. If the ${java.home}/lib/jaxws.properties file exists and it is readable by the 
    * java.util.Properties.load(InputStream) method and it contains an entry whose key is 
    * javax.xml.ws.spi.Provider, then the value of that entry is used as the name of the implementation class.
    * 
    * 3. If a system property with the name javax.xml.ws.spi.Provider is defined, then its value is used
    * as the name of the implementation class.
    * 
    * 4. Finally, a default implementation class name is used.
    * 
    * This is equivalent to calling {@link loadService(String propertyName, String defaultFactory, ClassLoader cl)}
    * passing in the Thread.currentThread().getContextClassLoader().
    * 
    * @param propertyName   The property name for the service to resolve
    * @param defaultFactory Default factory class name to be used when not able to resolve anything
    * @return               A new instance of the required service
    */
   public static Object loadService(String propertyName, String defaultFactory)
   {
      return loadService(propertyName, defaultFactory, SecurityActions.getContextClassLoader());
   }
   
   /** Use the Services API (as detailed in the JAR specification), if available, to determine the classname.
    */
   private static Object loadFromServices(String propertyName, ClassLoader loader)
   {
      Object factory = null;
      String factoryName = null;
      
      // Use the Services API (as detailed in the JAR specification), if available, to determine the classname.
      String filename = "META-INF/services/" + propertyName;
      
      try
      {
         factoryName = getServiceNameUsingCache(loader, filename);
         if (factoryName != null)
         {
            Class<?> factoryClass = SecurityActions.loadClass(loader, factoryName);
            factory = factoryClass.newInstance();
         }
      }
      catch (Throwable t)
      {
         throw new IllegalStateException("Failed to load " + propertyName + ": " + factoryName, t);
      }
      
      return factory;
   }

   private static String getServiceNameUsingCache(ClassLoader loader, String filename) throws IOException
   {
      Map<String, String> map = serviceMap.get(loader);
      if (map != null && map.containsKey(filename))
      {
         return map.get(filename);
      }
      else
      {
         if (map == null)
         {
            map = new ConcurrentHashMap<String, String>();
            serviceMap.put(loader, map);
         }
         InputStream inStream = SecurityActions.getResourceAsStream(loader, filename);
         String factoryName = null;
         if (inStream != null)
         {
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            factoryName = br.readLine();
            br.close();
            map.put(filename, factoryName);
         }
         return factoryName;
      }
   }
   
   /** Use the system property
    */
   private static Object loadFromSystemProperty(String propertyName, String defaultFactory, ClassLoader loader)
   {
      Object factory = null;

      PrivilegedAction<String> action = new PropertyAccessAction(propertyName);
      String factoryName = AccessController.doPrivileged(action);
      if (factoryName != null)
      {
         try
         {
            Class<?> factoryClass = SecurityActions.loadClass(loader, factoryName);
            factory = factoryClass.newInstance();
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load " + propertyName + ": " + factoryName, t);
         }
      }

      // Use the default factory implementation class.
      if (factory == null && defaultFactory != null)
      {
         factory = loadDefault(defaultFactory, loader);
      }

      return factory;
   }

   /**
    * Use the properties file "${java.home}/lib/jaxws.properties" in the JRE directory.
    * This configuration file is in standard java.util.Properties format and contains the 
    * fully qualified name of the implementation class with the key being the system property defined above.
    */
   private static Object loadFromPropertiesFile(String propertyName, ClassLoader loader)
   {
      Object factory = null;
      String factoryName = null;

      // Use the properties file "lib/jaxm.properties" in the JRE directory.
      // This configuration file is in standard java.util.Properties format and contains the fully qualified name of the implementation class with the key being the system property defined above.
      PrivilegedAction<String> propertyReadAction = new PropertyAccessAction("java.home");
      String javaHome = AccessController.doPrivileged(propertyReadAction);
      File jaxmFile = new File(javaHome + "/lib/jaxws.properties");
      if ((Boolean)AccessController.doPrivileged(new PropertyFileExistAction(jaxmFile)))
      {
         try
         {
            PropertyFileAccessAction propertyFileAccessAction = new PropertyFileAccessAction(jaxmFile.getCanonicalPath());
            Properties jaxmProperties = AccessController.doPrivileged(propertyFileAccessAction);
            factoryName = jaxmProperties.getProperty(propertyName);
            if (factoryName != null)
            {
               Class<?> factoryClass = SecurityActions.loadClass(loader, factoryName);
               factory = factoryClass.newInstance();
            }
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load " + propertyName + ": " + factoryName, t);
         }
      }

      return factory;
   }

   private static Object loadDefault(String defaultFactory, ClassLoader loader)
   {
      Object factory = null;

      // Use the default factory implementation class.
      if (defaultFactory != null)
      {
         try
         {
            Class<?> factoryClass = SecurityActions.loadClass(loader, defaultFactory);
            factory = factoryClass.newInstance();
         }
         catch (Throwable t)
         {
            throw new IllegalStateException("Failed to load: " + defaultFactory, t);
         }
      }

      return factory;
   }

   private static class PropertyAccessAction implements PrivilegedAction<String>
   {
      private String name;

      PropertyAccessAction(String name)
      {
         this.name = name;
      }

      public String run()
      {
         return System.getProperty(name);
      }
   }

   private static class PropertyFileAccessAction implements PrivilegedAction<Properties>
   {
      private String filename;

      PropertyFileAccessAction(String filename)
      {
         this.filename = filename;
      }

      public Properties run()
      {
         InputStream inStream = null;
         try
         {
            inStream = new FileInputStream(filename);
            Properties props = new Properties();
            props.load(inStream);
            return props;
         }
         catch (IOException ex)
         {
            throw new SecurityException("Cannot load properties: " + filename, ex);
         }
         finally
         {
            try
            {
               if (inStream != null)
               {
                  inStream.close();
               }
            }
            catch (Exception ignore) {}
         }
      }
   }
   
   private static class PropertyFileExistAction implements PrivilegedAction<Boolean>
   {
      private File file;

      PropertyFileExistAction(File file)
      {
         this.file = file;
      }

      public Boolean run()
      {
         return file.exists();
      }
   }
}
