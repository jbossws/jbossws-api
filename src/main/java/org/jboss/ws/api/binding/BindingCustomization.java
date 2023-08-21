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
package org.jboss.ws.api.binding;

import java.util.HashMap;

/**
 * Allows introduction of arbitrary binding customization properties.<p>
 * This may be different between stacks and addresses meta data binding
 * (i.e JSR-181 to UnifiedMetaData) as well as JAVA to XML binding operations.
 * <p>
 * Supported properties need to be documented in subclasses.
 *
 * @author <a href="mailto:Heiko.Braun@jboss.com">Heiko Braun</a>
 * @since Jun 28, 2007
 */
public abstract class BindingCustomization extends HashMap
{

}
