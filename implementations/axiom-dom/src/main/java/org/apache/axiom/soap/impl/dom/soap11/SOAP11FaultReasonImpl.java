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

package org.apache.axiom.soap.impl.dom.soap11;

import org.apache.axiom.om.OMCloneOptions;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMXMLParserWrapper;
import org.apache.axiom.om.impl.dom.ParentNode;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPFault;
import org.apache.axiom.soap.SOAPFaultText;
import org.apache.axiom.soap.SOAPProcessingException;
import org.apache.axiom.soap.impl.dom.SOAPFaultReasonImpl;

public class SOAP11FaultReasonImpl extends SOAPFaultReasonImpl {
    public SOAP11FaultReasonImpl(ParentNode parentNode, OMNamespace ns, OMXMLParserWrapper builder,
            OMFactory factory, boolean generateNSDecl) {
        super(parentNode, ns, builder, factory, generateNSDecl);
    }

    /** @param parent  */
    public SOAP11FaultReasonImpl(SOAPFault parent, SOAPFactory factory)
            throws SOAPProcessingException {
        super(parent, false, factory);
    }

    public void addSOAPText(SOAPFaultText soapFaultText)
            throws SOAPProcessingException {
        throw new UnsupportedOperationException("addSOAPText() not allowed for SOAP 1.1!");
    }

    protected void checkParent(OMElement parent) throws SOAPProcessingException {
        if (!(parent instanceof SOAP11FaultImpl)) {
            throw new SOAPProcessingException(
                    "Expecting SOAP 1.1 implementation of SOAP Fault as the " +
                            "parent. But received some other implementation");
        }
    }

    public String getLocalName() {
        return SOAP11Constants.SOAP_FAULT_STRING_LOCAL_NAME;
    }

    public SOAPFaultText getFirstSOAPText() {
        throw new UnsupportedOperationException("getFirstSOAPText() not supported for SOAP 1.1!");
    }

    protected OMElement createClone(OMCloneOptions options, ParentNode targetParent,
            boolean generateNSDecl) {
        return new SOAP11FaultReasonImpl(targetParent, getNamespace(), null, getOMFactory(), generateNSDecl);
    }
}
