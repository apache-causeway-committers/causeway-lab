/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.causeway.incubator.viewer.vaadin.model.models;

import org.apache.causeway.core.metamodel.facets.object.icon.ObjectIcon;
import org.apache.causeway.wicketstubs.api.ResourceReference;

import java.io.Serializable;

/**
 * Ideally I'd like to move this to the <tt>org.apache.causeway.viewer.wicket.model.causeway</tt>
 * package, however to do so would break existing API (gmap3 has a dependency on this, for example).
 */
public interface ImageResourceCache extends Serializable {

    //ResourceReference resourceReferenceFor(ManagedObject adapter);

    //ResourceReference resourceReferenceForSpec(ObjectSpecification objectSpecification);

    ResourceReference resourceReferenceForObjectIcon(final ObjectIcon objectIcon);
    
}
