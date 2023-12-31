/*
 * $Id: RemovableKeySetTest.java 1066446 2011-02-02 12:38:04Z apetrelli $
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
package org.apache.tiles.request.collection;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tiles.request.attribute.HasRemovableKeys;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link RemovableKeySet}.
 *
 * @version $Rev: 1066446 $ $Date: 2011-02-02 13:38:04 +0100 (Wed, 02 Feb 2011) $
 */
public class RemovableKeySetTest {

    /**
     * The extractor to use.
     */
    private HasRemovableKeys<Integer> extractor;

    /**
     * The key set to test.
     */
    private RemovableKeySet entrySet;

    /**
     * Sets up the test.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        extractor = createMock(HasRemovableKeys.class);
        entrySet = new RemovableKeySet(extractor);
    }

    /**
     * Test method for {@link org.apache.tiles.request.collection.RemovableKeySet#remove(java.lang.Object)}.
     */
    @Test
    public void testRemove() {
        expect(extractor.getValue("one")).andReturn(1);
        extractor.removeValue("one");

        replay(extractor);
        assertTrue(entrySet.remove("one"));
        verify(extractor);
    }

    /**
     * Test method for {@link org.apache.tiles.request.collection.RemovableKeySet#remove(java.lang.Object)}.
     */
    @Test
    public void testRemoveNoEffect() {
        expect(extractor.getValue("one")).andReturn(null);

        replay(extractor);
        assertFalse(entrySet.remove("one"));
        verify(extractor);
    }

    /**
     * Test method for {@link org.apache.tiles.request.collection.RemovableKeySet#removeAll(java.util.Collection)}.
     */
    @Test
    public void testRemoveAll() {
        expect(extractor.getValue("one")).andReturn(1);
        expect(extractor.getValue("two")).andReturn(2);
        extractor.removeValue("one");
        extractor.removeValue("two");

        replay(extractor);
        List<String> coll = new ArrayList<String>();
        coll.add("one");
        coll.add("two");
        assertTrue(entrySet.removeAll(coll));
        verify(extractor);
    }

    /**
     * Test method for {@link org.apache.tiles.request.collection.RemovableKeySet#retainAll(java.util.Collection)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testRetainAll() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("three");
        expect(keys.hasMoreElements()).andReturn(false);

        extractor.removeValue("three");

        replay(extractor, keys);
        List<String> coll = new ArrayList<String>();
        coll.add("one");
        coll.add("two");
        assertTrue(entrySet.retainAll(coll));
        verify(extractor, keys);
    }

}
