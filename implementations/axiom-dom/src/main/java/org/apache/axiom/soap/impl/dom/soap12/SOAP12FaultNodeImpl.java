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

package org.apache.axiom.soap.impl.dom.soap12;

import org.apache.axiom.om.OMCloneOptions;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMXMLParserWrapper;
import org.apache.axiom.om.impl.dom.ParentNode;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPFault;
import org.apache.axiom.soap.SOAPFaultNode;
import org.apache.axiom.soap.SOAPProcessingException;
import org.apache.axiom.soap.impl.dom.SOAPElement;

public class SOAP12FaultNodeImpl extends SOAPElement implements SOAPFaultNode {
    public SOAP12FaultNodeImpl(SOAPFault parent, SOAPFactory factory)
            throws SOAPProcessingException {
        super(parent, SOAP12Constants.SOAP_FAULT_NODE_LOCAL_NAME, true, factory);
    }

    public SOAP12FaultNodeImpl(ParentNode parentNode, OMNamespace ns, OMXMLParserWrapper builder,
            OMFactory factory, boolean generateNSDecl) {
        super(parentNode, SOAP12Constants.SOAP_FAULT_NODE_LOCAL_NAME, ns, builder, factory, generateNSDecl);
    }

    protected void checkParent(OMElement parent) throws SOAPProcessingException {
        if (!(parent instanceof SOAP12FaultImpl)) {
            throw new SOAPProcessingException(
                    "Expecting SOAP 1.2 implementation of SOAP Fault as the " +
                            "parent. But received some other implementation");
        }
    }

    public void setFaultNodeValue(String uri) {
        this.setText(uri);
    }

    public String getFaultNodeValue() {
        return this.getText();
    }

    public void setNodeValue(String uri) {
        setFaultNodeValue(uri);
    }

    public String getNodeValue() {
        return getFaultNodeValue();
    }

    protected OMElement createClone(OMCloneOptions options, ParentNode targetParent,
            boolean generateNSDecl) {
        return new SOAP12FaultNodeImpl(targetParent, getNamespace(), null, getOMFactory(), generateNSDecl);
    }
}
