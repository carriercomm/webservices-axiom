/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ws.commons.soap.impl.llom;

import org.apache.ws.commons.om.OMElement;
import org.apache.ws.commons.om.OMNode;
import org.apache.ws.commons.om.OMXMLParserWrapper;
import org.apache.ws.commons.om.impl.OMNodeEx;
import org.apache.ws.commons.om.impl.OMOutputImpl;
import org.apache.ws.commons.om.impl.llom.OMDocumentImpl;
import org.apache.ws.commons.soap.SOAPEnvelope;
import org.apache.ws.commons.soap.SOAPMessage;
import org.apache.ws.commons.soap.SOAPProcessingException;

import javax.xml.stream.XMLStreamException;

public class SOAPMessageImpl extends OMDocumentImpl implements SOAPMessage {


    public SOAPMessageImpl() {
    }

    public SOAPMessageImpl(SOAPEnvelope envelope, OMXMLParserWrapper parserWrapper) {
        super(envelope, parserWrapper);
    }

    public SOAPMessageImpl(OMXMLParserWrapper parserWrapper) {
        super(parserWrapper);
    }


    public SOAPEnvelope getSOAPEnvelope() throws SOAPProcessingException {
        return (SOAPEnvelope) getOMDocumentElement();
    }

    public void setSOAPEnvelope(SOAPEnvelope envelope) throws SOAPProcessingException {
        super.addChild(envelope);
        this.documentElement = envelope;
    }

    public void setOMDocumentElement(OMElement rootElement) {
        throw new UnsupportedOperationException("This is not allowed. Use set SOAPEnvelope instead");
    }

    public void setFirstChild(OMNode firstChild) {
        throw new UnsupportedOperationException("This is not allowed. Use set SOAPEnvelope instead");
    }

    protected void serialize(OMOutputImpl omOutput, boolean cache, boolean includeXMLDeclaration) throws XMLStreamException {
        if (cache) {
            ((OMNodeEx)this.documentElement).serialize(omOutput);
        } else {
            ((OMNodeEx)this.documentElement).serializeAndConsume(omOutput);
        }
    }
}
