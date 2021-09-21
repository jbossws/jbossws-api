/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
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

import jakarta.xml.ws.BindingProvider;

/**
 * A JBoss client JAXWS feature that setup a JBossWS predefined client config
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public final class ClientConfigFeature extends AbstractClientFeature
{
   private ClientConfigurer configurer;

   private String configFile;

   private String configName;
   
   private boolean configureProperties;

   public ClientConfigFeature()
   {
      super(ClientConfigFeature.class.getName());
      this.configurer = ClientConfigUtil.resolveClientConfigurer();
   }
   
   /**
    * Creates a feature for initializing the JAXWS client using
    * the specified predefined configuration.
    * 
    * @param configFile     The config file to read the config from;
    *                       null if the config is meant to be read
    *                       from the AS model.
    * @param configName     The config name to read.
    */
   public ClientConfigFeature(String configFile, String configName) {
      this(configFile, configName, false);
   }
   
   /**
    * Creates a feature for initializing the JAXWS client using
    * the specified predefined configuration.
    * 
    * @param configFile     The config file to read the config from;
    *                       null if the config is meant to be read
    *                       from the AS model.
    * @param configName     The config name to read.
    * @param configureProperties    Whether or not to set properties
    *                       from the specified config (default false)
    */
   public ClientConfigFeature(String configFile, String configName, boolean configureProperties) {
      this();
      this.configFile = configFile;
      this.configName = configName;
      this.configureProperties = configureProperties;
   }

   public void initialize(Object obj)
   {
      super.initialize(obj);
      if (configureProperties) {
         configurer.setConfigProperties(obj, configFile, configName);
      }
   }

   protected void initializeBindingProvider(BindingProvider bp)
   {
      configurer.setConfigHandlers(bp, configFile, configName);
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public void setConfigFile(String configFile)
   {
      this.configFile = configFile;
   }

   public String getConfigName()
   {
      return configName;
   }

   public void setConfigName(String configName)
   {
      this.configName = configName;
   }

   public boolean isConfigureProperties()
   {
      return configureProperties;
   }

   public void setConfigureProperties(boolean configureProperties)
   {
      this.configureProperties = configureProperties;
   }

}
