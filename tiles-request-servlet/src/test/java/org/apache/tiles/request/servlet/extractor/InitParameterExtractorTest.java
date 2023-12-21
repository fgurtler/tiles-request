/*
 * $Id: InitParameterExtractorTest.java 1066499 2011-02-02 15:33:34Z apetrelli $
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
package org.apache.tiles.request.servlet.extractor;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link InitParameterExtractor}.
 *
 * @version $Rev: 1066499 $ $Date: 2011-02-02 16:33:34 +0100 (Wed, 02 Feb 2011) $
 */
public class InitParameterExtractorTest {

    /**
     * The servlet context.
     */
    private ServletContext context;

    /**
     * The extractor to test.
     */
    private InitParameterExtractor extractor;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        context = createMock(ServletContext.class);
        extractor = new InitParameterExtractor(context);
    }

    /**
     * Test method for {@link org.apache.tiles.request.servlet.extractor.InitParameterExtractor#getKeys()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetKeys() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(context.getInitParameterNames()).andReturn(keys);

        replay(context, keys);
        assertEquals(keys, extractor.getKeys());
        verify(context, keys);
    }

    /**
     * Test method for {@link InitParameterExtractor#getValue(String)}.
     */
    @Test
    public void testGetValue() {
        expect(context.getInitParameter("name")).andReturn("value");

        replay(context);
        assertEquals("value", extractor.getValue("name"));
        verify(context);
    }

}
