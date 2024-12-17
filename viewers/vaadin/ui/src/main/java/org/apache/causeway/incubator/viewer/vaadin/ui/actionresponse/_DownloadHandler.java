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
package org.apache.causeway.incubator.viewer.vaadin.ui.actionresponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.wicketstubs.AbstractResourceStream;
import org.apache.causeway.wicketstubs.ContentDisposition;
import org.apache.causeway.wicketstubs.IRequestHandler;
import org.apache.causeway.wicketstubs.IResourceStream;
import org.apache.causeway.wicketstubs.ResourceStreamNotFoundException;
import org.apache.causeway.wicketstubs.ResourceStreamRequestHandler;
import org.apache.causeway.wicketstubs.StringResourceStream;

import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
final class _DownloadHandler {

    public IRequestHandler downloadHandler(
            final ObjectAction action,
            final Object value) {
        if (value instanceof Clob) {
            val clob = (Clob) value;
            return handlerFor(action, resourceStreamFor(clob), clob);
        }
        if (value instanceof Blob) {
            val blob = (Blob) value;
            return handlerFor(action, resourceStreamFor(blob), blob);
        }
        return null;
    }

    // -- HELPER

    private IResourceStream resourceStreamFor(final Blob blob) {
        final IResourceStream resourceStream = new AbstractResourceStream() {

            private static final long serialVersionUID = 1L;

            // @Override
            public InputStream getInputStream() throws ResourceStreamNotFoundException {
                return new ByteArrayInputStream(blob.getBytes());
            }

            //@Override
            public String getContentType() {
                return blob.getMimeType().toString();
            }

            //@Override
            public void close() throws IOException {
            }
        };
        return resourceStream;
    }

    private IResourceStream resourceStreamFor(final Clob clob) {
        return new StringResourceStream(clob.getChars(), clob.getMimeType().toString());
    }

    private IRequestHandler handlerFor(
            final ObjectAction action,
            final IResourceStream resourceStream,
            final NamedWithMimeType namedWithMimeType) {
        return null;
    }

    private static IRequestHandler enforceNoCacheOnClientSide(ResourceStreamRequestHandler handler) {
        return null;
    }

    // -- CLIENT SIDE CACHING ASPECTS ...

    private static IRequestHandler enforceNoCacheOnClientSide(final IRequestHandler downloadHandler) {
        if (downloadHandler == null) {
            return downloadHandler;
        }
        if (downloadHandler instanceof ResourceStreamRequestHandler)
            ((ResourceStreamRequestHandler) downloadHandler)
                    .setCacheDuration(Duration.ZERO);

        return downloadHandler;
    }

}
