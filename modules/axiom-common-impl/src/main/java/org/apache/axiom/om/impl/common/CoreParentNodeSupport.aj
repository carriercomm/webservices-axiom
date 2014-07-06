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
package org.apache.axiom.om.impl.common;

import org.apache.axiom.om.NodeUnavailableException;
import org.apache.axiom.om.OMContainer;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMXMLParserWrapper;
import org.apache.axiom.om.impl.builder.StAXBuilder;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

public aspect CoreParentNodeSupport {
    private CoreChildNode CoreParentNode.firstChild;
    private CoreChildNode CoreParentNode.lastChild;
    
    /**
     * Get the first child if it is available. The child is available if it is complete or
     * if the builder has started building the node. In the latter case,
     * {@link OMNode#isComplete()} may return <code>false</code> when called on the child. 
     * In contrast to {@link OMContainer#getFirstOMChild()}, this method will never modify
     * the state of the underlying parser.
     * 
     * @return the first child or <code>null</code> if the container has no children or
     *         the builder has not yet started to build the first child
     */
    public CoreChildNode CoreParentNode.coreGetFirstChildIfAvailable() {
        return firstChild;
    }

    public CoreChildNode CoreParentNode.coreGetLastKnownChild() {
        return lastChild;
    }

    /**
     * forcefully set the first element in this parent element
     * @param omNode
     */
    public void CoreParentNode.coreSetFirstChild(CoreChildNode firstChild) {
        if (firstChild != null) {
            firstChild.coreSetParent(this);
        }
        this.firstChild = firstChild;
    }

    /**
     * forcefully set the last element in this parent element
     * @param omNode
     */
    public void CoreParentNode.coreSetLastChild(CoreChildNode lastChild) {
         this.lastChild = lastChild;
    }

    public void CoreParentNode.buildNext() {
        OMXMLParserWrapper builder = getBuilder();
        if (builder == null) {
            throw new IllegalStateException("The node has no builder");
        } else if (((StAXOMBuilder)builder).isClosed()) {
            throw new OMException("The builder has already been closed");
        } else if (!builder.isCompleted()) {
            builder.next();
        } else {
            // If the builder is suddenly complete, but the completion status of the node
            // doesn't change, then this means that we built the wrong nodes
            throw new IllegalStateException("Builder is already complete");
        }         
    }
    
    public CoreChildNode CoreParentNode.coreGetFirstChild() {
        CoreChildNode firstChild = coreGetFirstChildIfAvailable();
        if (firstChild == null) {
            switch (getState()) {
                case CoreParentNode.DISCARDED:
                    ((StAXBuilder)getBuilder()).debugDiscarded(this);
                    throw new NodeUnavailableException();
                case CoreParentNode.INCOMPLETE:
                    do {
                        buildNext();
                    } while (getState() == CoreParentNode.INCOMPLETE
                            && (firstChild = coreGetFirstChildIfAvailable()) == null);
            }
        }
        return firstChild;
    }
}
