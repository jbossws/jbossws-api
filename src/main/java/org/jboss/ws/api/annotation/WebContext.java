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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides web context specific meta data to EJB based web service endpoints.
 *
 * @author <a href="mailto:thomas.diesler@jboss.org">Thomas Diesler</a>
 * @since 26-Apr-2005
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface WebContext {
   
   /** 
    * The contextRoot element specifies the context root that the web service endpoint is deployed to.
    * If it is not specified it will be derived from the deployment short name.
    * 
    * Applies to server side port components only
    *
    * @return  string
    */
   String contextRoot() default "";
   
   /** 
    * The virtual host that the web service endpoint is deployed to.
    * 
    * Applies to server side port components only.
    *
    * @return string
    */
   String virtualHost() default "";
   
   /** 
    * Relative path that is appended to the contextRoot to form fully qualified
    * endpoint address for the web service endpoint.
    * 
    * Applies to server side port components only.
    *
    * @return string
    */
   String urlPattern() default "";

   /**
    * The authMethod is used to configure the authentication mechanism for the web service. 
    * As a prerequisite to gaining access to any web service which are protected by an authorization
    * constraint, a user must have authenticated using the configured mechanism.
    *
    * Standard values for this element are "BASIC", or "CLIENT-CERT", custom authMethods may also
    * be specified.
    * 
    * @see AuthMethod
    *
    * @return string
    */
   String authMethod() default "";

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
    * @see TransportGuarantee
    *
    * @return string
    */
   String transportGuarantee() default "";

   /**
    * A secure endpoint does not secure wsdl access by default.
    * Explicitly setting secureWSDLAccess overrides this behaviour.
    * 
    * Protect access to WSDL. See http://jira.jboss.org/jira/browse/JBWS-723
    *
    * @return boolean
    */
   boolean secureWSDLAccess() default false;
   
   /**
    * Description of the resource being accessed
    *
    * @return string
    */
   String realmName() default ""; 
    
}
