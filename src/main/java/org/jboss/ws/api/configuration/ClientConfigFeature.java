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
