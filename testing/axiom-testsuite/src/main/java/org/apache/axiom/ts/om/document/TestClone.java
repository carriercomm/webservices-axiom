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
package org.apache.axiom.ts.om.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.axiom.om.OMCloneOptions;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMMetaFactory;
import org.apache.axiom.testutils.XMLAssertEx;
import org.apache.axiom.ts.ConformanceTestCase;
import org.apache.axiom.ts.xml.XMLSample;
import org.xml.sax.InputSource;

public class TestClone extends ConformanceTestCase {
    public TestClone(OMMetaFactory metaFactory, XMLSample file) {
        super(metaFactory, file);
    }

    protected void runTest() throws Throwable {
        OMDocument original = metaFactory.createOMBuilder(metaFactory.getOMFactory(),
                TEST_PARSER_CONFIGURATION, new InputSource(file.getUrl().toString())).getDocument();
        OMDocument clone = (OMDocument)original.clone(new OMCloneOptions());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        clone.serialize(baos);
        XMLAssertEx.assertXMLIdentical(file.getUrl(),
                new ByteArrayInputStream(baos.toByteArray()), false);
    }
}
