/*
 * MIT License
 *
 * Copyright (c) 2023 Overrun Organization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package overrun.marshal.test;

import overrun.marshal.*;

import java.lang.foreign.MemorySegment;

/**
 * @author squid233
 * @since 0.1.0
 */
@NativeApi(libname = "NativeLib.dll", name = "MarshalTest", makeFinal = false)
public interface CMarshalTest {
    int CONST_VALUE = 42;
    boolean BOOL_VALUE = true;
    String STR_VALUE = "Hello world";

    void test();

    @Native(entrypoint = "test")
    void testWithEntrypoint();

    @Native(doc = """
        This is a test method with javadoc.

        @return an integer
        """)
    int testWithDocAndReturnValue();

    int testWithReturnValue();

    @Native(optional = true)
    void testWithOptional();

    void testWithArgument(int i, MemorySegment holder);

    MemorySegment testWithArgAndReturnValue(MemorySegment segment);

    @Native(scope = MarshalScope.PRIVATE)
    int testWithPrivate(int i);

    void testWithArray(MemorySegment arr);

    @Overload
    void testWithArray(int[] arr);

    void testWithRefArray(MemorySegment arr0, MemorySegment arr1, MemorySegment arr2);

    @Overload
    void testWithRefArray(int[] arr0, @Ref int[] arr1, @Ref(nullable = true) int[] arr2);

    void testWithString(MemorySegment str);

    @Overload
    void testWithString(String str);

    @Native(entrypoint = "testWithReturnArray")
    MemorySegment ntestWithReturnArray();

    @Overload("ntestWithReturnArray")
    boolean[] testWithReturnArray();

    @Native(doc = """
        This is a test that tests all features.

        @param segment A memory segment
        @return Another memory segment""",
        entrypoint = "testAllFeatures",
        scope = MarshalScope.PROTECTED,
        optional = true
    )
    MemorySegment testAll(MemorySegment segment);
}
