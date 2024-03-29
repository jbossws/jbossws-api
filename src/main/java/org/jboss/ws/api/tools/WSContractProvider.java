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

import java.io.File;
import java.io.PrintStream;

import org.jboss.ws.api.util.ServiceLoader;

/**
 * WSContractProvider is responsible for generating the required portable
 * JAX-WS artifacts for a service endpoint implementation. This includes class
 * files for wrapper types and fault beans. WSDL may be optionally generated as
 * well using this API.
 *
 * <p>The following example generates class files, source files and WSDL for an
 * endpoint:</p> 
 * <pre>
 * WSContractProvider provider = WSContractProvider.newInstance();
 * provider.setGenerateSource(true);
 * provider.setGenerateWsdl(true);
 * provider.setOutputDirectory(new File("output"));
 * provider.setMessageStream(System.out);
 * provider.provide(TestMe.class);
 * </pre>
 * 
 * <p>Thread-Safety:</p>
 * This class expects to be thread-confined, so it can not be shared between threads.
 *
 * @author <a href="mailto:jason.greene@jboss.com">Jason T. Greene</a>
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public abstract class WSContractProvider
{
   private static String DEFAULT_PROVIDER = "org.jboss.wsf.stack.cxf.tools.CXFProviderFactoryImpl";
   public static final String PROVIDER_PROPERTY = "org.jboss.ws.api.tools.ProviderFactory";

   protected WSContractProvider()
   {

   }

   /**
    * Obtain a new instance of a {@link WSContractProvider}. This will use the current
    * thread's context class loader to locate the {@link WSContractProviderFactory}
    * implementation.
    * 
    * @return a new {@link WSContractProvider}
    */
   public static WSContractProvider newInstance()
   {
      return newInstance(SecurityActions.getContextClassLoader());
   }

   /**
    * Obtain a new instance of a {@link WSContractProvider}. The specified ClassLoader will be used to
    * locate the {@link WSContractProviderFactory} implementation
    * 
    * @param loader the ClassLoader to use
    * @return a new {@link WSContractProvider}
    */
   public static WSContractProvider newInstance(ClassLoader loader)
   {
      ClassLoader oldLoader = SecurityActions.getContextClassLoader();
      try
      {
         SecurityActions.setContextClassLoader(loader);
         WSContractProviderFactory factory = (WSContractProviderFactory) ServiceLoader.loadService(PROVIDER_PROPERTY, DEFAULT_PROVIDER);
         return factory.createProvider(loader);
      }
      finally
      {
         SecurityActions.setContextClassLoader(oldLoader);
      }
   }

   /**
    * Enables/Disables WSDL generation.
    * 
    * @param generateWsdl whether or not to generate WSDL
    */
   public abstract void setGenerateWsdl(boolean generateWsdl);
   
   /**
    * Enables/Disables SOAP 1.2 binding extension
    * 
    * @param extension whether or not to enable SOAP 1.2 binding extension
    */
   public abstract void setExtension(boolean extension);

   /**
    * Enables/Disables Java source generation.
    * 
    * @param generateSource whether or not to generate Java source.
    */
   public abstract void setGenerateSource(boolean generateSource);

   /**
    * Sets the main output directory. If the directory does not exist, it will be created.
    * 
    * @param directory the root directory for generated files
    */
   public abstract void setOutputDirectory(File directory);

   /**
    * Sets the resource directory. This directory will contain any generated
    * WSDL and XSD files. If the directory does not exist, it will be created.
    * If not specified, the output directory will be used instead.
    * 
    * @param directory the root directory for generated resource files
    */
   public abstract void setResourceDirectory(File directory);

   /**
    * Sets the source directory. This directory will contain any generated Java source.
    * If the directory does not exist, it will be created. If not specified, 
    * the output directory will be used instead.
    * 
    * @param directory the root directory for generated source code
    */
   public abstract void setSourceDirectory(File directory);
   
   /**
    * Sets the soap:address to be used for the generated port in the wsdl.
    * This is ignored if WSDL generation is disabled.
    * 
    * @param address  soap address
    */
   public abstract void setPortSoapAddress(String address);

   /**
    * Sets the ClassLoader used to discover types. This defaults to the one used
    * in instantiation.
    * 
    * @param loader the ClassLoader to use
    */
   public abstract void setClassLoader(ClassLoader loader);

   /**
    * Generates artifacts using the current settings. This method may be invoked
    * more than once (e.g. multiple endpoints).
    * 
    * @param endpointClass the name of the endpoint implementation bean
    * @throws RuntimeException if any error occurs during processing, or the class is not found
    */
   public abstract void provide(String endpointClass);

   /**
    * Generates artifacts using the current settings. This method may be invoked
    * more than once (e.g. multiple endpoints).
    * 
    * @param endpointClass the endpoint implementation bean
    * @throws RuntimeException if any error occurs during processing
    */
   public abstract void provide(Class<?> endpointClass);

   /**
    * Sets the PrintStream to use for status feedback. The simplest example
    * would be to use System.out.
    * 
    * <p>Example output:</p> 
    * <pre>
    * Generating WSDL: 
    * TestMeService.wsdl 
    * Writing Source:
    * org/jboss/ws/tools/jaxws/TestMe.java
    * org/jboss/ws/tools/jaxws/TestMeResponse.java 
    * Writing Classes:
    * org/jboss/ws/tools/jaxws/TestMe.class
    * org/jboss/ws/tools/jaxws/TestMeResponse.class
    * </pre>
    * @param messageStream  the stream to use for status messages:
    */
   public abstract void setMessageStream(PrintStream messageStream);
}
