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
package org.jboss.ws.api.monitoring;

import static org.jboss.ws.api.Log.LOGGER;

import java.util.Stack;

/**
 * Associates the record group ID with the current thread. This is implemented internally
 * through a static ThreadLocal member. 
 * 
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @since 8-Dec-2007
 */
public class RecordGroupAssociation
{
   private static ThreadLocal<Stack<String>> groupIDAssoc = new ThreadLocal<Stack<String>>();
   
   /**
    * Associates the specified groupID to the current thread
    * 
    * @param groupID the groupID to associate to the current thread
    */
   public static void pushGroupID(String groupID)
   {
      if (LOGGER.isTraceEnabled()) LOGGER.pushGroupID(groupID, Thread.currentThread().getName());
      Stack<String> stack = groupIDAssoc.get();
      if (stack == null)
      {
         stack = new Stack<String>();
         groupIDAssoc.set(stack);
      }
      stack.push(groupID);
   }

   /**
    * Returns the groupID currently associated to the current thread
    * 
    * @return the current thread groupID
    */
   public static String peekGroupID()
   {
      String groupID = null;
      Stack<String> stack = groupIDAssoc.get();
      if (stack != null && stack.isEmpty() == false)
      {
         groupID = stack.peek();
      }
      if (LOGGER.isTraceEnabled()) LOGGER.peekGroupID(groupID, Thread.currentThread().getName());
      return groupID;
   }

   /**
    * Returns the groupID for the current thread and removes the association.
    * 
    * @return thre current thread groupID
    */
   public static String popGroupID()
   {
      String groupID = null;
      Stack<String> stack = groupIDAssoc.get();
      if (stack != null && stack.isEmpty() == false)
      {
         groupID = stack.pop();
      }
      if (LOGGER.isTraceEnabled()) LOGGER.popGroupID(groupID, Thread.currentThread().getName());
      return groupID;
   }
   
}
