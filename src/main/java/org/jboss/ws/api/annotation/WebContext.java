/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
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
