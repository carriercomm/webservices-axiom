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

package org.apache.axiom.util.base64;

import java.util.Random;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

public class Base64UtilsTest extends TestCase {
    public void testDecode() {
        Random random = new Random(43219876);
        for (int len=0; len<20; len++) {
            byte[] data = new byte[len];
            random.nextBytes(data);
            Assert.assertThat(
                    Base64Utils.decode(Base64.encodeBase64String(data)),
                    CoreMatchers.equalTo(data));
        }
    }
    
    public void testMissingPadding() {
        try {
            Base64Utils.decode("cw");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Expected
        }
    }

    public void testTooMuchPadding() {
        try {
            Base64Utils.decode("cw===");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Expected
        }
    }
    
    public void testNonZeroRemainder() {
        try {
            Base64Utils.decode("//==");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Expected
        }
    }
    
    public void testSpace() throws Exception{
        assertEquals(
                "any carnal pleasure.",
                new String(Base64Utils.decode(" YW55IG\tNhcm5hbC\r\nBwb  GVhc3VyZS4 = "), "utf-8"));
    }

    public void testInvalidCharacter() {
        try {
            Base64Utils.decode("//-/");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Expected
        }
    }

    public void testInvalidPadding() {
        try {
            Base64Utils.decode("//=/");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Expected
        }
    }
}