/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ws.api.monitoring;

import java.io.Serializable;
import java.util.List;

/**
 * Processes a record. A RecordProcessor may have filters to allow processing
 * of records matching given criteria. It also gives users fine management of
 * record's attributes to be processed. 
 * 
 * @author <a href="mailto:alessio.soldano@jboss.com">Alessio Soldano</a>
 * @since 8-Dec-2007
 */
public interface RecordProcessor extends Cloneable, Serializable
{
   /**
    * Returns the current RecordProcessor name
    * 
    * @return the name
    */
   String getName();

   /**
    * Sets the record processor name
    * 
    * @param name the name to set
    */
   void setName(String name);

   /**
    * Return true when the record processor is configured for actively recording data; false otherwise
    * 
    * @return a boolean specifying if the recording is on
    */
   boolean isRecording();

   /**
    * Enable / disable actual data recording
    * 
    * @param value true to enable recording, false to disable it
    */
   void setRecording(boolean value);

   /**
    * Processes a {@link org.jboss.ws.api.monitoring.Record} instance
    * 
    * @param record the {@link org.jboss.ws.api.monitoring.Record} to process
    */
   void processRecord(Record record);

   /**
    * Gets the currently configured {@link org.jboss.ws.api.monitoring.RecordFilter} instances
    * 
    * @return the currently configured {@link org.jboss.ws.api.monitoring.RecordFilter} instances
    */
   List<RecordFilter> getFilters();

   /**
    * Adds a {@link org.jboss.ws.api.monitoring.RecordFilter} to the processor
    * 
    * @param filter the {@link org.jboss.ws.api.monitoring.RecordFilter} instance to add
    */
   void addFilter(RecordFilter filter);

   /**
    * Sets the {@link org.jboss.ws.api.monitoring.RecordFilter} instances for the current processor
    * 
    * @param filters the {@link org.jboss.ws.api.monitoring.RecordFilter} instances to set
    */
   void setFilters(List<RecordFilter> filters);

   /**
    * Return whether the source host data are being processed
    * 
    * @return true if source host data are being processed, false otherwise
    */
   boolean isProcessSourceHost();

   /**
    * Enable / disable processing of source host
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessSourceHost(boolean value);

   /**
    * Return whether the destination host data are being processed
    * 
    * @return true if destination host data are being processed, false otherwise
    */
   boolean isProcessDestinationHost();

   /**
    * Enable / disable processing of destination host
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessDestinationHost(boolean value);

   /**
    * Return whether the message type data are being processed
    * 
    * @return true if message type data are being processed, false otherwise
    */
   boolean isProcessMessageType();

   /**
    * Enable / disable processing of message type
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessMessageType(boolean value);

   /**
    * Return whether the envelope data are being processed
    * 
    * @return true if envelope data are being processed, false otherwise
    */
   boolean isProcessEnvelope();

   /**
    * Enable / disable processing of envelope
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessEnvelope(boolean value);

   /**
    * Return whether the headers data are being processed
    * 
    * @return true if headers data are being processed, false otherwise
    */
   boolean isProcessHeaders();

   /**
    * Enable / disable processing of headers
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessHeaders(boolean value);

   /**
    * Return whether the operation data are being processed
    * 
    * @return true if operation data are being processed, false otherwise
    */
   boolean isProcessOperation();

   /**
    * Enable / disable processing of operation
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessOperation(boolean value);

   /**
    * Return whether the date data are being processed
    * 
    * @return true if date data are being processed, false otherwise
    */
   boolean isProcessDate();

   /**
    * Enable / disable processing of date
    * 
    * @param value true to enable processing, false to disable it 
    */
   void setProcessDate(boolean value);

   /**
    * RecordFilters must override Object.clone()
    */
   Object clone() throws CloneNotSupportedException;
}
