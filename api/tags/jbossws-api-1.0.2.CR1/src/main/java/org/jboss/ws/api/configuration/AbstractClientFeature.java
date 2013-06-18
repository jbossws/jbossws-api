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

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;

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
    * @param obj
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
    * @param bp
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
