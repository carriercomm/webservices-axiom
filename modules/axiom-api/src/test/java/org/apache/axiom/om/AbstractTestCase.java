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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.URLDataSource;

import org.custommonkey.xmlunit.XMLTestCase;

/** Abstract base class for test cases. */
public abstract class AbstractTestCase
        extends XMLTestCase {
    protected String tempDir = "target" + File.separator + "generated" +
            File.separator +
            "temp";
    
    public static final String[] soapFiles = {
        "emtyBodymessage.xml",
        "invalidMustUnderstandSOAP12.xml",
        "minimalMessage.xml",
        "OMElementTest.xml",
        "reallyReallyBigMessage.xml",
        "sample1.xml",
        "security2-soap.xml",
        "soap12message.xml",
        "soap12RoleMessage.xml",
        "soapmessage.xml",
        "soapmessage1.xml",
        "whitespacedMessage.xml"
    };

    /** Basedir for all file I/O. Important when running tests from the reactor. */
    public String basedir = System.getProperty("basedir");

    public AbstractTestCase() {
        this(null);
    }
    
    /** @param testName  */
    public AbstractTestCase(String testName) {
        super(testName);
        if (basedir == null) {
            basedir = new File(".").getAbsolutePath();
        }
        tempDir = new File(basedir, tempDir).getAbsolutePath();
    }

    public DataSource getTestResourceDataSource(String relativePath) {
        URL url = AbstractTestCase.class.getClassLoader().getResource(relativePath);
        if (url == null) {
            fail("The test resource " + relativePath + " could not be found");
        }
        return new URLDataSource(url);
    }

    public InputStream getTestResource(String relativePath) {
        InputStream in = AbstractTestCase.class.getClassLoader().getResourceAsStream(relativePath);
        if (in == null) {
            fail("The test resource " + relativePath + " could not be found");
        }
        return in;
    }
    
    public static String[] getConformanceTestFiles() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                AbstractTestCase.class.getClassLoader().getResourceAsStream(
                        "conformance/filelist")));
        String line;
        List result = new ArrayList(10);
        while ((line = in.readLine()) != null) {
            result.add("conformance/" + line);
        }
        in.close();
        return (String[])result.toArray(new String[result.size()]);
    }

    public File getTempOutputFile(String filename) {
        File f = new File(tempDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        return new File(f, filename);
    }
}

