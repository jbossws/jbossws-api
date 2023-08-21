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
package org.jboss.ws.api.tools;

/**
 * Creates {@link WSContractProvider} implementations.
 * 
 * @author <a href="mailto:jason.greene@jboss.com">Jason T. Greene</a>
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public interface WSContractProviderFactory
{
   /**
    * Create a new {@link WSContractProvider}. There are no restrictions on how this
    * should be performed. The passed ClassLoader is the one used in
    * {@link WSContractProvider#newInstance(ClassLoader)}. This loader
    * should be made available to the generated {@link WSContractProvider}.
    * 
    * @param loader the ClassLoader for type discovery
    * @return a new {@link WSContractProvider}
    */
   WSContractProvider createProvider(ClassLoader loader);
}
