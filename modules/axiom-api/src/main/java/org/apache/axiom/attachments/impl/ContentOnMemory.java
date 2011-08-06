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

package org.apache.axiom.attachments.impl;

import org.apache.axiom.attachments.utils.BAAInputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * PartOnMemoryEnhanced stores the attachment in memory (in non-contigous byte arrays)
 * This implementation is used for smaller attachments to enhance 
 * performance.
 * 
 * The PartOnMemoryEnhanced object is created by the PartFactory
 * @see ContentStoreFactory
 */
public class ContentOnMemory extends ContentStore {

    ArrayList data;  // Arrays of 4K buffers
    int length;      // total length of data
    
    /**
     * Construct a PartOnMemory
     * @param headers
     * @param data array list of 4K byte[]
     * @param length (length of data in bytes)
     */
    ContentOnMemory(String contentType, ArrayList data, int length) {
        super(contentType);
        this.data =  data;
        this.length = length;
    }

    public DataHandler getDataHandler() throws MessagingException {
        DataSource ds = new MyByteArrayDataSource();
        return new MyDataHandler(ds);
    }
    
    public long getSize() throws MessagingException {
        return length;
    }
    
    
    class MyDataHandler extends DataHandler {

        DataSource ds;
        public MyDataHandler(DataSource ds) {
            super(ds);
            this.ds = ds;
        }

        public void writeTo(OutputStream os) throws IOException {
            InputStream is = ds.getInputStream();
            if (is instanceof BAAInputStream) {
                ((BAAInputStream)is).writeTo(os);
            } else {
                BufferUtils.inputStream2OutputStream(is, os);
            }
        }
    }
    
    /**
     * A DataSource that is backed by the byte[] and 
     * headers map.
     */
    class MyByteArrayDataSource implements DataSource {

        /* (non-Javadoc)
         * @see javax.activation.DataSource#getContentType()
         */
        public String getContentType() {
            String ct = ContentOnMemory.this.getContentType();
            return (ct == null) ?
                    "application/octet-stream" :
                    ct;
        }

        /* (non-Javadoc)
         * @see javax.activation.DataSource#getInputStream()
         */
        public InputStream getInputStream() throws IOException {
            return new BAAInputStream(data, length);
        }

        /* (non-Javadoc)
         * @see javax.activation.DataSource#getName()
         */
        public String getName() {
            return "MyByteArrayDataSource";
        }

        /* (non-Javadoc)
         * @see javax.activation.DataSource#getOutputStream()
         */
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
        
    }

}