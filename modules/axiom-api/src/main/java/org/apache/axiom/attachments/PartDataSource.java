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
package org.apache.axiom.attachments;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.axiom.ext.activation.SizeAwareDataSource;

/**
 * Default {@link DataSource} implementation for MIME parts. This implementation will be used if
 * there is no {@link DataSource} implementation specific to the buffering strategy being used, i.e.
 * if {@link PartContent#getDataSource(String)} returns <code>null</code>.
 */
class PartDataSource implements SizeAwareDataSource {
    private final PartImpl part;

    public PartDataSource(PartImpl part) {
        this.part = part;
    }

    public String getContentType() {
        return part.getDataSourceContentType();
    }

    public InputStream getInputStream() throws IOException {
        return part.getInputStream(true);
    }

    public String getName() {
        return part.getContentID();
    }

    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    public long getSize() {
        return part.getSize();
    }
}
