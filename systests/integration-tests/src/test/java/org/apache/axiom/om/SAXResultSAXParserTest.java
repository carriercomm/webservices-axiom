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

package org.apache.axiom.om;

import static org.apache.axiom.testing.multiton.Multiton.getInstances;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestSuite;

import org.apache.axiom.om.AbstractTestCase;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.testutils.XMLAssertEx;
import org.apache.axiom.ts.xml.XMLSample;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class SAXResultSAXParserTest extends AbstractTestCase {
    private final SAXParserFactory factory;
    private final XMLSample file;
    
    public SAXResultSAXParserTest(String name, SAXParserFactory factory, XMLSample file) {
        super(name);
        this.factory = factory;
        this.file = file;
    }

    @Override
    protected void runTest() throws Throwable {
        factory.setNamespaceAware(true);
        XMLReader reader = factory.newSAXParser().getXMLReader();
        OMDocument document = OMAbstractFactory.getOMFactory().createOMDocument();
        ContentHandler handler = document.getSAXResult().getHandler();
        reader.setContentHandler(handler);
        reader.setDTDHandler((DTDHandler)handler);
        reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
        reader.setProperty("http://xml.org/sax/properties/declaration-handler", handler);
        reader.parse(new InputSource(file.getUrl().toString()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.serialize(baos);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLAssertEx.assertXMLIdentical(file.getUrl(),
                new ByteArrayInputStream(baos.toByteArray()), true);
    }
    
    private static void addTests(TestSuite suite, SAXParserFactory factory, String name) throws Exception {
        for (XMLSample file : getInstances(XMLSample.class)) {
            suite.addTest(new SAXResultSAXParserTest(
                    file.getName() + " - " + name, factory, file));
        }
    }
    
    public static TestSuite suite() throws Exception {
        TestSuite suite = new TestSuite();
        addTests(suite, new org.apache.crimson.jaxp.SAXParserFactoryImpl(), "crimson");
        addTests(suite, new org.apache.xerces.jaxp.SAXParserFactoryImpl(), "xerces");
        return suite;
    }
}
