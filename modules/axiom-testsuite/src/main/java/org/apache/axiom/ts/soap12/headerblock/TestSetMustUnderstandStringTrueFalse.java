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
package org.apache.axiom.ts.soap12.headerblock;

import org.apache.axiom.om.OMMetaFactory;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axiom.ts.soap.SOAPSpec;
import org.apache.axiom.ts.soap.SOAPTestCase;

public class TestSetMustUnderstandStringTrueFalse extends SOAPTestCase {
    public TestSetMustUnderstandStringTrueFalse(OMMetaFactory metaFactory) {
        super(metaFactory, SOAPSpec.SOAP12);
    }

    protected void runTest() throws Throwable {
        SOAPHeaderBlock soapHeaderBlock = createSOAPHeaderBlock();
        soapHeaderBlock.setMustUnderstand("true");
        assertTrue(
                "SOAP HeaderBlock Test : - After setting MustUnderstand \"true\" calling setMustUnderstand method , getMustUnderstand method returns false",
                soapHeaderBlock.getMustUnderstand());
        soapHeaderBlock.setMustUnderstand("false");
        assertFalse(
                "SOAP HeaderBlock Test : - After setting MustUnderstand \"0\" calling setMustUnderstand method , getMustUnderstand method returns true",
                soapHeaderBlock.getMustUnderstand());
    }
}
