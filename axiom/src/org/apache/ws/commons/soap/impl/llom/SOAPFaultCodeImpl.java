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

import org.apache.ws.commons.om.OMNamespace;
import org.apache.ws.commons.om.OMXMLParserWrapper;
import org.apache.ws.commons.om.util.ElementHelper;
import org.apache.ws.commons.soap.SOAP12Constants;
import org.apache.ws.commons.soap.SOAPFactory;
import org.apache.ws.commons.soap.SOAPFault;
import org.apache.ws.commons.soap.SOAPFaultCode;
import org.apache.ws.commons.soap.SOAPFaultSubCode;
import org.apache.ws.commons.soap.SOAPFaultValue;
import org.apache.ws.commons.soap.SOAPProcessingException;

public abstract class SOAPFaultCodeImpl extends SOAPElement implements SOAPFaultCode {


    protected SOAPFaultCodeImpl(OMNamespace ns, SOAPFactory factory) {
        super(SOAP12Constants.SOAP_FAULT_CODE_LOCAL_NAME, ns, factory);
    }

    /**
     * Constructor OMElementImpl
     *
     * @param parent
     * @param builder
     */
    public SOAPFaultCodeImpl(SOAPFault parent, OMXMLParserWrapper builder, 
            SOAPFactory factory) {
        super(parent, SOAP12Constants.SOAP_FAULT_CODE_LOCAL_NAME, builder,
                factory);
    }

    /**
     * @param parent
     */
    public SOAPFaultCodeImpl(SOAPFault parent,
                             boolean extractNamespaceFromParent,
                             SOAPFactory factory) throws SOAPProcessingException {
        super(parent,
                SOAP12Constants.SOAP_FAULT_CODE_LOCAL_NAME,
                extractNamespaceFromParent, factory);
    }

    /**
     * Eran Chinthaka (chinthaka@apache.org)
     */
    public void setValue(SOAPFaultValue value) throws SOAPProcessingException {
        ElementHelper.setNewElement(this, value, value);
    }

    public SOAPFaultValue getValue() {
        return (SOAPFaultValue) ElementHelper.getChildWithName(this,
                SOAP12Constants.SOAP_FAULT_VALUE_LOCAL_NAME);
    }

    public void setSubCode(SOAPFaultSubCode value) throws SOAPProcessingException {
        ElementHelper.setNewElement(this, getSubCode(), value);
    }

    public SOAPFaultSubCode getSubCode() {
        return (SOAPFaultSubCode) ElementHelper.getChildWithName(this,
                SOAP12Constants.SOAP_FAULT_SUB_CODE_LOCAL_NAME);
    }

}
