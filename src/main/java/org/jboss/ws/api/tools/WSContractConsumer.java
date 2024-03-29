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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jboss.ws.api.util.ServiceLoader;

/**
 * WSContractConsumer is responsible for generating JAX-WS client and server
 * artifacts from the specified WSDL file. To implement a client, one would use
 * the generated ___Service.java file. For a server, one only needs to provide
 * an implementation class that implements the generated service endpoint
 * interface.
 * 
 * @author <a href="mailto:jason.greene@jboss.com">Jason T. Greene</a>
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public abstract class WSContractConsumer
{
   private static String DEFAULT_PROVIDER = "org.jboss.wsf.stack.cxf.tools.CXFConsumerFactoryImpl";
   public static final String PROVIDER_PROPERTY = "org.jboss.ws.api.tools.ConsumerFactory";

   /**
    * Obtain a new instance of a {@link WSContractConsumer}. This will use the current
    * thread's context class loader to locate the {@link WSContractConsumerFactory}
    * implementation.
    *
    * @return a new {@link WSContractConsumer} instance
    */
   public static WSContractConsumer newInstance()
   {
      return newInstance(SecurityActions.getContextClassLoader());
   }

   /**
    * Obtain a new instance of a {@link WSContractConsumer}. The specified ClassLoader will be used to
    * locate the {@link WSContractConsumerFactory} implementation
    *
    * @param loader the ClassLoader to use
    * @return a new {@link WSContractConsumer} instance
    */
   public static WSContractConsumer newInstance(ClassLoader loader)
   {
      ClassLoader oldLoader = SecurityActions.getContextClassLoader();
      try
      {
         SecurityActions.setContextClassLoader(loader);
         WSContractConsumerFactory factory = (WSContractConsumerFactory) ServiceLoader.loadService(PROVIDER_PROPERTY, DEFAULT_PROVIDER);
         return factory.createConsumer();
      }
      finally
      {
         SecurityActions.setContextClassLoader(oldLoader);
      }
   }

   /**
    * Specifies the JAX-WS and JAXB binding files to use on import operations.
    *
    * @param bindingFiles list of JAX-WS or JAXB binding files
    */
   public abstract void setBindingFiles(List<File> bindingFiles);

   /**
    * Sets the OASIS XML Catalog file to use for entity resolution.
    *
    * @param catalog the OASIS XML Catalog file
    */
   public abstract void setCatalog(File catalog);

   /**
    * Sets the main output directory. If the directory does not exist, it will be created.
    *
    * @param directory the root directory for generated files
    */
   public abstract void setOutputDirectory(File directory);

   /**
    * Sets the source directory. This directory will contain any generated Java source.
    * If the directory does not exist, it will be created. If not specified,
    * the output directory will be used instead.
    *
    * @param directory the root directory for generated source code
    */
   public abstract void setSourceDirectory(File directory);

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
    * Enables/Disables Java source compilation.
    *
    * @param nocompile whether or not to compile Java source.
    */
   public abstract void setNoCompile(boolean nocompile);
   
   /**
    * Sets the target package for generated source. If not specified the default
    * is based off of the XML namespace.
    *
    * @param targetPackage the target package for generated source
    */
   public abstract void setTargetPackage(String targetPackage);

   /**
    * Sets the @@WebService.wsdlLocation and @@WebServiceClient.wsdlLocation attributes to a custom value.
    *
    * @param wsdlLocation the custom WSDL location to use in generated source
    */
   public abstract void setWsdlLocation(String wsdlLocation);
   
   
   /**
    * Sets the charset encoding to be used for generating sources
    *  
    * @param encoding the charset encoding to be used for generating sources
    */
   public abstract void setEncoding(String encoding);

   /**
    * Sets the PrintStream to use for status feedback. The simplest example
    * would be to use System.out.
    *
    * @param messageStream  the stream to use for status messages:
    */
   public abstract void setMessageStream(PrintStream messageStream);

   /**
    * Sets the additional classpath to use if/when invoking the Java compiler.
    * Typically an implementation will use the system <code>java.class.path</code>
    * property. So for most normal applications this method is not needed. However,
    * if this API is being used from an isolated classloader, then it needs to
    * be called in order to reference all jars that are required by the
    * implementation.
    *
    * @param classPath a list of strings where each entry references a
    *                  single jar or directory
    */
   public abstract void setAdditionalCompilerClassPath(List<String> classPath);

   /**
    * Enables or disables processing of implicit SOAP headers (i.e. SOAP headers
    * defined in the wsdl:binding but not wsdl:portType section.) Default is false. 
    * 
    * @param additionalHeaders a boolean enabling processing of implicit SOAP headers
    */
   public abstract void setAdditionalHeaders(boolean additionalHeaders);
   
   /**
    * Set the target JAX-WS specification target. Allowed values are 2.0, 2.1 and 2.2
    * @param target  the JAX-WS specification version.
    */
   public abstract void setTarget(String target);
   
   
   /**
    * Set the clientjar file name 
    * @param clientJar  client Jar file name 
    */
   public abstract void setClientJar(File clientJar);

   /**
    * Generate the required artifacts using the specified WSDL URL. This method
    * may be called more than once, although this is probably not desireable
    * 
    * @param wsdl the URL of the WSDL
    */
   public abstract void consume(URL wsdl);

   /**
    * Generate the required artifacts using the specified WSDL. This method
    * may be called more than once, although this is probably not desireable.
    * The passed string is expect to either be a valid URL, or a local file path.
    *
    * @param wsdl a URL or local file path
    * @throws MalformedURLException if wsdl is not a legal URL or local file
    */
   public void consume(String wsdl) throws MalformedURLException
   {
      URL url = null;
      try
      {
         url = new URL(wsdl);
      }
      catch (MalformedURLException e)
      {
         File file = new File(wsdl);
         url = file.toURI().toURL();
      }

      consume(url);
   }
}
