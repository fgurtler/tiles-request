/*
 * $Id: HeaderValuesCollectionTest.java 1066446 2011-02-02 12:38:04Z apetrelli $
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
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.tiles.request.attribute.EnumeratedValuesExtractor;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link HeaderValuesMap#values()}.
 *
 * @version $Rev: 1066446 $ $Date: 2011-02-02 13:38:04 +0100 (Wed, 02 Feb 2011) $
 */
public class HeaderValuesCollectionTest {


    /**
     * The extractor to use.
     */
    private EnumeratedValuesExtractor extractor;

    /**
     * The map to test.
     */
    private HeaderValuesMap map;

    /**
     * The collection.
     */
    private Collection<String[]> coll;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        extractor = createMock(EnumeratedValuesExtractor.class);
        map = new HeaderValuesMap(extractor);
        coll = map.values();
    }

    /**
     * Tests {@link Collection#add(Object)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        coll.add(null);
    }

    /**
     * Tests {@link Collection#addAll(Object)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        coll.addAll(null);
    }

    /**
     * Tests {@link Collection#clear(Object)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        coll.clear();
    }

    /**
     * Tests {@link Collection#contains(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsValue() {
        assertFalse(map.containsValue(1));

        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");

        expect(extractor.getValues("one")).andReturn(values1);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value3");
        expect(values2.hasMoreElements()).andReturn(false);

        replay(extractor, keys, values1, values2);
        assertTrue(coll.contains(new String[] {"value2", "value3"}));
        verify(extractor, keys, values1, values2);
    }

    /**
     * Tests {@link Collection#contains(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsValueFalse() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("one")).andReturn(values1);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value3");
        expect(values2.hasMoreElements()).andReturn(false);

        replay(extractor, keys, values1, values2);
        assertFalse(coll.contains(new String[] {"value2", "value4"}));
        verify(extractor, keys, values1, values2);
    }

    /**
     * Tests {@link Collection#containsAll(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys).times(2);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");

        expect(extractor.getValues("one")).andReturn(values1).times(2);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value3");
        expect(values2.hasMoreElements()).andReturn(false);

        replay(extractor, keys, values1, values2);
        List<String[]> coll = new ArrayList<String[]>();
        coll.add(new String[] {"value1"});
        coll.add(new String[] {"value2", "value3"});
        assertTrue(this.coll.containsAll(coll));
        verify(extractor, keys, values1, values2);
    }

    /**
     * Tests {@link Collection#containsAll(Object)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAllFalse() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("one")).andReturn(values1);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);

        replay(extractor, keys, values1, values2);
        List<String[]> coll = new ArrayList<String[]>();
        coll.add(new String[] {"value4"});
        assertFalse(this.coll.containsAll(coll));
        verify(extractor, keys, values1, values2);
    }

    /**
     * Test method for {@link Collection#isEmpty()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsEmpty() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);

        replay(extractor, keys);
        assertFalse(coll.isEmpty());
        verify(extractor, keys);
    }

    /**
     * Test method for {@link Collection#iterator()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIterator() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value3");
        expect(values2.hasMoreElements()).andReturn(false);

        replay(extractor, keys, values2);
        Iterator<String[]> entryIt = coll.iterator();
        assertTrue(entryIt.hasNext());
        assertArrayEquals(new String[] { "value2", "value3" }, entryIt.next());
        verify(extractor, keys, values2);
    }

    /**
     * Test method for {@link Collection#iterator()}.
     */
    @SuppressWarnings("unchecked")
    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);

        try {
            replay(extractor, keys);
            coll.iterator().remove();
        } finally {
            verify(extractor, keys);
        }
    }

    /**
     * Tests {@link Collection#remove(Object)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        coll.remove(null);
    }

    /**
     * Tests {@link Collection#removeAll(java.util.Collection)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        coll.removeAll(null);
    }

    /**
     * Tests {@link Collection#retainAll(java.util.Collection)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        coll.retainAll(null);
    }

    /**
     * Test method for {@link org.apache.tiles.request.collection.HeaderValuesMap#size()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSize() {
        Enumeration<String> keys = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        replay(extractor, keys);
        assertEquals(2, coll.size());
        verify(extractor, keys);
    }

    /**
     * Test method for {@link Collection#toArray()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testToArray() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("one")).andReturn(values1);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value3");
        expect(values2.hasMoreElements()).andReturn(false);

        String[][] entryArray = new String[2][];
        entryArray[0] = new String[] {"value1"};
        entryArray[1] = new String[] {"value2", "value3"};

        replay(extractor, keys, values1, values2);
        assertArrayEquals(entryArray, coll.toArray());
        verify(extractor, keys, values1, values2);
    }

    /**
     * Test method for {@link Collection#toArray(Object[])}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testToArrayTArray() {
        Enumeration<String> keys = createMock(Enumeration.class);
        Enumeration<String> values1 = createMock(Enumeration.class);
        Enumeration<String> values2 = createMock(Enumeration.class);

        expect(extractor.getKeys()).andReturn(keys);
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("one");
        expect(keys.hasMoreElements()).andReturn(true);
        expect(keys.nextElement()).andReturn("two");
        expect(keys.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("one")).andReturn(values1);
        expect(values1.hasMoreElements()).andReturn(true);
        expect(values1.nextElement()).andReturn("value1");
        expect(values1.hasMoreElements()).andReturn(false);

        expect(extractor.getValues("two")).andReturn(values2);
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value2");
        expect(values2.hasMoreElements()).andReturn(true);
        expect(values2.nextElement()).andReturn("value3");
        expect(values2.hasMoreElements()).andReturn(false);

        String[][] entryArray = new String[2][];
        entryArray[0] = new String[] {"value1"};
        entryArray[1] = new String[] {"value2", "value3"};
        String[][] realArray = new String[2][];

        replay(extractor, keys, values1, values2);
        assertArrayEquals(entryArray, coll.toArray(realArray));
        verify(extractor, keys, values1, values2);
    }
}
