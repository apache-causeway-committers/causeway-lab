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
package org.apache.causeway.incubator.viewer.vaadin.ui.util;

import org.apache.causeway.wicketstubs.IRequestListener;
import org.apache.causeway.wicketstubs.Link;
import org.apache.causeway.wicketstubs.api.IResource;
import org.apache.causeway.wicketstubs.api.IResource.Attributes;
import org.apache.causeway.wicketstubs.api.PageParameters;
import org.apache.causeway.wicketstubs.api.RequestCycle;

import java.util.UUID;

/**
 * Each ResourceLinkVolatile instance generates a unique URL, which
 * effectively eliminates any caching during the request response cycle.
 * <p>
 * This is the desired behavior for Blob/Clob 'download' buttons.
 *
 * @since 2.0
// * @implNote this is almost a copy of Wicket's {@link ResourceLink}.
 */
public class ResourceLinkVolatile extends Link<Object> implements IRequestListener {
    private static final long serialVersionUID = 1L;

    /** The Resource */
    private final IResource resource;

    /** The resource parameters */
    private final PageParameters resourceParameters;

    /**
     * Constructs a link directly to the provided resource.
     *
     * @param id       See Component
     * @param resource The resource
     */
    public ResourceLinkVolatile(final String id, final IResource resource) {
        super(id);
        this.resource = resource;
        this.resourceParameters = new PageParameters()
                .add("antiCache", UUID.randomUUID().toString());
    }

    @Override
    public void onClick() {
    }

    @Override
    public boolean rendersPage() {
        return false;
    }

    @Override
    protected boolean getStatelessHint() {
        return false;
    }

    @Override
    protected final CharSequence getURL() {
        return urlForListener(resourceParameters);
    }

    private CharSequence urlForListener(PageParameters resourceParameters) {
        return null;
    }

    @Override
    public final void onRequest() {
        Attributes a = new Attributes(RequestCycle.get().getRequest(), RequestCycle.get().getResponse(), null);
        resource.respond(a);

        super.onRequest();
    }
}
