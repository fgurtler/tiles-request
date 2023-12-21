/*
 * $Id: ParameterExtractorTest.java 1066849 2011-02-03 16:12:39Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tiles.request.portlet.extractor;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Enumeration;

import javax.portlet.PortletRequest;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link ParameterExtractor}.
 *
 * @version $Rev: 1066849 $ $Date: 2011-02-03 17:12:39 +0100 (Thu, 03 Feb 2011) $
 */
public class ParameterExtractorTest {

    /**
     * The request.
     */
    private PortletRequest request;

    /**
     * The extractor to test.
     */
    private ParameterExtractor extractor;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        request = createMock(PortletRequest.class);
        extractor = new ParameterExtractor(request);
    }

    /**
     * Test method for {@link org.apache.tiles.request.portlet.extractor.ParameterExtractor#getKeys()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetKeys() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(request.getParameterNames()).andReturn(keys);

        replay(request, keys);
        assertEquals(keys, extractor.getKeys());
        verify(request, keys);
    }

    /**
     * Test method for {@link org.apache.tiles.request.portlet.extractor.ParameterExtractor#getValue(java.lang.String)}.
     */
    @Test
    public void testGetValue() {
        expect(request.getParameter("name")).andReturn("value");

        replay(request);
        assertEquals("value", extractor.getValue("name"));
        verify(request);
    }

}
