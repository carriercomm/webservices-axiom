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

package org.apache.axiom.util.stax.dialect;

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

class NormalizingXMLOutputFactoryWrapper extends XMLOutputFactory {
    private final XMLOutputFactory parent;
    private final AbstractStAXDialect dialect;
    
    public NormalizingXMLOutputFactoryWrapper(XMLOutputFactory parent, AbstractStAXDialect dialect) {
        this.parent = parent;
        this.dialect = dialect;
    }

    public XMLEventWriter createXMLEventWriter(OutputStream stream, String encoding)
            throws XMLStreamException {
        return parent.createXMLEventWriter(stream, encoding);
    }

    public XMLEventWriter createXMLEventWriter(OutputStream stream) throws XMLStreamException {
        return parent.createXMLEventWriter(stream);
    }

    public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
        return parent.createXMLEventWriter(result);
    }

    public XMLEventWriter createXMLEventWriter(Writer stream) throws XMLStreamException {
        return parent.createXMLEventWriter(stream);
    }

    public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding)
            throws XMLStreamException {
        return dialect.normalize(parent.createXMLStreamWriter(stream, encoding));
    }

    public XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException {
        return dialect.normalize(parent.createXMLStreamWriter(stream));
    }

    public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
        return dialect.normalize(parent.createXMLStreamWriter(result));
    }

    public XMLStreamWriter createXMLStreamWriter(Writer stream) throws XMLStreamException {
        return dialect.normalize(parent.createXMLStreamWriter(stream));
    }

    public Object getProperty(String name) throws IllegalArgumentException {
        return parent.getProperty(name);
    }

    public boolean isPropertySupported(String name) {
        return parent.isPropertySupported(name);
    }

    public void setProperty(String name, Object value) throws IllegalArgumentException {
        parent.setProperty(name, value);
    }
}