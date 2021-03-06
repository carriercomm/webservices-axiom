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

package org.apache.axiom.om.impl.dom.factory;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLParserWrapper;
import org.apache.axiom.om.dom.DOMMetaFactory;
import org.apache.axiom.om.impl.common.factory.AbstractOMMetaFactory;
import org.apache.axiom.om.impl.dom.DOMImplementationImpl;
import org.apache.axiom.om.impl.dom.jaxp.DOOMDocumentBuilderFactory;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPMessage;
import org.apache.axiom.soap.impl.dom.SOAPMessageImpl;
import org.apache.axiom.soap.impl.dom.soap11.SOAP11Factory;
import org.apache.axiom.soap.impl.dom.soap12.SOAP12Factory;
import org.w3c.dom.DOMImplementation;

/**
 * Meta factory for the DOOM implementation.
 */
public class OMDOMMetaFactory extends AbstractOMMetaFactory implements DOMMetaFactory {
    // This singleton is necessary to support the no-arg constructor in DOOMDocumentBuilderFactory.
    // Since that method is deprecated, this singleton should disappear in Axiom 1.3.
    public static final OMDOMMetaFactory INSTANCE = new OMDOMMetaFactory();
    
    private final OMFactory omFactory = new OMDOMFactory(this);
    private final SOAPFactory soap11Factory = new SOAP11Factory(this);
    private final SOAPFactory soap12Factory = new SOAP12Factory(this);
    private final DOMImplementation domImplementation = new DOMImplementationImpl(omFactory);
    
    public OMFactory getOMFactory() {
        return omFactory;
    }

    public SOAPFactory getSOAP11Factory() {
        return soap11Factory;
    }

    public SOAPFactory getSOAP12Factory() {
        return soap12Factory;
    }

    public SOAPMessage createSOAPMessage(OMXMLParserWrapper builder) {
        return new SOAPMessageImpl(builder, null);
    }

    public DocumentBuilderFactory newDocumentBuilderFactory() {
        return new DOOMDocumentBuilderFactory(omFactory);
    }

    public DOMImplementation getDOMImplementation() {
        return domImplementation;
    }
}
