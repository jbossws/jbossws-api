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
package org.jboss.ws.api.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
/**
 * Utility class to get resource bundle and i18n message
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 * 
 */
public final class BundleUtils {
   private static final String BUNDLE_FILE_NAME = ".Message";
   
   private BundleUtils() {
   }

   /**Get resource bundle with a class
    * @param cls the class to find the resource bundle
    * @return ResourceBundle if exists, otherwise null
    */
   public static ResourceBundle getBundle(Class<?> cls)
   {
      String bundleName = cls.getPackage().getName() + BUNDLE_FILE_NAME;
      try
      {
         return ResourceBundle.getBundle(bundleName, Locale.getDefault(), cls.getClassLoader());
      }
      catch (MissingResourceException ex)
      {
         return ResourceBundle.getBundle(bundleName, Locale.getDefault(), Thread.currentThread()
               .getContextClassLoader());

      }
   }

   /**
    * Get the i18n string message with an associated resource bundle
    * @param bundle resourceBundle
    * @param key    the key for the desired string
    * @param params the message substitution parameters
    * @return the formated string for the given key
    */
   public static String getMessage(ResourceBundle bundle, String key, Object... params)
   {
      String fmt = null;
      try
      {
         if (null == bundle)
         {
            return key;
         }
         fmt = bundle.getString(key);
      }
      catch (MissingResourceException ex)
      {
         return key;
      }
      return MessageFormat.format(fmt, params);
   }
}
