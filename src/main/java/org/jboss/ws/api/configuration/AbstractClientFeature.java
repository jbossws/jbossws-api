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
import jakarta.xml.ws.WebServiceFeature;

/**
 * Abstract JBoss client JAXWS feature
 *
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
public abstract class AbstractClientFeature extends WebServiceFeature
{
   private String id;
   
   protected AbstractClientFeature(String id)
   {
      this.id = id;
   }

   /**
    * Initializes the provided JAXWS client component; this is called by the
    * the JBossWS impl of JAXWS Provider.
    * 
    * @param obj  client component
    */
   public void initialize(Object obj)
   {
      if (obj instanceof BindingProvider)
      {
         this.initializeBindingProvider((BindingProvider) obj);
      }
   }

   /**
    * Initializes the provided BindingProvider instance 
    * 
    * @param bp   binding provider
    */
   protected void initializeBindingProvider(BindingProvider bp)
   {

   }

   @Override
   public String getID()
   {
      return id;
   }
}
