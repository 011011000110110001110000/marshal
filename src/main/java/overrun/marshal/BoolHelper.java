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

package overrun.marshal;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

/**
 * Helper of boolean.
 *
 * @author squid233
 * @since 0.1.0
 */
public final class BoolHelper {
    public static MemorySegment of(SegmentAllocator allocator, boolean[] arr) {
        final MemorySegment segment = allocator.allocate(ValueLayout.JAVA_BOOLEAN, arr.length);
        for (int i = 0; i < arr.length; i++) {
            segment.setAtIndex(ValueLayout.JAVA_BOOLEAN, i, arr[i]);
        }
        return segment;
    }

    public static boolean[] toBoolArray(MemorySegment segment) {
        final long byteSize = segment.byteSize();
        if (byteSize > (Integer.MAX_VALUE - 8)) {
            throw new IllegalStateException(String.format("Segment is too large to wrap as %s. Size: %d", ValueLayout.JAVA_BOOLEAN, byteSize));
        }
        boolean[] b = new boolean[(int) byteSize];
        for (int i = 0; i < b.length; i++) {
            b[i] = segment.getAtIndex(ValueLayout.JAVA_BOOLEAN, i);
        }
        return b;
    }
}
