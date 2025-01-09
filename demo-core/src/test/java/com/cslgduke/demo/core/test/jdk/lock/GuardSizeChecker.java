package com.cslgduke.demo.core.test.jdk.lock;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;

public class GuardSizeChecker {

    // Define the interface for the native library
    public interface CLibrary extends Library {
        CLibrary INSTANCE = Native.load("c", CLibrary.class);

        int pthread_attr_init(Pointer attr);
        int pthread_attr_getguardsize(Pointer attr, LongByReference guardsize);
        int pthread_attr_destroy(Pointer attr);
    }

    public static void main(String[] args) {
        // Allocate memory for the pthread attribute
        Pointer attr = new Memory(Native.POINTER_SIZE);
        LongByReference guardSize = new LongByReference();

        // Initialize the pthread attribute
        if (CLibrary.INSTANCE.pthread_attr_init(attr) != 0) {
            System.err.println("Failed to initialize pthread attributes");
            return;
        }

        // Get the guard size
        if (CLibrary.INSTANCE.pthread_attr_getguardsize(attr, guardSize) == 0) {
            System.out.println("Guard size: " + guardSize.getValue() + " bytes");
        } else {
            System.err.println("Failed to get guard size");
        }

        // Destroy the pthread attribute
        CLibrary.INSTANCE.pthread_attr_destroy(attr);
    }
}