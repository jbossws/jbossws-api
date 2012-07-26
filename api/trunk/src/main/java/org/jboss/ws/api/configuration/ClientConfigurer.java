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

import javax.xml.ws.BindingProvider;

/**
 * JBossWS Client Configuration interface
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public interface ClientConfigurer
{
   /**
    * Reads a client configuration and setups the handlers in the provided BindingProvider accordingly.
    * 
    * @param bp             The BindingProvider instance to setup
    * @param configFile     The configuration file
    * @param configName     The configuration name
    */
   public void setConfigHandlers(BindingProvider bp, String configFile, String configName);
   
   /**
    * Reads a client configuration and setups the properties in the provided client proxy accordingly.
    * 
    * @param proxy          The client proxy (port)
    * @param configFile     The configuration file
    * @param configName     The configuration name
    */
   public void setConfigProperties(Object proxy, String configFile, String configName);

}
