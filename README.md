# Marshal

[![Java CI with Gradle](https://github.com/Over-Run/marshal/actions/workflows/gradle.yml/badge.svg?event=push)](https://github.com/Over-Run/marshal/actions/workflows/gradle.yml)

Marshal allows you to conveniently create native library bindings with [FFM API](https://openjdk.org/jeps/454).

See [wiki](https://github.com/Over-Run/marshal/wiki) for more information.

This library requires JDK 22 or newer.

## Overview

```java
import overrun.marshal.*;

import java.lang.foreign.MemorySegment;

/**
 * GLFW constants and functions
 * <p>
 * The documentation will be automatically copied
 * into the generated file
 */
@NativeApi(libname = "libglfw.so", name = "GLFW")
interface CGLFW {
    /**
     * A field
     */
    int GLFW_KEY_A = 65;

    /**
     * Sets the swap interval.
     * <p> 
     * You can set the access modifier.
     *
     * @param interval the interval
     */
    @Access(AccessModifier.PROTECTED)
    void glfwSwapInterval(int interval);

    /**
     * Custom method body
     */
    @Custom("""
        glfwSwapInterval(1);""")
    void glfwEnableVSync();

    /**
     * {@return default value if the function was not found}
     */
    @Default("0")
    @Entrypoint("glfwGetTime")
    double getTime();

    /**
     * Fixed size array.
     * Note: this method doesn't exist in GLFW
     *
     * @param arr The array
     */
    void fixedSizeArray(@FixedSize(2) int[] arr);

    /**
     * A simple method
     * <p>
     * Note: annotation Ref in this method is unnecessary;
     * however, you can use it to mark
     *
     * @param window the window
     * @param posX the position x
     * @param posY the position y
     */
    void glfwSetWindowPos(MemorySegment window, @Ref MemorySegment posX, @Ref MemorySegment posY);

    /**
     * Overload another method with the same name
     *
     * @param window the window
     * @param posX the array where to store the position x
     * @param posY the array where to store the position y
     */
    @Overload
    void glfwSetWindowPos(MemorySegment window, @Ref int[] posX, @Ref int[] posY);

    /**
     * {@return a UTF-16 string}
     */
    @SetCharset("UTF-16")
    String returnString();
}

class Main {
    public static void main(String[] args) {
        int key = GLFW.GLFW_KEY_A;
        GLFW.glfwSwapInterval(1);
        GLFW.glfwEnableVSync();
        double time = GLFW.getTime();
        GLFW.fixedSizeArray(new int[]{4, 2});
        MemorySegment windowHandle = /*...*/createWindow();
        try (MemoryStack stack = /*...*/stackPush()) {
            MemorySegment bufX1 = stack.callocInt(1);
            MemorySegment bufY1 = stack.callocInt(1);
            int[] bufX2 = {0};
            int[] bufY2 = {0};
            GLFW.glfwSetWindowPos(windowHandle, bufX1, bufY1);
            GLFW.glfwSetWindowPos(windowHandle, bufX2, bufY2);
        }
        String s = GLFW.returnString();
    }
}
```

## Import

Import as a Gradle dependency:

```groovy
dependencies {
    def marshalVersion = "0.1.0-alpha.1"
    annotationProcessor("io.github.over-run:marshal:$marshalVersion")
    implementation("io.github.over-run:marshal:$marshalVersion")
}
```
