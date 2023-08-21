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
 * Defines an endpoint configuration.
 * This annotation is valid on an endpoint implementation bean or a SEI.
 * 
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface EndpointConfig {

   /**
    * The configName gives the configuration name that must be present in the configuration given by configFile.
    * If configName is not specified, the standard endpoint configuration is used.
    *
    * @return string
    */
   String configName() default "";

   /**
    * The configFile element is a URL or resource name for the configuration.
    * The default value references the current AS configuration.
    *
    * @return  string
    */
   String configFile() default "";
}
