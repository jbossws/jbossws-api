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

import org.jboss.ws.api.Messages;

/**
 * The transportGuarantee specifies that the communication
 * between client and server should be NONE, INTEGRAL, or
 * CONFIDENTIAL. NONE means that the application does not require any
 * transport guarantees. A value of INTEGRAL means that the application
 * requires that the data sent between the client and server be sent in
 * such a way that it can't be changed in transit. CONFIDENTIAL means
 * that the application requires that the data be transmitted in a
 * fashion that prevents other entities from observing the contents of
 * the transmission. In most cases, the presence of the INTEGRAL or
 * CONFIDENTIAL flag will indicate that the use of SSL is required.
 * 
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class TransportGuarantee
{
   /**
    * Application does not require any transport guarantees.
    */
   public static final String NONE = "NONE";
   /**
    * Application requires that the data sent between the client and
    * server be sent in such a way that it can't be changed in transit.
    */
   public static final String INTEGRAL = "INTEGRAL";
   /**
    * Application requires that the data be transmitted in a fashion that
    * prevents other entities from observing the contents of the transmission.
    */
   public static final String CONFIDENTIAL = "CONFIDENTIAL";

   /**
    * Forbidden constructor.
    */
   private TransportGuarantee()
   {
      super();
   }
   
   /**
    * Returns string representing correct {@link org.jboss.ws.api.annotation.TransportGuarantee} value.
    * @param s string to convert.
    * @return correct transport guarantee value
    * @throws IllegalArgumentException if <b>s</b> is <b>null</b> or contains unknown value.
    */
   public static String valueOf(final String s)
   {
      if (s != null)
      {
         if (s.equals(""))
         {
            return s;
         }
         if (s.equals(TransportGuarantee.NONE))
         {
            return TransportGuarantee.NONE;
         }
         if (s.equals(TransportGuarantee.INTEGRAL))
         {
            return TransportGuarantee.INTEGRAL;
         }
         if (s.equals(TransportGuarantee.CONFIDENTIAL))
         {
            return TransportGuarantee.CONFIDENTIAL;
         }
      }
      
      throw Messages.MESSAGES.illegalTransportGuarantee(s);
   }
   
}
