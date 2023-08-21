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
package org.jboss.ws.api.util;

import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * Security actions for this package
 * 
 * @author alessio.soldano@jboss.com
 * @since 19-Jun-2009
 *
 */
class SecurityActions
{
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
   
   /**
    * Set context classloader.
    * 
    */
   static void setContextClassLoader(final ClassLoader cl)
   {
      SecurityManager sm = System.getSecurityManager();
      if (sm == null)
      {
         Thread.currentThread().setContextClassLoader(cl);
      }
      else
      {
         AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run()
            {
               Thread.currentThread().setContextClassLoader(cl);
               return null;
            }
         });
      }
   }
   
   /**
    * Get resource as stream
    * 
    * @param cl
    * @param filename
    * @return input stream
    * @throws PrivilegedActionException
    */
   static InputStream getResourceAsStream(final ClassLoader cl, final String filename)
   {
      SecurityManager sm = System.getSecurityManager();
      if (sm == null)
      {
         return cl.getResourceAsStream(filename);
      }
      else
      {
         return AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run()
            {
               return cl.getResourceAsStream(filename);
            }
         });
      }
   }
   
   /**
    * Load a class using the provided classloader
    * 
    * @param name
    * @return
    * @throws PrivilegedActionException
    */
   static Class<?> loadClass(final ClassLoader cl, final String name) throws PrivilegedActionException, ClassNotFoundException
   {
      SecurityManager sm = System.getSecurityManager();
      if (sm == null)
      {
         return cl.loadClass(name);
      }
      else
      {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>() {
            public Class<?> run() throws PrivilegedActionException
            {
               try
               {
                  return cl.loadClass(name);
               }
               catch (Exception e)
               {
                  throw new PrivilegedActionException(e);
               }
            }
         });
      }
   }
}
