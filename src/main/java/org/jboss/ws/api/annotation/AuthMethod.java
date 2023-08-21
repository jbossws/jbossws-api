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
package org.jboss.ws.api.annotation;

import org.jboss.ws.api.Log;
import org.jboss.ws.api.Messages;

/**
 * The authMethod is used to configure the authentication mechanism for the web service. 
 * As a prerequisite to gaining access to any web service which are protected by an 
 * authorization constraint, a user must have authenticated using the configured mechanism.
 * 
 * @author <a href="ropalka@redhat.com">Richard Opalka</a>
 */
public final class AuthMethod
{
   /**
    * Basic authentication.
    */
   public static final String BASIC = "BASIC";
   /**
    * Client certificate based authentication.
    */
   public static final String CLIENT_CERT = "CLIENT-CERT";
   
   /**
    * Forbidden constructor.
    */
   private AuthMethod()
   {
      super();
   }

   /**
    * Returns string representing correct {@link org.jboss.ws.api.annotation.AuthMethod} value.
    * @param s string to convert.
    * @return correct auth method value
    * @throws IllegalArgumentException if <b>s</b> is <b>null</b>.
    */
   public static String valueOf(final String s)
   {
      if (s != null)
      {
         if (s.equals(""))
         {
            return s;
         }
         if (s.equals(AuthMethod.BASIC))
         {
            return AuthMethod.BASIC;
         }
         if (s.equals(AuthMethod.CLIENT_CERT))
         {
            return AuthMethod.CLIENT_CERT;
         }
         Log.LOGGER.nonStandardMethod(s);
         return s;         
      }
      
      throw Messages.MESSAGES.illegalAuthMethod(s);
   }
   
}
